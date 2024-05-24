import ch.aplu.jgamegrid.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The Plus class extends the Version class to provide additional features for the SpaceInvader game.
 * It handles the setup and behavior of different types of aliens.
 */
public class Plus extends GameVersion {
    private ArrayList<AlienGridLocation> powerfulAlienLocations = new ArrayList<>();
    private ArrayList<AlienGridLocation> invulnerableAlienLocations = new ArrayList<>();
    private ArrayList<AlienGridLocation> multipleAlienLocations = new ArrayList<>();
    private MultipleAlien multipleAlien;
    private boolean multipleNow = false;

    /**
     * Constructor for the Plus class.
     * @param properties Game properties.
     * @param host The SpaceInvader instance hosting this class.
     */
    public Plus(Properties properties, SpaceInvader host) {
        super(properties, host);
    }

    /**
     * Sets up the aliens on the game grid based on their types and positions.
     * Initializes the alienGrid array and adds the aliens to the game grid.
     * Also sets the initial top-most position of the aliens for later use.
     */
    @Override
    public void setupAliens() {
        // Load the alien locations based on game properties
        setupAlienLocations();

        // Retrieve the movement controls for the aliens
        List<String> movements = getControls("aliens.control");

        // Initialize the 2D array to hold the Alien objects
        alienGrid = new Alien[nbRows][nbCols];

        // Loop through each row and column to place aliens
        for (int i = 0; i < nbRows; i++) {
            for (int k = 0; k < nbCols; k++) {
                Alien alien;

                // Determine the type of alien based on its location
                if (arrayContains(powerfulAlienLocations, i, k)) {
                    alien = new PowerfulAlien(i, k);
                } else if (arrayContains(invulnerableAlienLocations, i, k)) {
                    alien = new InvulnerableAlien(i, k);
                } else if (arrayContains(multipleAlienLocations, i, k)) {
                    alien = new MultipleAlien(i, k);
                } else {
                    alien = new NormalAlien(i, k);
                }

                // Set testing conditions if any
                alien.setTestingConditions(isAutoTesting, movements);

                // Add the alien to the grid
                alienGrid[i][k] = alien;

                // Add the alien actor to the game grid at the specified location
                addActor(alienGrid[i][k], new Location(100 - 5 * nbCols + 10 * k, 10 + 10 * i));
            }
        }
    }


    private boolean arrayContains(ArrayList<AlienGridLocation> locations, int rowIndex, int colIndex) {
        for (AlienGridLocation location : locations) {
            if (location.rowIndex == rowIndex && location.colIndex == colIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a property value into an ArrayList of AlienGridLocation objects.
     * The property value is expected to be a string representing alien locations in the format "row-col;row-col;...".
     *
     * @param propertyName The name of the property to read from the properties file.
     * @return An ArrayList of AlienGridLocation objects representing the locations of specific types of aliens.
     */
    private ArrayList<AlienGridLocation> convertFromProperty(String propertyName) {
        // Retrieve the property value as a string
        String alienLocationString = properties.getProperty(propertyName);

        // Initialize an ArrayList to hold the AlienGridLocation objects
        ArrayList<AlienGridLocation> alienLocations = new ArrayList<>();

        // Check if the property value exists
        if (alienLocationString != null) {
            // Split the string into individual location strings
            String[] locations = alienLocationString.split(";");

            // Loop through each location string
            for (String location : locations) {
                // Split the location string into row and column indices
                String[] locationPair = location.split("-");

                // Parse the row and column indices as integers
                int rowIndex = Integer.parseInt(locationPair[0]);
                int colIndex = Integer.parseInt(locationPair[1]);

                // Create a new AlienGridLocation object and add it to the ArrayList
                alienLocations.add(new AlienGridLocation(rowIndex, colIndex));
            }
        }

        // Return the ArrayList of AlienGridLocation objects
        return alienLocations;
    }


    /**
     * Sets up the locations of special types of aliens based on properties.
     * Reads properties for powerful, invulnerable, and multiple aliens and populates the respective ArrayLists.
     */
    private void setupAlienLocations() {
        powerfulAlienLocations = convertFromProperty("Powerful.locations");
        invulnerableAlienLocations = convertFromProperty("Invulnerable.locations");
        multipleAlienLocations = convertFromProperty("Multiple.locations");
    }

    /**
     * used to let game know it is time to multiple !
     * @param multipleAlien who triggers this
     */
    public void askForMultiple(MultipleAlien multipleAlien){
        this.multipleAlien = multipleAlien;
        multipleNow = true;
    }

    /**
     * used to achieve multiple alien's capability
     * Adds a new row of aliens and adapt all current aliens' locations to new game grid
     *
     */
    private void multiple(){
        // create a new game grid
        Alien[][] newGrid = new Alien[nbRows+1][nbCols];
        int row = multipleAlien.getRowIndex();
        int col = multipleAlien.getColIndex();

        // Add a new row of aliens
        for(int i=0;i<nbCols;i++){
            newGrid[0][i] = new NormalAlien(0, i);
            addActor(newGrid[0][i], new Location(multipleAlien.getX() - 10*col + 10*i, 5));
            // make the new regular alien follows existing alien's setting
            newGrid[0][i].follow(multipleAlien);
        }

        for(int i=1;i<nbRows+1;i++){
            for(int j=0;j<nbCols;j++){
                if(i-1 != row || j != col) {
                    // adapt all non-multiple aliens' locations to the new game grid
                    newGrid[i][j] = alienGrid[i-1][j];
                    newGrid[i][j].incrementRowIndex();
                }else{
                    // turn the multiple alien into regular alien
                    newGrid[i][j] = new NormalAlien(i, j);
                    System.out.println(i);
                    addActor(newGrid[i][j], alienGrid[i-1][j].getLocation());
                    // make the new regular alien follows existing alien's setting
                    newGrid[i][j].follow(multipleAlien);
                }
            }
        }
        multipleAlien.removeSelf();

        alienGrid = newGrid;
        nbRows += 1;
    }

    /**
     * This method accelerates all aliens in the grid that implement the Accelerable interface.
     * It is called when the number of shots satisfy with certain conditions.
     */

    /**
     * Accelerates all aliens in the grid that implement the Accelerable interface.
     * Called when the number of shots satisfies certain conditions.
     * @param nbShots The number of shots fired.
     */
    public void accelerateAliens(int nbShots) {
        int speed = 0;
        boolean accelerated = false;
        if (nbShots == 500) {
            notifyAliensMoveFast();  // Call the method on the instance
            speed = 5;
            accelerated = true;
        } else if (nbShots == 100) {
            notifyAliensMoveFast();  // Call the method on the instance
            speed = 4;
            accelerated = true;
        } else if (nbShots == 50) {
            notifyAliensMoveFast();  // Call the method on the instance
            speed = 3;
            accelerated = true;
        } else if (nbShots == 10) {
            notifyAliensMoveFast();  // Call the method on the instance
            speed = 2;
            accelerated = true;
        }
        if(accelerated) {
            // Loop through each row of aliens in the grid
            for (Alien[] row : alienGrid) {
                // Loop through each alien in the current row
                for (Alien alien : row) {
                    // Check if the current alien implements the Accelerable interface
                    if (alien instanceof Accelerable) {
                        // Call the accelerate() method to make the alien move faster
                        ((Accelerable) alien).accelerate(speed);
                    }
                }
            }
        }
    }

    /**
     * Notify when aliens start moving fast.
     */
    protected void notifyAliensMoveFast() {
        console.log("Aliens start moving fast");
    }

    @Override
    public void act() {
        super.act();
        if(multipleNow){
            multiple();
            multipleNow = false;
        }
    }
}

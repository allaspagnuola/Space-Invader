import ch.aplu.jgamegrid.GGKeyListener;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Abstract class representing different versions of the Space Invader game.
 */
public abstract class GameVersion extends GameGrid implements GGKeyListener {
    protected SpaceInvader console = null;
    protected boolean isAutoTesting = false;
    protected Properties properties = null;
    private boolean isGameOver = false;
    protected int nbRows = 3;
    protected int nbCols = 11;
    protected Alien[][] alienGrid;

    /**
     * Constructor for the Version class.
     *
     * @param properties The properties object containing game settings.
     * @param console       The instance of SpaceInvader hosting the game.
     */
    public GameVersion(Properties properties, SpaceInvader console) {
        super(200, 100, 5, false);
        this.properties = properties;
        this.isAutoTesting = Boolean.parseBoolean(properties.getProperty("isAuto"));
        this.console = console;
        getRowCols();
    }

    public void run(boolean isDisplayingUI) {
        // Set simulation period from properties
        setSimulationPeriod(Integer.parseInt(properties.getProperty("simulationPeriod")));

        // Setup alien grid and spaceship based on specific game version
        setupAliens();
        setUpSpaceship();

        // Add key listener and set background text
        addKeyListener(this);
        getBg().setFont(new Font("SansSerif", Font.PLAIN, 12));
        getBg().drawText("Use <- -> to move, spacebar to shoot", new Point(400, 330));
        getBg().drawText("Press any key to start...", new Point(400, 350));

        // Show UI if needed
        if (isDisplayingUI) {
            show();
        }

        // Start auto-testing if enabled
        isAutoTesting = Boolean.parseBoolean(properties.getProperty("isAuto"));
        if (isAutoTesting) {
            setBgColor(java.awt.Color.black);  // Erase text
            doRun();
        }

        // Main game loop
        while (!isGameOver) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Pause the game
        doPause();
    }

    /**
     * Override the act method to define custom behavior.
     */
    @Override
    public void act() {// Log alien locations and statuses
        console.log("Alien locations: ");
        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbCols; j++) {
                Alien alienData = alienGrid[i][j];

                // Determine if the alien is removed (dead) or still alive
                String isDeadStatus = alienData.isRemoved() ? "Dead" : "Alive";

                // Determine the grid location of the alien
                String gridLocation = "0-0";
                if (!alienData.isRemoved()) {
                    gridLocation = alienData.getX() + "-" + alienData.getY();
                }


                // Construct a string representing the alien's data in a specific format
                String alienDataString = String.format("%s@%d-%d@%s@%s#", alienData.getType(),
                        alienData.getRowIndex(), alienData.getColIndex(), isDeadStatus, gridLocation);

                // Append the alien data to the log
                console.log(alienDataString);
            }
        }
        console.log("\n"); // Add a new line after logging all alien data
    }


    /**
     * Sets the game over status.
     * @param isOver Boolean to set the game over status.
     */
    public void setIsGameOver(boolean isOver) {
        isGameOver = isOver;
    }

    /**
     * Set up the spaceship in the game.
     */
    protected void setUpSpaceship() {
        SpaceShip ss = new SpaceShip(this);
        List<String> controls = getControls("space_craft.control");
        ss.setTestingConditions(isAutoTesting, controls);
        // add as a listener for the game
        addKeyListener(ss);
        addActor(ss, new Location(100, 90));
    }

    /**
     * Retrieve the number of rows and columns for the alien grid from properties.
     */
    private void getRowCols() {
        nbRows = Integer.parseInt(properties.getProperty("rows"));
        nbCols = Integer.parseInt(properties.getProperty("cols"));
    }

    /**
     * Get the list of controls for aliens or the spaceship from properties.
     *
     * @param controlName The name of the control in the properties.
     * @return A list of control strings.
     */
    protected List<String> getControls(String controlName) {
        String control = properties.getProperty(controlName);
        List<String> controls = null;
        if (control != null) {
            controls = Arrays.asList(control.split(";"));
        }
        return controls;
    }

    /**
     * Set up the aliens gird based on corresponding version
     */
    protected abstract void setupAliens();


    /**
     * Notify when an alien has been hit.
     *
     * @param actor The Alien object that was hit.
     */
    protected void notifyAlienHit(Alien actor) {
        String alienData = String.format("%s@%d-%d",
                actor.getType(), actor.getRowIndex(), actor.getColIndex());
        console.log("An alien has been hit.");
        console.log(alienData + "\n");
    }

    /**
     * Handle key pressed events.
     *
     * @param evt The KeyEvent object.
     * @return Boolean to consume the key or not.
     */
    public boolean keyPressed(KeyEvent evt)
    {
        if (!isRunning())
        {
            setBgColor(java.awt.Color.black);  // Erase text
            doRun();
        }
        return false;  // Do not consume key
    }
    /**
     * Handle key released events.
     *
     * @param evt The KeyEvent object.
     * @return Boolean to consume the key or not.
     */
    public boolean keyReleased(KeyEvent evt)
    {
        return false;
    }
}
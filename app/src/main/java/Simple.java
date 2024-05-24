import ch.aplu.jgamegrid.Location;

import java.util.List;
import java.util.Properties;

/**
 * Simple class represents a simple version of the game.
 * It extends the Version class.
 */
public class Simple extends GameVersion {

    /**
     * Constructor for creating a Simple object.
     *
     * @param properties The properties object containing game settings.
     * @param host       The SpaceInvader object that hosts this version.
     */
    public Simple(Properties properties, SpaceInvader host) {
        super(properties, host);
    }

    /**
     * Sets up the aliens on the game grid for the Simple version.
     * Overrides the setupAliens method from the parent class.
     */
    @Override
    public void setupAliens() {
        // Retrieve the list of movements for the aliens
        List<String> movements = getControls("aliens.control");

        // Initialize the alien grid
        alienGrid = new Alien[nbRows][nbCols];

        // Loop through each row and column to populate the grid with RegularAlien objects
        for (int i = 0; i < nbRows; i++) {
            for (int k = 0; k < nbCols; k++) {
                NormalAlien alien = new NormalAlien(i, k);

                // Set testing conditions for the alien
                alien.setTestingConditions(isAutoTesting, movements);

                // Add the alien to the grid
                alienGrid[i][k] = alien;

                // Add the alien actor to the game host at the specified location
                addActor(alienGrid[i][k], new Location(100 - 5 * nbCols + 10 * k, 10 + 10 * i));
            }
        }
    }
}

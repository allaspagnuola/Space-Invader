import ch.aplu.jgamegrid.*;

import java.util.List;

/**
 * The Bomb class represents a bomb in the SpaceInvader game.
 * It extends the Actor class from the JGameGrid library.
 */
public class Bomb extends Actor {

    /**
     * Constructor for the Bomb class.
     * Initializes the bomb with its sprite image.
     */
    public Bomb() {
        super("sprites/bomb.gif");
    }

    /**
     * Resets the direction of the bomb to face north.
     */
    public void reset() {
        setDirection(Location.NORTH);
    }

    /**
     * Defines the behavior of the bomb during each act.
     * The bomb moves, checks for collisions with aliens, and handles explosions.
     */
    public void act() {
        // Move the bomb
        move();

        // Get the SpaceInvader game instance
        GameVersion game = (GameVersion) gameGrid;

        // Check for collisions with aliens at the current location
        List<Actor> actors = gameGrid.getActorsAt(getLocation(), Alien.class);
        if (actors.size() > 0) {
            // If there are aliens at the current location, trigger their hit method
            for (Actor actor : actors) {
                if (actor instanceof Alien) {
                    Alien alien = (Alien) actor;
                    game.notifyAlienHit(alien);
                    alien.hit(this);
                }
            }
            return;
        }

        // Remove the bomb if it reaches the top of the grid
        if (getLocation().y < 5) {
            removeSelf();
        }
    }
}

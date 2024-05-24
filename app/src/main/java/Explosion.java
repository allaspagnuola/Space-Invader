import ch.aplu.jgamegrid.*;

/**
 * The Explosion class represents an explosion in the SpaceInvader game.
 * It extends the Actor class from the JGameGrid library.
 */
public abstract class Explosion extends Actor {

    /**
     * Constructor for the Explosion class.
     * Initializes the explosion sprite and sets its slowdown factor.
     */
    public Explosion(String image) {
        // Initialize the explosion sprite using the image "sprites/explosion1.gif"
        super(image);
    }
}

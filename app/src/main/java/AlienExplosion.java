public class AlienExplosion extends Explosion{
    /**
     * Constructor for the Explosion class.
     * Initializes the explosion sprite and sets its slowdown factor.
     */
    public AlienExplosion() {
        // Initialize the explosion sprite using the image "sprites/explosion1.gif"
        super("sprites/explosion1.gif");

        // Set the slowdown factor to 3 to control the speed of the animation
        setSlowDown(3);
    }

    /**
     * Defines the behavior of the explosion during each act.
     * In this case, the explosion removes itself from the game grid.
     */
    @Override
    public void act() {
        // Remove the explosion sprite from the game grid
        removeSelf();
    }
}

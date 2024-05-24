/**
 * PowerfulAlien class that extends the Alien class.
 * This type of alien has a shield that can withstand multiple hits.
 */
public class PowerfulAlien extends Alien {
    private static final int SHIELD = 4;  // Initial shield strength
    private int shield;  // Current shield strength
    private SpaceShip spaceShip;  // Reference to the SpaceShip object (if needed)

    /**
     * Constructor for PowerfulAlien.
     *
     * @param rowIndex The row index of the alien in the grid.
     * @param colIndex The column index of the alien in the grid.
     */
    public PowerfulAlien(int rowIndex, int colIndex) {
        super("sprites/powerful_alien.gif", "powerful", rowIndex, colIndex);
        shield = SHIELD;  // Initialize shield strength
    }

    /**
     * Overrides the act method from the Alien class.
     * Calls the parent class's act method and performs additional actions if needed.
     */
    public void act() {
        super.act();
        // Additional actions can be added here
    }

    /**
     * Overrides the accelerate method from the Alien class.
     * Accelerates the alien's movement based on the number of shots.
     *
     * @param nbShots The number of shots fired.
     */
    public void accelerate(int nbShots) {
        super.accelerate(nbShots);
    }

    /**
     * Overrides the hit method from the Alien class.
     * Reduces the shield strength if it's not zero, otherwise calls the parent class's hit method.
     *
     * @param hitter The Bomb object that hit the alien.
     */
    public void hit(Bomb hitter) {
        if (shield == 0) {
            super.hit(hitter);
        } else {
            shield--;  // Reduce shield strength
            hitter.removeSelf();  // Remove the bomb that hit the alien
        }
    }
}

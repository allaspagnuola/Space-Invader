/**
 * The InvulnerableAlien class represents an alien in the SpaceInvader game that has invulnerability features.
 * It extends the Alien class.
 */
public class InvulnerableAlien extends Alien {
    private boolean isInvulnerable = true;  // Flag to indicate if the alien is invulnerable
    private int invulnerableCount = 0;  // Counter to track invulnerability time
    private final int INVULNERABLE_TIME = 4;  // Time duration for which the alien is invulnerable
    private final int INVULNERABLE_CD = 8;  // Cooldown time for invulnerability


    /**
     * Constructor for the InvulnerableAlien class.
     * @param rowIndex The row index in the grid.
     * @param colIndex The column index in the grid.
     */
    public InvulnerableAlien(int rowIndex, int colIndex) {
        super("sprites/invulnerable_alien.gif", "invulnerable", rowIndex, colIndex);
    }

    /**
     * Defines the behavior of the invulnerable alien during each act.
     * It calls the parent class's act method and handles its own invulnerability logic.
     */
    @Override
    public void act() {
        super.act();
        handleInvulnerability();
    }

    /**
     * Handles the invulnerability logic for the alien.
     */
    private void handleInvulnerability() {
        if (isInvulnerable) {
            invulnerableCount++;
            if (invulnerableCount >= INVULNERABLE_TIME) {
                isInvulnerable = false;
                invulnerableCount = 0;
            }
        } else {
            invulnerableCount++;
            if (invulnerableCount >= INVULNERABLE_CD) {
                isInvulnerable = true;
                invulnerableCount = 0;
            }
        }
    }

    /**
     * Handles the event when the alien is hit by a bomb.
     * @param hitter The bomb that hit the alien.
     */
    public void hit(Bomb hitter) {
        if (!isInvulnerable) {
            super.hit(hitter);
        } else {
            // Do nothing since it's invulnerable
        }
    }
}

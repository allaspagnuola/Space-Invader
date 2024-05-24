import ch.aplu.jgamegrid.*;
import java.util.List;

/**
 * The Alien class represents an alien in the SpaceInvader game.
 * It extends the Actor class and implements the Accelerable interface.
 */
public abstract class Alien extends Actor implements Accelerable, Explodable{
    // Maximum number of steps before changing direction
    private int maxNbSteps = 28;
    // Current number of steps taken
    private int nbSteps;
    // Flag to indicate if the alien is moving
    private boolean isMoving = true;
    // Flag for automated testing
    private boolean isAutoTesting;
    // List of movements for automated testing
    private List<String> movements;
    // Index to track the current movement in the list
    private int movementIndex = 0;
    // Type of alien (e.g., "multiple", "invulnerable", etc.)
    private String type;
    // Row index in the grid
    private int rowIndex;
    // Column index in the grid
    private int colIndex;
    // Speed of the alien
    private int speed = 1;
    // Step size for movement
    private final int stepSize = 5;

    /**
     * Constructor for the Alien class.
     * @param imageName The image file name for the alien sprite.
     * @param type The type of the alien.
     * @param rowIndex The row index in the grid.
     * @param colIndex The column index in the grid.
     */
    public Alien(String imageName, String type, int rowIndex, int colIndex) {
        super(imageName);
        setSlowDown(7);
        this.type = type;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public String getType() {
        return type;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    /**
     * Resets the number of steps taken by the alien.
     */
    public void reset() {
        nbSteps = 13;
    }

    /**
     * Sets the testing conditions for the alien.
     * @param isAutoTesting Flag for automated testing.
     * @param movements List of movements for automated testing.
     */
    public void setTestingConditions(boolean isAutoTesting, List<String> movements) {
        this.isAutoTesting = isAutoTesting;
        this.movements = movements;
    }

    /**
     * Checks the movements for the alien based on the list of movements.
     * This method is particularly useful for automated testing.
     */
    private void checkMovements() {
        if (isAutoTesting) {
            if (movements != null && movementIndex < movements.size()) {
                String movement = movements.get(movementIndex);
                if (movement.equals("S")) {
                    isMoving = false;
                } else if (movement.equals("M")) {
                    isMoving = true;
                }
                movementIndex++;
            }
        }
    }

    /**
     * Defines the behavior of the alien during each act.
     * It checks the movements, updates the position, and handles collisions.
     */
    public void act() {
        checkMovements();
        if (!isMoving) {
            return;
        }
        if (nbSteps < maxNbSteps) {
            if (maxNbSteps - nbSteps < speed) {
                move((maxNbSteps - nbSteps) * stepSize);
                nbSteps += maxNbSteps - nbSteps;
            } else {
                move(speed * stepSize);
                nbSteps += speed;
            }
        } else {
            nbSteps = 0;
            int angle;
            if (getDirection() == 0) {
                angle = 90;
            } else {
                angle = -90;
            }
            turn(angle);
            move();
            turn(angle);
        }
        if (getLocation().y > 90) {
            removeSelf();
        }
    }

    /**
     * Handles the event when the alien is hit by a bomb.
     * @param hitter The bomb that hit the alien.
     */
    protected void hit(Bomb hitter) {
        Explode();
        hitter.removeSelf();
    }

    /**
     * Accelerates the alien based on the speed parameter.
     * @param speed The new speed of the alien.
     */
    @Override
    public void accelerate(int speed) {
        this.speed = speed;
    }

    /**
     * Gets the number of steps taken by the object.
     *
     * @return The number of steps.
     */
    public int getNbSteps() {
        return nbSteps;
    }

    /**
     * Gets the current movement index.
     *
     * @return The movement index.
     */
    public int getMovementIndex() {
        return movementIndex;
    }

    /**
     * Gets the speed of the object.
     *
     * @return The speed.
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Checks if the object is operating in auto-testing mode.
     *
     * @return true if auto-testing is enabled, false otherwise.
     */
    public boolean isAutoTesting() {
        return isAutoTesting;
    }

    /**
     * Gets the list of movements.
     *
     * @return The list of movements.
     */
    public List<String> getMovements() {
        return movements;
    }

    /**
     * Gets the maximum number of steps allowed.
     *
     * @return The maximum number of steps.
     */
    public int getMaxNbSteps() {
        return maxNbSteps;
    }


    /**
     * Makes the alien follow another alien's movements and states.
     * @param alien The alien to follow.
     */
    public void follow(Alien alien) {
        this.movementIndex = alien.getMovementIndex();
        this.nbSteps = alien.getNbSteps();
        this.maxNbSteps = alien.getMaxNbSteps();
        this.speed = alien.getSpeed();
        this.isAutoTesting = alien.isAutoTesting();
        this.movements = alien.getMovements();
        this.setDirection(alien.getDirection());
    }

    /**
     * used to increase the row index in alien grid
     */
    public void incrementRowIndex() {
        this.rowIndex++;
    }

    @Override
    public void Explode() {
        GameVersion host = (GameVersion) gameGrid;
        Explosion explosion = new AlienExplosion();
        host.addActor(explosion, getLocation());
        host.removeActorsAt(getLocation(), Alien.class);
    }
}

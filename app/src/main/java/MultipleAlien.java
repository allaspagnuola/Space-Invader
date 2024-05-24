/**
 * The MultipleAlien class represents an alien in the SpaceInvader game that has the ability to multiply.
 * It extends the Alien class.
 */
public class MultipleAlien extends Alien {

    private Integer initialY = null;

    /**
     * Constructor for the MultipleAlien class.
     * @param rowIndex The row index in the grid.
     * @param colIndex The column index in the grid.
     */
    public MultipleAlien(int rowIndex, int colIndex) {
        super("sprites/multiple_alien.gif", "multiple", rowIndex, colIndex);
    }

    /**
     * Defines the behavior of the multiple alien during each act.
     * It calls the parent class's act method and can be overridden for custom behavior.
     */
    @Override
    public void act() {
        // The act method can be overridden if the multiple alien has a different behavior
        super.act();
        if(initialY == null) initialY = getY();
        if(getY() != initialY){
            ((Plus)gameGrid).askForMultiple(this);
        }
    }
}

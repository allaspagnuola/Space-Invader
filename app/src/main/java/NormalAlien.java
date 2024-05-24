/**
 * RegularAlien class represents a regular type of alien in the game.
 * It extends the Alien class.
 */
public class NormalAlien extends Alien {

    /**
     * Constructor for creating a RegularAlien object.
     *
     * @param rowIndex The row index where the alien is located on the grid.
     * @param colIndex The column index where the alien is located on the grid.
     */
    public NormalAlien(int rowIndex, int colIndex) {
        // Call the constructor of the parent class (Alien)
        super("sprites/alien.gif", "alien", rowIndex, colIndex);
    }
}

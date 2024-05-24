/**
 * The AlienGridLocation class represents the grid location of an alien in the SpaceInvader game.
 * It contains the row and column indices to identify the alien's position in the grid.
 */
public class AlienGridLocation {

    // The row index of the alien in the grid
    public int rowIndex;

    // The column index of the alien in the grid
    public int colIndex;

    /**
     * Constructor for the AlienGridLocation class.
     *
     * @param rowIndex The row index of the alien in the grid.
     * @param colIndex The column index of the alien in the grid.
     */
    public AlienGridLocation(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }
}

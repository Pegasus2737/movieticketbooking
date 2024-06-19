package ticketbooking.Seat;

/**
 * This exception is thrown when a seat does not exist in a seating arrangement.
 */
public class SeatDoesntExistException extends Exception {
    private int row;
    private int column;

    /**
     * Constructs a new SeatDoesntExistException with the specified row and column.
     *
     * @param row    the row of the non-existent seat
     * @param column the column of the non-existent seat
     */
    public SeatDoesntExistException(int row, int column) {
        super("Seat at row " + row + " and column " + column + " does not exist.");
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row of the non-existent seat.
     *
     * @return the row of the non-existent seat
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the non-existent seat.
     *
     * @return the column of the non-existent seat
     */
    public int getColumn() {
        return column;
    }
}

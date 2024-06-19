package ticketbooking.Seat;

/**
 * Exception thrown when attempting to book a seat that is already booked.
 */
public class SeatAlreadyBookedException extends Exception {
    private int row;
    private int column;

    /**
     * Constructs a SeatAlreadyBookedException with the specified row and column.
     *
     * @param row    the row of the seat
     * @param column the column of the seat
     */
    public SeatAlreadyBookedException(int row, int column) {
        super("Seat at row " + row + " and column " + column + " is already booked.");
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row of the seat.
     *
     * @return the row of the seat
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the seat.
     *
     * @return the column of the seat
     */
    public int getColumn() {
        return column;
    }
}

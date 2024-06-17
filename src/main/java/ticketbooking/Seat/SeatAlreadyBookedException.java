package ticketbooking.Seat;

public class SeatAlreadyBookedException extends Exception{
    private int row;
    private int column;

    public SeatAlreadyBookedException(int row, int column) {
        super("Seat at row " + row + " and column " + column + " is already booked.");
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

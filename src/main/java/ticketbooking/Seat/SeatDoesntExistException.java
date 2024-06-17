package ticketbooking.Seat;

public class SeatDoesntExistException extends Exception{
    private int row;
    private int column;

    public SeatDoesntExistException(int row, int column) {
        super("Seat at row " + row + " and column " + column + " does not exist.");
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

package ticketbooking.showroom;

/**
 * Exception thrown when a show room does not exist.
 */
public class ShowRoomDoesntExistException extends Exception {
    /**
     * Constructs a new ShowRoomDoesntExistException with the specified room name.
     *
     * @param roomName the name of the show room that doesn't exist
     */
    public ShowRoomDoesntExistException(String roomName) {
        super("Showroom " + roomName + " does not exist");
    }
}

package ticketbooking.showroom;

/**
 * Exception thrown when a showroom already exists.
 */
public class ShowRoomExistException extends Exception{
    public ShowRoomExistException(ShowRoom showRoom) {
        super("Showroom " + showRoom.getRoomName() + " already exists");
    }
}

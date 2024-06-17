package ticketbooking.showroom;

public class ShowRoomDoesntExistException extends Exception{
    public ShowRoomDoesntExistException(String roomName) {
        super("Showroom " + roomName + " does not exist");
    }
}

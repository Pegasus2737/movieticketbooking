package ticketbooking.showroom;

public class ShowRoomExistException extends Exception{
    public ShowRoomExistException(ShowRoom showRoom) {
        super("Showroom " + showRoom.getRoomName() + " already exists");
    }
}

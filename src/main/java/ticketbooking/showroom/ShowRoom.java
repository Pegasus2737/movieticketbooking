package ticketbooking.showroom;
import ticketbooking.movieinfo.*;
import java.util.ArrayList;

public class ShowRoom{
    private String roomName;
    private int row;
    private int column;
    private Boolean isAvailable;
    private ArrayList<MovieShow> shows;

    public ShowRoom(String roomName, int row, int column, Boolean isAvailable, ArrayList<MovieShow> shows) {
        this.roomName = roomName;
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
        this.shows = shows;
    }

    public ShowRoom(String roomName, int row, int column, Boolean isAvailable) {
        this.roomName = roomName;
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
    }

    public ShowRoom(String roomName, int row, int column) {
        this.roomName = roomName;
        this.row = row;
        this.column = column;
        this.isAvailable = false;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setSeats(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public boolean timeAvailable(MovieShow show) {
        for (MovieShow s : shows) {
            if (s.getShowTime().equals(show.getShowTime())) {
                return false;
            }
        }
        return true;
    }

    public void addShow(MovieShow show) {
        if (shows == null) {
            shows = new ArrayList<MovieShow>();
            shows.add(show);
        } else {
            shows.add(show);
            shows.sort((s1, s2) -> s1.getShowTime().compareTo(s2.getShowTime()));
        }
    }

    public int showCount() {
        return shows.size();
    }

    @Override
    public String toString() {
        String showsStr = "";
        if (shows != null) {
            for (MovieShow show : shows) {
                showsStr += show.toString() + "\n";
            }
        }
        return "Room Name: " + this.getRoomName() + "\n" +
                "Row: " + this.row + "\n" +
                "Column: " + this.column + "\n" +
                "Available: " + this.isAvailable + "\n" +
                "Shows: \n" + showsStr;
    }
}

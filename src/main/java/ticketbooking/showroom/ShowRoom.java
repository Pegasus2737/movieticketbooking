package ticketbooking.showroom;
import ticketbooking.movieinfo.*;
import java.util.ArrayList;

/**
 * The ShowRoom class represents a movie showroom in a theater.
 * It contains information about the room name, seating arrangement, availability, and the list of movie shows.
 */
public class ShowRoom {
    private String roomName;
    private int row;
    private int column;
    private Boolean isAvailable;
    private ArrayList<MovieShow> shows;

    /**
     * Constructs a ShowRoom object with the specified room name, seating arrangement, availability, and list of movie shows.
     *
     * @param roomName     the name of the showroom
     * @param row          the number of rows in the seating arrangement
     * @param column       the number of columns in the seating arrangement
     * @param isAvailable  the availability status of the showroom
     * @param shows        the list of movie shows in the showroom
     */
    public ShowRoom(String roomName, int row, int column, Boolean isAvailable, ArrayList<MovieShow> shows) {
        this.roomName = roomName;
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
        this.shows = shows;
    }

    /**
     * Constructs a ShowRoom object with the specified room name, seating arrangement, and availability.
     *
     * @param roomName     the name of the showroom
     * @param row          the number of rows in the seating arrangement
     * @param column       the number of columns in the seating arrangement
     * @param isAvailable  the availability status of the showroom
     */
    public ShowRoom(String roomName, int row, int column, Boolean isAvailable) {
        this.roomName = roomName;
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
    }

    /**
     * Constructs a ShowRoom object with the specified room name, seating arrangement, and sets the availability to false.
     *
     * @param roomName     the name of the showroom
     * @param row          the number of rows in the seating arrangement
     * @param column       the number of columns in the seating arrangement
     */
    public ShowRoom(String roomName, int row, int column) {
        this.roomName = roomName;
        this.row = row;
        this.column = column;
        this.isAvailable = false;
    }

    /**
     * Returns the name of the showroom.
     *
     * @return the name of the showroom
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Sets the name of the showroom.
     *
     * @param roomName the name of the showroom
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Returns the number of rows in the seating arrangement.
     *
     * @return the number of rows in the seating arrangement
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the number of columns in the seating arrangement.
     *
     * @return the number of columns in the seating arrangement
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the number of rows and columns in the seating arrangement.
     *
     * @param row    the number of rows in the seating arrangement
     * @param column the number of columns in the seating arrangement
     */
    public void setSeats(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the availability status of the showroom.
     *
     * @return the availability status of the showroom
     */
    public Boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability status of the showroom.
     *
     * @param available the availability status of the showroom
     */
    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    /**
     * Checks if the given movie show time is available in the showroom.
     *
     * @param show the movie show to check availability for
     * @return true if the show time is available, false otherwise
     */
    public boolean timeAvailable(MovieShow show) {
        for (MovieShow s : shows) {
            if (s.getShowTime().equals(show.getShowTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a movie show to the showroom.
     * If the list of shows is null, it creates a new list and adds the show.
     * Otherwise, it adds the show to the existing list and sorts the shows by show time.
     *
     * @param show the movie show to add
     */
    public void addShow(MovieShow show) {
        if (shows == null) {
            shows = new ArrayList<MovieShow>();
            shows.add(show);
        } else {
            shows.add(show);
            shows.sort((s1, s2) -> s1.getShowTime().compareTo(s2.getShowTime()));
        }
    }

    /**
     * Returns the number of movie shows in the showroom.
     *
     * @return the number of movie shows in the showroom
     */
    public int showCount() {
        return shows.size();
    }

    /**
     * Returns a string representation of the showroom.
     *
     * @return a string representation of the showroom
     */
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

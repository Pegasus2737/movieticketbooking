package ticketbooking.movieinfo;

/**
 * Represents a movie show with additional information about the show room.
 * Extends the MovieShow class.
 */
public class MovieShowWithShowRoom extends MovieShow {
    private String roomString;

    /**
     * Constructs a MovieShowWithShowRoom object with the given movie show and show room information.
     * 
     * @param movieShow The movie show information.
     * @param roomString The show room information.
     */
    public MovieShowWithShowRoom(MovieShow movieShow, String roomString) {
        super(movieShow);
        this.roomString = roomString;
    }

    /**
     * Gets the show room information.
     * 
     * @return The show room information.
     */
    public String getRoomString() {
        return roomString;
    }

    /**
     * Gets the movie show information.
     * 
     * @return A new MovieShow object with the movie show information.
     */
    public MovieShow getMovieShow() {
        return new MovieShow(super.getTitle(), super.getLength(), super.getShowTime(), super.getTicketPrice());
    }
    
    /**
     * Returns a string representation of the MovieShowWithShowRoom object.
     * 
     * @return A string representation of the object, including the movie show information and show room information.
     */
    @Override
    public String toString() {
        return super.toString() + "Room: " + roomString + "\n\n";
    }

    /**
     * Checks if this MovieShowWithShowRoom object is equal to another MovieShowWithShowRoom object.
     * 
     * @param movieShowWithShowRoom The other MovieShowWithShowRoom object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    public boolean equals(MovieShowWithShowRoom movieShowWithShowRoom) {
        return super.equals(movieShowWithShowRoom) && roomString.equals(movieShowWithShowRoom.roomString);
    }
}

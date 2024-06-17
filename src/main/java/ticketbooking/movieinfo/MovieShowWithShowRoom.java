package ticketbooking.movieinfo;

public class MovieShowWithShowRoom extends MovieShow {
    private String roomString;
    public MovieShowWithShowRoom(MovieShow movieShow, String roomString) {
        super(movieShow);
        this.roomString = roomString;
    }

    public String getRoomString() {
        return roomString;
    }

    public MovieShow getMovieShow() {
        return new MovieShow(super.getTitle(), super.getLength(), super.getShowTime(), super.getTicketPrice());
    }
    
    @Override
    public String toString() {
        return super.toString() + "Room: " + roomString + "\n\n";
    }

    public boolean equals(MovieShowWithShowRoom movieShowWithShowRoom) {
        return super.equals(movieShowWithShowRoom) && roomString.equals(movieShowWithShowRoom.roomString);
    }
}

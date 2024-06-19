package ticketbooking.movieinfo;
import ticketbooking.movieinfo.showtime.ShowTime;

/**
 * Represents a movie show, which is a specific instance of a movie with a show time and ticket price.
 * Inherits from the Movie class.
 */
public class MovieShow extends Movie {

    private ShowTime showTime;
    private int ticketPrice;

    /**
     * Constructs a MovieShow object with the specified title, length, show time, and ticket price.
     *
     * @param title       the title of the movie
     * @param length      the length of the movie in minutes
     * @param showTime    the show time of the movie
     * @param ticketPrice the ticket price of the movie
     */
    public MovieShow(String title, int length, String showTime, int ticketPrice) {
        super(title, length);
        this.showTime = new ShowTime(showTime);
        this.ticketPrice = ticketPrice;
    }

    /**
     * Constructs a MovieShow object with the specified title, length, show time, and ticket price.
     *
     * @param title       the title of the movie
     * @param length      the length of the movie in minutes
     * @param showTime    the show time of the movie
     * @param ticketPrice the ticket price of the movie
     */
    public MovieShow(String title, int length, ShowTime showTime, int ticketPrice) {
        super(title, length);
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    /**
     * Constructs a MovieShow object with the specified movie, show time, and ticket price.
     *
     * @param movie       the movie object
     * @param showTime    the show time of the movie
     * @param ticketPrice the ticket price of the movie
     */
    public MovieShow(Movie movie, ShowTime showTime, int ticketPrice) {
        super(movie);
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    /**
     * Constructs a MovieShow object with the same properties as the specified MovieShow object.
     *
     * @param movieShow the MovieShow object to copy
     */
    public MovieShow(MovieShow movieShow) {
        super(movieShow);
        this.showTime = movieShow.showTime;
        this.ticketPrice = movieShow.ticketPrice;
    }

    /**
     * Returns the show time of the movie show.
     *
     * @return the show time
     */
    public ShowTime getShowTime() {
        return showTime;
    }

    /**
     * Returns the ticket price of the movie show.
     *
     * @return the ticket price
     */
    public int getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Sets the ticket price of the movie show.
     *
     * @param ticketPrice the ticket price to set
     */
    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Returns a string representation of the movie show.
     *
     * @return a string representation of the movie show
     */
    @Override
    public String toString() {
        return super.toString() +
                "Show Time: " + showTime + "\n" +
                "Ticket Price: " + ticketPrice + "\n";
    }

    /**
     * Checks if the specified MovieShow object is equal to this MovieShow object.
     *
     * @param movieShow the MovieShow object to compare
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(MovieShow movieShow) {
        return getTitle().equals(movieShow.getTitle()) &&
                getLength() == movieShow.getLength() &&
                showTime.equals(movieShow.showTime) &&
                ticketPrice == movieShow.ticketPrice;
    }

    /**
     * Returns a new Movie object with the same title and length as this MovieShow object.
     *
     * @return a new Movie object
     */
    public Movie getMovie() {
        return new Movie(getTitle(), getLength());
    }
}

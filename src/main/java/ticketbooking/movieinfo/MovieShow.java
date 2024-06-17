package ticketbooking.movieinfo;
import ticketbooking.movieinfo.showtime.ShowTime;

public class MovieShow extends Movie{

    private ShowTime showTime;
    private int ticketPrice;

    public MovieShow(String title, int length, String showTime, int ticketPrice) {
        super(title, length);
        this.showTime = new ShowTime(showTime);
        this.ticketPrice = ticketPrice;
    }

    public MovieShow(String title, int length, ShowTime showTime, int ticketPrice) {
        super(title, length);
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    public MovieShow(Movie movie, ShowTime showTime, int ticketPrice) {
        super(movie);
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    public MovieShow(MovieShow movieShow) {
        super(movieShow);
        this.showTime = movieShow.showTime;
        this.ticketPrice = movieShow.ticketPrice;
    }

    public ShowTime getShowTime() {
        return showTime;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Show Time: " + showTime + "\n" +
                "Ticket Price: " + ticketPrice + "\n";
    }

    public boolean equals(MovieShow movieShow) {
        return getTitle().equals(movieShow.getTitle()) &&
                getLength() == movieShow.getLength() &&
                showTime.equals(movieShow.showTime) &&
                ticketPrice == movieShow.ticketPrice;
    }

    public Movie getMovie() {
        return new Movie(getTitle(), getLength());
    }
}

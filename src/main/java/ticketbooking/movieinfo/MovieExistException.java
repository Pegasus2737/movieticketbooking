package ticketbooking.movieinfo;

public class MovieExistException extends Exception{
    public MovieExistException(Movie movie) {
        super("Movie " + movie.getTitle() + " already exists");
    }
}

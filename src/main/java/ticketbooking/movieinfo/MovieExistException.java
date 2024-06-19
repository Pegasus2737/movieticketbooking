package ticketbooking.movieinfo;

/**
 * Exception thrown when attempting to add a movie that already exists.
 */
public class MovieExistException extends Exception {
    /**
     * Constructs a new MovieExistException with the specified movie.
     *
     * @param movie the movie that already exists
     */
    public MovieExistException(Movie movie) {
        super("Movie " + movie.getTitle() + " already exists");
    }
}

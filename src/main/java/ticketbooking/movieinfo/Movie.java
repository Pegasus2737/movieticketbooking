package ticketbooking.movieinfo;

/**
 * The Movie class represents a movie with a title and length in minutes.
 */
public class Movie {
    private String title;
    private int length; // in minutes

    /**
     * Constructs a Movie object with the specified title and length.
     *
     * @param title  the title of the movie
     * @param length the length of the movie in minutes
     */
    public Movie(String title, int length) {
        this.title = title;
        this.length = length;
    }

    /**
     * Constructs a Movie object by copying the properties of another Movie object.
     *
     * @param movie the Movie object to copy
     */
    public Movie(Movie movie) {
        this.title = movie.title;
        this.length = movie.length;
    }

    /**
     * Returns the title of the movie.
     *
     * @return the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the length of the movie in minutes.
     *
     * @return the length of the movie in minutes
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title the title of the movie
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the length of the movie in minutes.
     *
     * @param length the length of the movie in minutes
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns a string representation of the Movie object.
     *
     * @return a string representation of the Movie object
     */
    @Override
    public String toString() {
        return "Title: " + title + "\nLength: " + length + "\n";
    }
}
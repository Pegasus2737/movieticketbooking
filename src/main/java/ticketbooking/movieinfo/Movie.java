package ticketbooking.movieinfo;

public class Movie {
    private String title;
    private int length; // in minutes

    public Movie(String title, int length) {
        this.title = title;
        this.length = length;
    }
    public Movie(Movie movie) {
        this.title = movie.title;
        this.length = movie.length;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nLength: " + length + "\n";
    }
}
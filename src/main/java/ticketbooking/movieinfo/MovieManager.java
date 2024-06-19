package ticketbooking.movieinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * The MovieManager class is responsible for managing movie-related operations such as adding movies, finding movies,
 * adding movie shows with showrooms, retrieving movies, retrieving movie shows, and validating movie show with showroom.
 * It also provides a method to display exceptions using a message dialog.
 */
public class MovieManager {

    /**
     * Adds a new movie to the movie list.
     *
     * @param movie the movie to be added
     * @throws IOException if an I/O error occurs while creating or writing to the file
     * @throws MovieExistException if the movie already exists in the movie list
     */
    public static void addMovie(Movie movie) {
        try {
            // Append the new movie details to the file
            try {
                // Check if file exists
                BufferedReader reader = new BufferedReader(new FileReader("target/movielist.txt"));
                reader.close();
            } catch (FileNotFoundException e) {
                // File does not exist, so create a new file
                File file = new File("target/movielist.txt");
                file.createNewFile();
            }
            // Check if movie already exists
            Movie foundMovie = findMovie(movie.getTitle());
            if (foundMovie != null) {
                throw new MovieExistException(foundMovie);
            }
            File file = new File("target/" + movie.getTitle() + ".txt");
            file.createNewFile();
            FileWriter myWriter = new FileWriter("target/movielist.txt", true);
            myWriter.write(movie.toString());
            myWriter.close();
        } catch (IOException e) {
            showException(e);  
        } catch (MovieExistException e) {
            showException(e);  
        }
    }

    /**
     * Finds a movie with the specified title.
     * 
     * @param title the title of the movie to find
     * @return the Movie object with the specified title, or null if no such movie is found
     */
    public static Movie findMovie(String title) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("target/movielist.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title: " + title)) {
                    int length = Integer.parseInt(reader.readLine().split(": ")[1]);
                    reader.close();
                    return new Movie(title, length);
                }
            }
            reader.close();
        } catch (IOException e) {
            showException(e);  
        }
        return null;
    }

    /**
     * Adds a movie show with show room to the system.
     * If the movie show file already exists, the method reads the existing shows, adds the new show, and sorts them by show time.
     * If the movie show file does not exist, the method creates a new file and adds the show to it.
     * The method then writes the shows to the file.
     *
     * @param movieShowWithShowRoom The movie show with show room to be added.
     */
    public static void addMovieShowWithShowRoom(MovieShowWithShowRoom movieShowWithShowRoom) {
        try {
            ArrayList<MovieShowWithShowRoom> shows = new ArrayList<>();
            try {
                // Check if file exists
                BufferedReader reader = new BufferedReader(new FileReader("target/" + movieShowWithShowRoom.getTitle() + ".txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Show Time: ")) {
                        String showTime = line.split(": ")[1];
                        int ticketPrice = Integer.parseInt(reader.readLine().split(": ")[1]);
                        MovieShow movieShow = new MovieShow(movieShowWithShowRoom.getTitle(), movieShowWithShowRoom.getLength(), showTime, ticketPrice);
                        String room = reader.readLine().split(": ")[1];
                        shows.add(new MovieShowWithShowRoom(movieShow, room));
                    }
                }
                reader.close();
                shows.add(movieShowWithShowRoom);
                shows.sort((o1, o2) -> o1.getShowTime().compareTo(o2.getShowTime()));
            } catch (FileNotFoundException e) {
                // File does not exist, so create a new file
                File file = new File("target/" + movieShowWithShowRoom.getTitle() + ".txt");
                file.createNewFile();
                shows.add(movieShowWithShowRoom);
            }
            // Write shows to file
            FileWriter myWriter = new FileWriter("target/" + movieShowWithShowRoom.getTitle() + ".txt");
            for (MovieShowWithShowRoom show : shows) {
                myWriter.write(show.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            showException(e);  
        }
    }

    /**
     * Retrieves a list of movies from a text file and returns them as an ArrayList.
     *
     * @return An ArrayList of Movie objects representing the movies retrieved from the file.
     */
    public static ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("target/movielist.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title: ")) {
                    String title = line.split(": ")[1];
                    int length = Integer.parseInt(reader.readLine().split(": ")[1]);
                    movies.add(new Movie(title, length));
                }
            }
            reader.close();
        } catch (IOException e) {
            showException(e);  
        }
        return movies;
    }

    /**
     * Retrieves a list of movie shows with their corresponding show rooms for a given movie.
     *
     * @param movie The movie for which to retrieve the movie shows.
     * @return An ArrayList of MovieShowWithShowRoom objects representing the movie shows and their show rooms.
     */
    public static ArrayList<MovieShowWithShowRoom> getMovieShows(Movie movie) {
        ArrayList<MovieShowWithShowRoom> movieShows = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("target/" + movie.getTitle() + ".txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Show Time: ")) {
                    String showTime = line.split(": ")[1];
                    int ticketPrice = Integer.parseInt(reader.readLine().split(": ")[1]);
                    MovieShow movieShow = new MovieShow(movie.getTitle(), movie.getLength(), showTime, ticketPrice);
                    String room = reader.readLine().split(": ")[1];
                    movieShows.add(new MovieShowWithShowRoom(movieShow, room));
                }
            }
            reader.close();
        } catch (IOException e) {
            showException(e);  
        }
        return movieShows;
    }

    /**
     * Validates if a movie show with show room exists.
     * 
     * @param movieShowWithShowRoom the movie show with show room to validate
     * @return true if the movie show with show room exists, false otherwise
     */
    public static boolean validateMovieShowWithShowRoom(MovieShowWithShowRoom movieShowWithShowRoom) {
        ArrayList<MovieShowWithShowRoom> shows = getMovieShows(movieShowWithShowRoom.getMovie());
        for (MovieShowWithShowRoom show : shows) {
            if (show.equals(movieShowWithShowRoom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays an error message dialog with the given exception message.
     *
     * @param e the exception to display the message for
     */
    public static void showException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
    }
}


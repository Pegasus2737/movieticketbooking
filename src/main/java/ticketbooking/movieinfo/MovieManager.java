package ticketbooking.movieinfo;

import javax.swing.JOptionPane;  
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MovieManager {

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

    public static boolean validateMovieShowWithShowRoom(MovieShowWithShowRoom movieShowWithShowRoom) {
        ArrayList<MovieShowWithShowRoom> shows = getMovieShows(movieShowWithShowRoom.getMovie());
        for (MovieShowWithShowRoom show : shows) {
            if (show.equals(movieShowWithShowRoom)) {
                return true;
            }
        }
        return false;
    }

    public static void showException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
}
}


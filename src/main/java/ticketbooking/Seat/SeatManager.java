package ticketbooking.Seat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import ticketbooking.movieinfo.MovieManager;
import ticketbooking.movieinfo.MovieShow;
import ticketbooking.movieinfo.MovieShowWithShowRoom;
import ticketbooking.showroom.ShowRoom;
import ticketbooking.showroom.ShowRoomDoesntExistException;
import ticketbooking.showroom.ShowRoomManager;

/**
 * The SeatManager class is responsible for managing the seat chart and booking seats for a movie show.
 * It provides methods to add a seat chart, check seat availability, and book seats.
 */
public class SeatManager {

    /**
     * Adds a seat chart for a given movie show with show room.
     * 
     * @param movieShowWithShowRoom the movie show with show room object
     * @throws ShowRoomDoesntExistException if the show room doesn't exist
     */
    public static void addSeatChart(MovieShowWithShowRoom movieShowWithShowRoom) {
        try {
            String movieinfoString = movieShowWithShowRoom.getTitle() + "-" + movieShowWithShowRoom.getRoomString() + "-" + movieShowWithShowRoom.getShowTime().toString();
            // Create a new file for the seat chart
            File file = new File("target/" + movieinfoString + " seatchart.txt");
            file.createNewFile();
            // Write the seat chart to the file
            ShowRoom showRoom = ShowRoomManager.findShowRoom(movieShowWithShowRoom.getRoomString());
            if(showRoom == null) {
                throw new ShowRoomDoesntExistException(movieShowWithShowRoom.getRoomString());
            }
            FileWriter myWriter = new FileWriter("target/" + movieinfoString + " seatchart.txt");
            for(int i = 0; i < showRoom.getRow(); i++) {
                myWriter.write(i + "\n");
                for(int j = 0; j < showRoom.getColumn(); j++) {
                    myWriter.write("-" + j + ": " + "Available\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            showException(e);
        } catch (ShowRoomDoesntExistException e) {
            showException(e);
        }
    }

    /**
     * Checks if a seat is available in a specific movie show and location.
     *
     * @param movieShowWithShowRoom The movie show and show room information.
     * @param row The row number of the seat.
     * @param column The column number of the seat.
     * @return True if the seat is available, false otherwise.
     */
    public static boolean isSeatAvailable(MovieShowWithShowRoom movieShowWithShowRoom, int row, int column) {
        try {
            ShowRoom showRoom = ShowRoomManager.findShowRoom(movieShowWithShowRoom.getRoomString());
            if(row >= showRoom.getRow() || column >= showRoom.getColumn()) {
                throw new SeatDoesntExistException(row, column);
            }
            String movieinfoString = movieShowWithShowRoom.getTitle() + "-" + movieShowWithShowRoom.getRoomString() + "-" + movieShowWithShowRoom.getShowTime().toString();
            // Read the seat chart file
            BufferedReader reader = new BufferedReader(new FileReader("target/" + movieinfoString + " seatchart.txt"));
            String line;
            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(row + "")) {
                    flag = true;
                    continue;
                }
                if(flag) {
                    if(line.startsWith("-" + column + ": ")) {
                        if(line.split(": ")[1].equals("Available")) {
                            reader.close();
                            return true;
                        }
                        else {
                            reader.close();
                            return false;
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            showException(e);
        } catch (SeatDoesntExistException e) {
            showException(e);
        }
        return false;
    }

    /**
     * Books a seat for a given movie show in a show room.
     *
     * @param movieShowWithShowRoom the movie show with show room information
     * @param row the row number of the seat
     * @param column the column number of the seat
     * @param userid the ID of the user booking the seat
     * @return the authentication code for the booked seat
     * @throws IllegalArgumentException if the movie show with show room is null, invalid, or if the user ID is null or empty
     * @throws SeatDoesntExistException if the specified seat does not exist in the show room
     * @throws SeatAlreadyBookedException if the specified seat is already booked
     */
    public static int bookSeat(MovieShowWithShowRoom movieShowWithShowRoom, int row, int column, String userid) {
        try {
            // Validate the input
            if(movieShowWithShowRoom == null) {
                throw new IllegalArgumentException("MovieShowWithShowRoom is null");
            }
            if(!MovieManager.validateMovieShowWithShowRoom(movieShowWithShowRoom)) {
                throw new IllegalArgumentException("MovieShowWithShowRoom is invalid");
            }
            if(userid == null || userid.isEmpty()) {
                throw new IllegalArgumentException("User ID is null or empty");
            }
            // Start the booking process
            int authcode = (int)(Math.random() * 1000000);
            ShowRoom showRoom = ShowRoomManager.findShowRoom(movieShowWithShowRoom.getRoomString());
            if(row >= showRoom.getRow() || column >= showRoom.getColumn()) {
                throw new SeatDoesntExistException(row, column);
            }
            String movieinfoString = movieShowWithShowRoom.getTitle() + "-" + movieShowWithShowRoom.getRoomString() + "-" + movieShowWithShowRoom.getShowTime().toString();
            // Read the seat chart file
            BufferedReader reader = new BufferedReader(new FileReader("target/" + movieinfoString + " seatchart.txt"));
            String line;
            String content = "";
            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(row + "")) {
                    flag = true;
                    content += line + "\n";
                    continue;
                }
                if(flag && line.startsWith("-" + column + ": ")) {
                    if(line.split(": ")[1].equals("Available")) {
                        content += "-" + column + ": Booked by " + userid + " with authcode " + authcode + "\n";
                    }
                    else {
                        reader.close();
                        throw new SeatAlreadyBookedException(row, column);
                    }
                    flag = false;
                }
                else {
                    content += line + "\n";
                }
            }
            reader.close();
            // Write the updated seat chart to the file
            FileWriter myWriter = new FileWriter("target/" + movieinfoString + " seatchart.txt");
            myWriter.write(content);
            myWriter.close();
            return authcode;
        } catch (IOException e) {
            showException(e);
        } catch (SeatDoesntExistException e) {
            showException(e);
        } catch (SeatAlreadyBookedException e) {
            showException(e);
        }
        return -1;
    }

    /**
     * Displays an error message dialog with the given exception message.
     *
     * @param e the exception to display the message for
     */
    public static void showException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * The main method serves as the entry point for the program.
     * It demonstrates the usage of the SeatManager class by creating a MovieShowWithShowRoom object,
     * checking seat availability, booking a seat, and checking seat availability again.
     *
     * @param args The command-line arguments passed to the program.
     */
    public static void main(String[] args) { //driver code
        MovieShow movieShow = new MovieShow("Hello", 90, "2024-6-16-19-00", 50);
        MovieShowWithShowRoom movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow, "Room1");
        System.out.println(SeatManager.isSeatAvailable(movieShowWithShowRoom, 0, 0));
        int code = SeatManager.bookSeat(movieShowWithShowRoom, 0, 0, "0932512010");
        System.out.println(code);
        System.out.println(SeatManager.isSeatAvailable(movieShowWithShowRoom, 0, 0));
    }
}


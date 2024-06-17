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

public class SeatManager {

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

    public static void showException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) { //driver code
        MovieShow movieShow = new MovieShow("Hello", 90, "2024-6-16-19-00", 50);
        MovieShowWithShowRoom movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow, "Room1");
        System.out.println(SeatManager.isSeatAvailable(movieShowWithShowRoom, 0, 0));
        int code = SeatManager.bookSeat(movieShowWithShowRoom, 0, 0, "0932512010");
        System.out.println(code);
        System.out.println(SeatManager.isSeatAvailable(movieShowWithShowRoom, 0, 0));
    }
}


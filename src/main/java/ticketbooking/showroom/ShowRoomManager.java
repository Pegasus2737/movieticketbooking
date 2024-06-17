package ticketbooking.showroom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ticketbooking.Seat.SeatManager;
import ticketbooking.movieinfo.MovieManager;
import ticketbooking.movieinfo.MovieShow;
import ticketbooking.movieinfo.MovieShowWithShowRoom;

public class ShowRoomManager {

    public static void addShowRoom(ShowRoom showRoom) {
        try {
            // Append the new show room details to the file
            try {
                // Check if file exists
                BufferedReader reader = new BufferedReader(new FileReader("target/showroomlist.txt"));
                reader.close();
            } catch (FileNotFoundException e) {
                // File does not exist, so create a new file
                File file = new File("target/showroomlist.txt");
                file.createNewFile();
            }
            // Check if showroom already exists
            ShowRoom foundShowRoom = findShowRoom(showRoom.getRoomName());
            if (foundShowRoom != null) {
                throw new ShowRoomExistException(foundShowRoom);
            }
            FileWriter myWriter = new FileWriter("target/showroomlist.txt", true);
            myWriter.write(showRoom.toString() + "\n");
            myWriter.close();
        } catch (IOException e) {
            showException(e);
        } catch (ShowRoomExistException e) {
            showException(e);
        }
    }

    public static void setShowRoomAvailability(ShowRoom showRoom, Boolean available) {
        try {
            // Read the showroom list from the file
            BufferedReader reader = new BufferedReader(new FileReader("target/showroomlist.txt"));
            String line;
            String content = "";
            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Room Name: " + showRoom.getRoomName())) {
                    flag = true;
                }
                if (flag && line.startsWith("Available: ")) {
                    line = "Available: " + available;
                    flag = false;
                }
                content += line + "\n";
            }
            reader.close();

            // Write the updated showroom list to the file
            FileWriter myWriter = new FileWriter("target/showroomlist.txt");
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            showException(e);
        }
    }

    public static ShowRoom findShowRoom(String roomName) {
        try {
            // Read the showroom list from the file
            BufferedReader reader = new BufferedReader(new FileReader("target/showroomlist.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Room Name: " + roomName)) {
                    int row = Integer.parseInt(reader.readLine().split(": ")[1]);
                    int column = Integer.parseInt(reader.readLine().split(": ")[1]);
                    Boolean isAvailable = Boolean.parseBoolean(reader.readLine().split(": ")[1]);
                    ShowRoom showRoom = new ShowRoom(roomName, row, column, isAvailable);
                    reader.readLine(); // Skip the "Shows: " line
                    while ((line = reader.readLine()) != null && line.equals("\n") && !line.startsWith("Room Name: ")) {
                        String title = line.split(": ")[1];
                        int length = Integer.parseInt(reader.readLine().split(": ")[1]);
                        String showTime = reader.readLine().split(": ")[1];
                        String ticketPrice = reader.readLine().split(": ")[1];
                        MovieShow movieShow = new MovieShow(title, length, showTime, Integer.parseInt(ticketPrice));
                        try {
                            showRoom.addShow(movieShow);
                        } catch (Exception e) {
                            showException(e);
                        }
                        reader.readLine(); // Skip the empty line
                    }
                    reader.close();
                    return showRoom;
                }
            }
            reader.close();
        } catch (IOException e) {
            showException(e);
        }
        return null;
    }

    public static ArrayList<ShowRoom> getShowRooms() {
        ArrayList<ShowRoom> showRooms = new ArrayList<>();
        try {
            // Read the showroom list from the file
            BufferedReader reader = new BufferedReader(new FileReader("target/showroomlist.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Room Name: ")) {
                    String roomName = line.split(": ")[1];
                    int row = Integer.parseInt(reader.readLine().split(": ")[1]);
                    int column = Integer.parseInt(reader.readLine().split(": ")[1]);
                    Boolean isAvailable = Boolean.parseBoolean(reader.readLine().split(": ")[1]);
                    ShowRoom showRoom = new ShowRoom(roomName, row, column, isAvailable);
                    reader.readLine(); // Skip the "Shows: " line
                    while ((line = reader.readLine()) != null && line.equals("\n") && !line.startsWith("Room Name: ")) {
                        String title = line.split(": ")[1];
                        int length = Integer.parseInt(reader.readLine().split(": ")[1]);
                        String showTime = reader.readLine().split(": ")[1];
                        String ticketPrice = reader.readLine().split(": ")[1];
                        MovieShow movieShow = new MovieShow(title, length, showTime, Integer.parseInt(ticketPrice));
                        try {
                            showRoom.addShow(movieShow);
                        } catch (Exception e) {
                            showException(e);
                        }
                        reader.readLine(); // Skip the empty line
                    }
                    //System.out.println(line);
                    showRooms.add(showRoom);
                }
            }
            reader.close();
        } catch (IOException e) {
            showException(e);
        }
        return showRooms;
    }

    public static void addShow(MovieShowWithShowRoom movieShowWithShowRoom) {
        try {
            // Read the showroom list from the file
            BufferedReader reader = new BufferedReader(new FileReader("target/showroomlist.txt"));
            String line;
            String content = "";
            String roomString = movieShowWithShowRoom.getRoomString();
            ShowRoom showRoom = findShowRoom(roomString);
            if (showRoom == null) {
                reader.close();
                throw new ShowRoomDoesntExistException(roomString);
            }
            showRoom.addShow(movieShowWithShowRoom.getMovieShow());
            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Room Name: " + roomString)) {
                    content += showRoom.toString();
                    flag = true;
                } else if (line.startsWith("Room Name: ")) {
                    flag = false;
                }
                if (!flag) {
                    content += line + "\n";
                }
            }
            reader.close();
            // Write the updated showroom list to the file
            FileWriter myWriter = new FileWriter("target/showroomlist.txt");
            myWriter.write(content);
            myWriter.close();
            SeatManager.addSeatChart(movieShowWithShowRoom);
            MovieManager.addMovieShowWithShowRoom(movieShowWithShowRoom);
        } catch (IOException e) {
            showException(e);
        } catch (ShowRoomDoesntExistException e) {
            showException(e);
        }
    }


    public static void showException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) { //driver codes, test all methods
        ShowRoom showRoom = new ShowRoom("Room1", 10, 10);
        addShowRoom(showRoom);
        ShowRoom showRoom2 = new ShowRoom("Room2", 10, 10);
        addShowRoom(showRoom2);
        // ShowRoom Room1 = findShowRoom("Room 1");
        MovieShow movieShow = new MovieShow("Movie1", 120, "2024-01-01-12-00", 10);
        MovieShowWithShowRoom movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow, "Room1");
        addShow(movieShowWithShowRoom);
        MovieShow movieShow2 = new MovieShow("Movie2", 120, "2024-01-01-12-00", 10);
        movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow2, "Room1");
        addShow(movieShowWithShowRoom);
        MovieShow movieShow3 = new MovieShow("Movie3", 120, "2024-01-01-14-00", 10);
        movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow3, "Room1");
        addShow(movieShowWithShowRoom);
        MovieShow movieShow4 = new MovieShow("Movie1", 120, "2024-01-01-14-00", 10);
        movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow4, "Room2");
        addShow(movieShowWithShowRoom);
        setShowRoomAvailability(showRoom, false);
        ShowRoom foundShowRoom = findShowRoom("Room1");
        System.out.println(foundShowRoom.toString());
    }
}


package ticketbooking.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ticketbooking.movieinfo.Movie;
import ticketbooking.movieinfo.MovieManager;
import ticketbooking.movieinfo.MovieShow;
import ticketbooking.movieinfo.MovieShowWithShowRoom;
import ticketbooking.movieinfo.showtime.ShowTime;
import ticketbooking.showroom.*;

public class EmployeeGUI {
    private JFrame frame;
    private JTextField movieTitleField;
    private JTextField movieLengthField;
    private JTextArea movieOutputArea;

    private JComboBox<Movie> showTimeTitleBox;
    private JTextField showTimeField;
    private JComboBox<ShowRoom> showRoomField;
    private JTextArea ticketPriceField;
    private JTextArea showTimeOutputArea;

    public EmployeeGUI() {
        frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel showRoomPanel = createShowRoomPanel();
        JPanel moviePanel = createMoviePanel();
        JPanel showTimePanel = createShowTimePanel();

        tabbedPane.add("ShowRoom Management", showRoomPanel);
        tabbedPane.add("Movie Management", moviePanel);
        tabbedPane.add("ShowTime Management", showTimePanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createShowRoomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Show Room Name:"));
        JTextField showRoomNameField = new JTextField();
        inputPanel.add(showRoomNameField);
        inputPanel.add(new JLabel("Show Room Rows:"));
        JTextField showRoomRowsField = new JTextField();
        inputPanel.add(showRoomRowsField);
        inputPanel.add(new JLabel("Show Room Columns:"));
        JTextField showRoomColumnsField = new JTextField();
        inputPanel.add(showRoomColumnsField);

        JButton addShowRoomButton = new JButton("Add Show Room");
        addShowRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = showRoomNameField.getText();
                int rows = Integer.parseInt(showRoomRowsField.getText());
                int columns = Integer.parseInt(showRoomColumnsField.getText());
                ShowRoom showRoom = new ShowRoom(roomName, rows, columns);
                ShowRoomManager.addShowRoom(showRoom);
            }
        });
        inputPanel.add(addShowRoomButton);

        JButton setAvailabilityButton = new JButton("Set Availability");
        setAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = showRoomNameField.getText();
                ShowRoom showRoom = ShowRoomManager.findShowRoom(roomName);
                if (showRoom != null) {
                    ShowRoomManager.setShowRoomAvailability(showRoom, true);
                    showRoomField.addItem(showRoom);
                }
            }
        });
        inputPanel.add(setAvailabilityButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createMoviePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Movie Title:"));
        movieTitleField = new JTextField();
        inputPanel.add(movieTitleField);

        inputPanel.add(new JLabel("Movie Length (minutes):"));
        movieLengthField = new JTextField();
        inputPanel.add(movieLengthField);

        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = movieTitleField.getText();
                int length = Integer.parseInt(movieLengthField.getText());
                Movie movie = new Movie(title, length);
                MovieManager.addMovie(movie);
                showTimeTitleBox.addItem(movie);
            }
        });
        inputPanel.add(addMovieButton);

        JButton findMovieButton = new JButton("Find Movie");
        findMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = movieTitleField.getText();
                Movie movie = MovieManager.findMovie(title);
                if (movie != null) {
                    movieOutputArea.setText("Movie found: " + movie.toString());
                } else {
                    movieOutputArea.setText("Movie not found.");
                }
            }
        });
        inputPanel.add(findMovieButton);

        movieOutputArea = new JTextArea();
        movieOutputArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(movieOutputArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createShowTimePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Movie:"));
        showTimeTitleBox = new JComboBox<>();
        try {
            for (Movie movie : MovieManager.getMovies()) {
                showTimeTitleBox.addItem(movie);
            }
        } catch (NullPointerException e) {
            System.out.println("No movies found.");
        }
        inputPanel.add(showTimeTitleBox);

        inputPanel.add(new JLabel("ShowTime (YYYY-MM-DD-HH-MM):"));
        showTimeField = new JTextField();
        inputPanel.add(showTimeField);

        inputPanel.add(new JLabel("Show Room:"));
        showRoomField = new JComboBox<>();
        try {
            for (ShowRoom showRoom : ShowRoomManager.getShowRooms()) {
                if (showRoom.isAvailable()){
                    showRoomField.addItem(showRoom);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No show rooms found.");
        }
        inputPanel.add(showRoomField);

        inputPanel.add(new JLabel("Ticket Price:"));
        ticketPriceField = new JTextArea();
        inputPanel.add(ticketPriceField);

        JButton addShowTimeButton = new JButton("Add ShowTime");
        addShowTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Movie title = (Movie) showTimeTitleBox.getSelectedItem();
                String showTime = showTimeField.getText();
                ShowRoom showRoom = (ShowRoom) showRoomField.getSelectedItem();
                int ticketPrice = Integer.parseInt(ticketPriceField.getText());
                if (showRoom != null) {
                    ShowTime time = new ShowTime(showTime);
                    MovieShow movieShow = new MovieShow(title, time, ticketPrice);
                    MovieShowWithShowRoom movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow, showRoom.getRoomName());
                    ShowRoomManager.addShow(movieShowWithShowRoom);
                }
                else {
                    showTimeOutputArea.setText("Show Room not found.");
                }
            }
        });
        inputPanel.add(addShowTimeButton);

        showTimeOutputArea = new JTextArea();
        showTimeOutputArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(showTimeOutputArea), BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeGUI());
    }
}

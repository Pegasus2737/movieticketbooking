package ticketbooking.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ticketbooking.Seat.SeatManager;
import ticketbooking.movieinfo.Movie;
import ticketbooking.movieinfo.MovieManager;
import ticketbooking.movieinfo.MovieShow;
import ticketbooking.movieinfo.MovieShowWithShowRoom;
import ticketbooking.movieinfo.showtime.ShowTime;

public class CustomerBookingGUI {
    private JFrame frame;
    private JTextField searchMovieTitleField;
    private JPanel movieDetailsArea;
    private JTextField bookMovieTitleField;
    private JTextField bookShowTimeField;
    private JTextField bookRoomField;
    private JTextField bookRowField;
    private JTextField bookColumnField;
    private JTextField bookUserIdField;
    private JLabel bookPriceField;
    private JTextArea bookingStatusArea;

    public CustomerBookingGUI() {
        frame = new JFrame("Customer Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel searchMoviePanel = createSearchMoviePanel();
        JPanel bookSeatPanel = createBookSeatPanel();

        tabbedPane.add("Search Movies", searchMoviePanel);
        tabbedPane.add("Book Seats", bookSeatPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createSearchMoviePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1, 3));
        inputPanel.add(new JLabel("Movie Title:"));
        searchMovieTitleField = new JTextField();
        inputPanel.add(searchMovieTitleField);

        JButton searchMovieButton = new JButton("Search Movie");
        searchMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movieDetailsArea.removeAll();
                String title = searchMovieTitleField.getText();
                Movie movie = MovieManager.findMovie(title);
                if (movie != null) {
                    ArrayList<MovieShowWithShowRoom> movieShows = MovieManager.getMovieShows(movie);
                    for (MovieShowWithShowRoom movieShow : movieShows) {
                        JPanel movieShowPanel = new JPanel();
                        movieShowPanel.setLayout(new GridLayout(2, 1));
                        movieShowPanel.add(new JLabel(movieShow.toString()));
                        JButton bookButton = new JButton("Book");
                        bookButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                bookMovieTitleField.setText(movieShow.getTitle());
                                bookShowTimeField.setText(movieShow.getShowTime().toString());
                                bookRoomField.setText(movieShow.getRoomString());
                                bookPriceField.setText(String.valueOf(movieShow.getTicketPrice()));
                            }
                        });
                        movieShowPanel.add(bookButton);
                        movieDetailsArea.add(movieShowPanel);
                    }
                    movieDetailsArea.setLayout(new GridLayout(movieShows.size(), 1));
                } else {
                    movieDetailsArea.add(new JLabel("Movie not found"));
                }
            }
        });
        inputPanel.add(searchMovieButton);

        movieDetailsArea = new JPanel();

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(movieDetailsArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBookSeatPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Movie Title:"));
        bookMovieTitleField = new JTextField();
        inputPanel.add(bookMovieTitleField);

        inputPanel.add(new JLabel("ShowTime (YYYY-MM-DD-HH-MM):"));
        bookShowTimeField = new JTextField();
        inputPanel.add(bookShowTimeField);

        inputPanel.add(new JLabel("Show Room:"));
        bookRoomField = new JTextField();
        inputPanel.add(bookRoomField);

        inputPanel.add(new JLabel("Row:"));
        bookRowField = new JTextField();
        inputPanel.add(bookRowField);

        inputPanel.add(new JLabel("Column:"));
        bookColumnField = new JTextField();
        inputPanel.add(bookColumnField);

        inputPanel.add(new JLabel("User ID:"));
        bookUserIdField = new JTextField();
        inputPanel.add(bookUserIdField);

        inputPanel.add(new JLabel("Price:"));
        bookPriceField = new JLabel();
        inputPanel.add(bookPriceField);

        JButton bookSeatButton = new JButton("Book Seat");
        bookSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = bookMovieTitleField.getText();
                String showTime = bookShowTimeField.getText();
                String room = bookRoomField.getText();
                int row = Integer.parseInt(bookRowField.getText());
                int column = Integer.parseInt(bookColumnField.getText());
                String userId = bookUserIdField.getText();

                Movie movie = MovieManager.findMovie(title);
                MovieShow movieShow = new MovieShow(movie, new ShowTime(showTime), Integer.parseInt(bookPriceField.getText()));
                MovieShowWithShowRoom movieShowWithShowRoom = new MovieShowWithShowRoom(movieShow, room);
                try {
                    int authcode = SeatManager.bookSeat(movieShowWithShowRoom, row, column, userId);
                    if (authcode == -1) {
                        bookingStatusArea.setText("Seat " + row + "-" + column + " of " + movieShowWithShowRoom + " is not available");
                        return;
                    }
                    bookingStatusArea.setText("Seat " + row + "-" + column + " of " + movieShowWithShowRoom + " booked successfully. Authcode: " + authcode);
                } catch (Exception ex) {
                    bookingStatusArea.setText(ex.getMessage());
                }
            }
        });
        inputPanel.add(bookSeatButton);

        bookingStatusArea = new JTextArea();
        bookingStatusArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bookingStatusArea), BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerBookingGUI());
    }
}

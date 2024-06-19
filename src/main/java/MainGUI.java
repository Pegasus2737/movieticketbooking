import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ticketbooking.gui.CustomerBookingGUI;
import ticketbooking.gui.EmployeeGUI;


/**
 * The MainGUI class represents the main graphical user interface for the Ticket Booking System.
 * It provides options for selecting the user type (Employee or Customer) and launches the corresponding GUI.
 */
public class MainGUI {
    private JFrame frame;

    /**
     * The MainGUI class represents the main graphical user interface for the Ticket Booking System.
     * It provides options for selecting the user type (Employee or Customer) and launches the respective GUI.
     */
    public MainGUI() {
        // check if target folder exists
        try {
            // Check if file exists
            new java.io.File("target").mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Ticket Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(2, 1));

        JLabel label = new JLabel("Select User Type", SwingConstants.CENTER);
        frame.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton employeeButton = new JButton("Employee");
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane PasswordPane = new JOptionPane();   
                String password = PasswordPane.showInputDialog("Enter Password");
                if (password == null || !password.equals("admin")) {
                    JOptionPane.showMessageDialog(frame, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new EmployeeGUI();
                frame.dispose();
            }
        });
        buttonPanel.add(employeeButton);

        JButton customerButton = new JButton("Customer");
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerBookingGUI();
                frame.dispose();
            }
        });
        buttonPanel.add(customerButton);

        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    /**
     * The entry point of the application.
     * Initializes the main GUI window.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}

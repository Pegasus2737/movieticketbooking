import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ticketbooking.gui.CustomerBookingGUI;
import ticketbooking.gui.EmployeeGUI;


public class MainGUI {
    private JFrame frame;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}

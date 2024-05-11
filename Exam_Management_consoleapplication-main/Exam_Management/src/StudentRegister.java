import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRegister extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public StudentRegister() {
        initComponents();
        setTitle("Student Registration");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to maximize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        add(mainPanel);

        JLabel usernameLabel = new JLabel("Username:");
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        mainPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (register(username, password)) {
                    JOptionPane.showMessageDialog(StudentRegister.this, "Registration successful! Welcome, " + username + ".");
                    // Proceed to student home
                    studentHome studentHome = new studentHome(username);
                    studentHome.initComponents(username);
                    dispose(); // Close registration window
                } else {
                    JOptionPane.showMessageDialog(StudentRegister.this, "Registration failed. Please try again.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mainPanel.add(registerButton);
    }

    public boolean register(String username, String password) {
        // Connect to the database and insert the new student's details
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root")) {
            String query = "INSERT INTO studentregister (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if registration is successful
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Return false if an error occurs
        }
    }

    public static void main(String[] args) {
        // Create and display the registration UI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentRegister();
            }
        });
    }
}

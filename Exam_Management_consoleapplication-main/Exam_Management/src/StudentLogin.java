import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public StudentLogin() {
        initComponents();
        setTitle("Student Login");
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

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateCredentials(username, password)) {
                    JOptionPane.showMessageDialog(StudentLogin.this, "Login successful! Welcome, " + username + ".");
                    // Proceed to student home
                    studentHome studentHome = new studentHome(username);
                    studentHome.initComponents(username);
                    dispose(); // Close login window
                } else {
                    JOptionPane.showMessageDialog(StudentLogin.this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mainPanel.add(loginButton);
    }

    private boolean validateCredentials(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root")) {
            String query = "SELECT * FROM studentregister WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }

    public static void main(String[] args) {
        // Create and display the login UI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentLogin();
            }
        });
    }
}

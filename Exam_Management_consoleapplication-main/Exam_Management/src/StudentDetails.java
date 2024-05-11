import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDetails extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField rollNumberField;
    private JTextField nameField;
    private JTextField fatherNameField;
    private JTextField surnameField;
    private JTextField motherNameField;
    private JTextField genderField;
    private JTextField contactNumberField;
    private JTextField emailField;
    private JTextField parentsContactNumberField;
    private JTextField addressField;

    public StudentDetails(String username) {
        initComponents(username);
        setTitle("Student Details");
        setSize(600, 500);
        setMinimumSize(new Dimension(400, 400)); // Set minimum size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents(String username) {
        JPanel mainPanel = new JPanel(new GridLayout(12, 2, 5, 5)); // Increased rows
        JScrollPane scrollPane = new JScrollPane(mainPanel); // Use JScrollPane for large content
        add(scrollPane);

        JLabel rollNumberLabel = new JLabel("Roll Number:");
        mainPanel.add(rollNumberLabel);
        rollNumberField = new JTextField();
        mainPanel.add(rollNumberField);

        JLabel nameLabel = new JLabel("Name:");
        mainPanel.add(nameLabel);
        nameField = new JTextField();
        mainPanel.add(nameField);

        JLabel fatherNameLabel = new JLabel("Father's Name:");
        mainPanel.add(fatherNameLabel);
        fatherNameField = new JTextField();
        mainPanel.add(fatherNameField);

        JLabel surnameLabel = new JLabel("Surname:");
        mainPanel.add(surnameLabel);
        surnameField = new JTextField();
        mainPanel.add(surnameField);

        JLabel motherNameLabel = new JLabel("Mother's Name:");
        mainPanel.add(motherNameLabel);
        motherNameField = new JTextField();
        mainPanel.add(motherNameField);

        JLabel genderLabel = new JLabel("Gender:");
        mainPanel.add(genderLabel);
        genderField = new JTextField();
        mainPanel.add(genderField);

        JLabel contactNumberLabel = new JLabel("Contact Number:");
        mainPanel.add(contactNumberLabel);
        contactNumberField = new JTextField();
        mainPanel.add(contactNumberField);

        JLabel emailLabel = new JLabel("Email:");
        mainPanel.add(emailLabel);
        emailField = new JTextField();
        mainPanel.add(emailField);

        JLabel parentsContactNumberLabel = new JLabel("Parents Contact Number:");
        mainPanel.add(parentsContactNumberLabel);
        parentsContactNumberField = new JTextField();
        mainPanel.add(parentsContactNumberField);

        JLabel addressLabel = new JLabel("Address:");
        mainPanel.add(addressLabel);
        addressField = new JTextField();
        mainPanel.add(addressField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitDetails(username);
            }
        });
        mainPanel.add(submitButton);
    }

    private void submitDetails(String username) {
        String rollNumber = rollNumberField.getText();
        String name = nameField.getText();
        String fatherName = fatherNameField.getText();
        String surname = surnameField.getText();
        String motherName = motherNameField.getText();
        String gender = genderField.getText();
        String contactNumber = contactNumberField.getText();
        String email = emailField.getText();
        String parentsContactNumber = parentsContactNumberField.getText();
        String address = addressField.getText();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root")) {
            String query = "INSERT INTO studentdata (roll_no, name, father_name, surname, mother_name, gender, contact_number, email, parent_contact_number, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rollNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, fatherName);
            preparedStatement.setString(4, surname);
            preparedStatement.setString(5, motherName);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, contactNumber);
            preparedStatement.setString(8, email);
            preparedStatement.setString(9, parentsContactNumber);
            preparedStatement.setString(10, address);
            
            // Print the SQL query before executing
            System.out.println("SQL Query: " + query);
            
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Details inserted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to insert details.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while inserting student details: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentDetails("TestUser");
            }
        });
    }
}

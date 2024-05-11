import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class studentHome extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel welcomeLabel;
    private JButton takeQuizButton;
    private JButton viewResultsButton;
    private JButton addDetailsButton;
    private JButton exitButton;

    public studentHome(String username) {
    	initComponents(username);
        setTitle("Student Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to maximize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public void initComponents(String username) {
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        add(mainPanel);

        welcomeLabel = new JLabel("Welcome to the Student Home, " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel);

        takeQuizButton = new JButton("Take Quiz");
        takeQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTakeQuizButton(username);
            }
        });
        mainPanel.add(takeQuizButton);

        viewResultsButton = new JButton("View Quiz Results");
        viewResultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleViewResultsButton(username);
            }
        });
        mainPanel.add(viewResultsButton);

        addDetailsButton = new JButton("Add Details");
        addDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddDetailsButton(username);
            }
        });
        mainPanel.add(addDetailsButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting application...");
                dispose(); // Close the student home window
            }
        });
        mainPanel.add(exitButton);
    }

    private void handleTakeQuizButton(String username) {
        System.out.println("You selected: Take Quiz");
        AttemptTest attemptTest = new AttemptTest(username);
        attemptTest.initComponents();
    }

    private void handleViewResultsButton(String username) {
        System.out.println("You selected: View Quiz Results");
        ViewResult viewResult = new ViewResult(username);
        viewResult.initComponents(username);
    }

    private void handleAddDetailsButton(String username) {
        System.out.println("You selected: Add Details");
        StudentDetails studentDetails = new StudentDetails(username);
        studentDetails.initComponents(username);
    }

    public static void main(String[] args) {
        // Create and display the student home UI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new studentHome("TestUser");
            }
        });
    }
}

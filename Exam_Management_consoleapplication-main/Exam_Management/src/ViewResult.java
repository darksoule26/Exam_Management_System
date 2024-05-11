import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ViewResult extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel resultLabel;

    public ViewResult(String username) {
        initComponents(username);
        setTitle("View Quiz Results");
        // Set the frame to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public void initComponents(String username) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        resultLabel = new JLabel("", SwingConstants.CENTER);
        mainPanel.add(resultLabel, BorderLayout.CENTER);

        // Fetch and display the quiz results
        fetchAndDisplayResults(username);
    }
    private void fetchAndDisplayResults(String username) {
        StringBuilder resultText = new StringBuilder();
        int totalMarks = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root")) {
            // Fetch all attempted questions and answers by the user
            String attemptedQuery = "SELECT sr.question_id, sr.answer AS given_answer, q.name AS question, q.answer AS correct_answer " +
                                    "FROM studentresult sr " +
                                    "JOIN question q ON sr.question_id = q.id " +
                                    "WHERE sr.username = ?";
            PreparedStatement attemptedStatement = connection.prepareStatement(attemptedQuery);
            attemptedStatement.setString(1, username);
            ResultSet attemptedResultSet = attemptedStatement.executeQuery();

            // Create a formatted HTML text for displaying results
            resultText.append("<html><body>");
            resultText.append("<h2>Results for ").append(username).append(":</h2>");

            // Display each question along with given answer and correct answer
            while (attemptedResultSet.next()) {
                String question = attemptedResultSet.getString("question");
                String givenAnswer = attemptedResultSet.getString("given_answer");
                String correctAnswer = attemptedResultSet.getString("correct_answer");

                resultText.append("<b>Question:</b> ").append(question).append("<br>");
                resultText.append("<b>Given Answer:</b> ").append(givenAnswer).append("<br>");
                resultText.append("<b>Correct Answer:</b> ").append(correctAnswer).append("<br>");

                // Check if the answer is correct and update total marks
                if (givenAnswer != null && givenAnswer.equals(correctAnswer)) {
                    totalMarks++;
                }
                resultText.append("<br>");
            }

            resultText.append("<hr>");
            resultText.append("<h3>Total Marks Obtained:</h3>");
            resultText.append("<p>").append(totalMarks).append("</p>");
            resultText.append("</body></html>");

            resultLabel.setText(resultText.toString());
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching or updating results: " + e.getMessage());
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewResult("TestUser");
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttemptTest extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton nextButton;
    private String username;

    public AttemptTest(String username) {
        this.username = username;
        questions = getQuestions();
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions available to attempt the quiz.");
            return;
        }

        setTitle("Attempt Quiz");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setLayout(new BorderLayout());

        initComponents();

        displayQuestion();
        setVisible(true);
    }

    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM question");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String opt1 = resultSet.getString("opt1");
                String opt2 = resultSet.getString("opt2");
                String opt3 = resultSet.getString("opt3");
                String opt4 = resultSet.getString("opt4");
                String answer = resultSet.getString("answer");

                Question question = new Question(id, name, opt1, opt2, opt3, opt4, answer);
                questions.add(question);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while fetching questions: " + e.getMessage());
        }
        return questions;
    }

    public void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JPanel questionPanel = new JPanel(new BorderLayout());
        mainPanel.add(questionPanel, BorderLayout.CENTER);

        questionLabel = new JLabel();
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        questionPanel.add(optionsPanel, BorderLayout.CENTER);

        optionButtons = new JRadioButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            buttonGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNextButton();
            }
        });
        buttonPanel.add(nextButton);
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getName());
        String[] options = {currentQuestion.getOpt1(), currentQuestion.getOpt2(), currentQuestion.getOpt3(), currentQuestion.getOpt4()};
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
        }
    }

    private void handleNextButton() {
        int selectedOption = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedOption = i + 1;
                break;
            }
        }

        if (selectedOption == -1) {
            JOptionPane.showMessageDialog(null, "Please select an option.");
            return;
        }

        // Store the user's response
        Question currentQuestion = questions.get(currentQuestionIndex);
        storeUserResponse(username, currentQuestion.getId(), selectedOption);

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            JOptionPane.showMessageDialog(null, "Quiz completed. Your responses have been recorded.");
            dispose();
        }
    }

    private void storeUserResponse(String username, String questionId, int userChoice) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root")) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO studentresult (username, question_id, answer) VALUES (?, ?, ?)");
            insertStatement.setString(1, username);
            insertStatement.setString(2, questionId);
            insertStatement.setInt(3, userChoice);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while inserting student result: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AttemptTest("TestUser");
            }
        });
    }
}

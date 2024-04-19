import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateQuestion {

    private Scanner scanner = new Scanner(System.in);

    public Connection getConnection() throws SQLException {
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root");
        return connect;
    }

    public void updateQuestion() {
        try {
            Connection connection = getConnection();

            // Prompt the user to enter the question ID
            System.out.print("Enter question ID: ");
            String id = scanner.nextLine();

            // Retrieve the question from the database based on the ID
            String selectQuery = "SELECT * FROM question WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Display the current details of the question
                System.out.println("Current question details:");
                System.out.println("ID: " + resultSet.getString("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Option 1: " + resultSet.getString("opt1"));
                System.out.println("Option 2: " + resultSet.getString("opt2"));
                System.out.println("Option 3: " + resultSet.getString("opt3"));
                System.out.println("Option 4: " + resultSet.getString("opt4"));
                System.out.println("Answer: " + resultSet.getString("answer"));

                // Prompt the user to enter the updated question details
                System.out.println("Enter updated question details:");
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Option 1: ");
                String opt1 = scanner.nextLine();
                System.out.print("Option 2: ");
                String opt2 = scanner.nextLine();
                System.out.print("Option 3: ");
                String opt3 = scanner.nextLine();
                System.out.print("Option 4: ");
                String opt4 = scanner.nextLine();
                System.out.print("Answer: ");
                String answer = scanner.nextLine();

                // Update the question in the database
                String updateQuery = "UPDATE question SET name = ?, opt1 = ?, opt2 = ?, opt3 = ?, opt4 = ?, answer = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, name);
                updateStatement.setString(2, opt1);
                updateStatement.setString(3, opt2);
                updateStatement.setString(4, opt3);
                updateStatement.setString(5, opt4);
                updateStatement.setString(6, answer);
                updateStatement.setString(7, id);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Question updated successfully!");
                } else {
                    System.out.println("Failed to update question.");
                }
            } else {
                System.out.println("Question with ID " + id + " not found.");
            }

            // Close resources
            resultSet.close();
            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error occurred while updating question: " + e.getMessage());
        }
    }
}

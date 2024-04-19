import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteQuestion {

    private Scanner scanner = new Scanner(System.in);

    public Connection getConnection() throws SQLException {
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root");
        return connect;
    }

    public void deleteQuestion() {
        try {
            Connection connection = getConnection();

            // Prompt the user to enter the question ID to delete
            System.out.print("Enter question ID to delete: ");
            String id = scanner.nextLine();

            // Check if the question exists in the database
            String selectQuery = "SELECT * FROM question WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Delete the question from the database
                String deleteQuery = "DELETE FROM question WHERE id = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setString(1, id);

                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Question with ID " + id + " deleted successfully!");
                } else {
                    System.out.println("Failed to delete question with ID " + id);
                }
            } else {
                System.out.println("Question with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while deleting data: " + e.getMessage());
        }
    }
}

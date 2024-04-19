import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAllQuestion {

    public void viewAllQuestions() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root");
            
            // SQL query to retrieve all questions
            String query = "SELECT * FROM question";
            preparedStatement = connection.prepareStatement(query);
            
            // Execute the query
            resultSet = preparedStatement.executeQuery();
            
            // Process the results
            while (resultSet.next()) {
                // Retrieve question details from the result set
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String opt1 = resultSet.getString("opt1");
                String opt2 = resultSet.getString("opt2");
                String opt3 = resultSet.getString("opt3");
                String opt4 = resultSet.getString("opt4");
                String answer = resultSet.getString("answer");

                // Display question details
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Opt1: " + opt1);
                System.out.println("Opt2: " + opt2);
                System.out.println("Opt3: " + opt3);
                System.out.println("Opt4: " + opt4);
                System.out.println("Answer: " + answer);
                System.out.println(); // Add a line break between questions
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching questions: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error occurred while closing resources: " + e.getMessage());
            }
        }
    }
}

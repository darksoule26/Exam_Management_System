import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewQuestion {
	
	 Scanner sc = new Scanner(System.in);

	    public Connection getConnection() throws SQLException {
	        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qems", "root", "root");
	        return connect;
	    }

	    public void adddata() {
	        try {
	            Connection connection = getConnection();
	            System.out.print("ID: ");
	            String id = sc.next();
	            System.out.print("Name: ");
	            String name = sc.next();
	            System.out.print("Opt1: ");
	            String opt1 = sc.next();
	            System.out.print("Opt2: ");
	            String opt2 = sc.next();
	            System.out.print("Opt3: ");
	            String opt3 = sc.next();
	            System.out.print("Opt4: ");
	            String opt4 = sc.next();
	            System.out.print("answer: ");
	            String answer = sc.next();

	            String query = "INSERT INTO question(id,name,opt1,opt2,opt3,opt4,answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement preparedStatement = connection.prepareStatement(query);

	            preparedStatement.setString(1, id);
	            preparedStatement.setString(2, name);
	            preparedStatement.setString(3, opt1);
	            preparedStatement.setString(4, opt2);
	            preparedStatement.setString(5, opt3);
	            preparedStatement.setString(6, opt4);
	            preparedStatement.setString(7, answer);

	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            System.out.println("User Successfully registered!");
	        } catch (SQLException e) {
	            System.out.println("Error occurred while inserting data: " + e.getMessage());
	        }
	    }

}


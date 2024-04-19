import java.util.Scanner;

public class loginAdmin {

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.equals("qems") && password.equals("admin")) {
            System.out.println("Login successful! Welcome, Admin.");
            adminHome adminHome = new adminHome();
            adminHome.initComponents();
        } else {
            System.out.println("Incorrect username or password.");
        }

        scanner.close();
    }
}

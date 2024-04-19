import java.util.Scanner;

public class index {

    public index() {
        initComponents();
    }

    public void initComponents() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the application!");
        System.out.println("1. STUDENT");
        System.out.println("2. ADMIN");
        System.out.println("3. EXIT");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You selected STUDENT.");
                System.out.println("1. LOGIN");
                System.out.println("2. REGISTER");
                System.out.print("Enter your choice: ");
                int studentChoice = scanner.nextInt();
                if (studentChoice == 1) {
                    // Call method to handle student login
                    StudentLogin studentLogin = new StudentLogin();
                    studentLogin.initLogin();
                } else if (studentChoice == 2) {
                    // Call method to handle student registration
                    StudentRegister studentRegister = new StudentRegister();
                    studentRegister.initRegister();
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
                break;
            case 2:
                System.out.println("You selected ADMIN.");
                loginAdmin loginAdmin = new loginAdmin();
                loginAdmin.login();
                break;
            case 3:
                System.out.println("Exiting application...");
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                break;
        }

        scanner.close();
    }
}

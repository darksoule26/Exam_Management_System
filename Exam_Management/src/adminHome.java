import java.util.Scanner;

public class adminHome {

    Scanner scanner = new Scanner(System.in);

    public void initComponents() {
        System.out.println("Welcome to the Admin Home!");
        System.out.println("1. Add New Question");
        System.out.println("2. Update Question");
        System.out.println("3. View All Questions");
        System.out.println("4. Delete Question");
        System.out.println("5. View All Student Results");
        System.out.println("6. Logout");
        System.out.println("7. Exit");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You selected: Add New Question");
                AddNewQuestion addNewQuestion = new AddNewQuestion();
                addNewQuestion.adddata();
                break;
            case 2:
                System.out.println("You selected: Update Question");
                UpdateQuestion updateQuestion = new UpdateQuestion();
                updateQuestion.updateQuestion();
                break;
            case 3:
                System.out.println("You selected: View All Questions");
                ViewAllQuestion viewAllQuestion = new ViewAllQuestion();
                viewAllQuestion.viewAllQuestions();
                break;
            case 4:
                System.out.println("You selected: Delete Question");
                DeleteQuestion deleteQuestion = new DeleteQuestion();
                deleteQuestion.deleteQuestion();
                break;
            case 5:
                System.out.println("You selected: View All Student Results");
                // Add code for viewing all student results here
                break;
            case 6:
                System.out.println("Logging out...");
                // Add code for logout here
                break;
            case 7:
                System.out.println("Exiting application...");
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                break;
        }
    }
}

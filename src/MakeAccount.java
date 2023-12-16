import java.util.Scanner;

public class MakeAccount {
    NormalUser normalUser = new NormalUser();
    public void getInformation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name:");
        normalUser.setName(scanner.nextLine());
        String email;
        do {
            System.out.println("Email:");
            email = scanner.nextLine();
            if (email.contains("@") && email.endsWith(".com")) {
                normalUser.setEmail(email);
            } else {
                System.out.println("Incorrect email!!");
            }
        }while (!email.contains("@") && !email.endsWith(".com"));
        System.out.println("Age: ");
        normalUser.setAge(scanner.nextInt());
        System.out.println("Password: ");
        normalUser.setPassword(scanner.nextLine());
        normalUser.setUserID(LibraryManagementSystem.allusers.getLast().getUserID() + 1);
        LibraryManagementSystem.addUser(normalUser);
    }

}

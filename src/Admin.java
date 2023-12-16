import java.util.Iterator;
public class Admin extends User{
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public Admin(String name, String email, int age, String userID, String password, String status) {
        super(name, email, age, userID, password);
        this.status = status;
    }

    @Override
    public void displayInfo() {
        System.out.println("ID : " + getUserID());
        System.out.println("Name : " + getName());
        System.out.println("Status : " + status);
    }

    public static void deleteBookfromList(String bookId){
        Iterator<Books> iterator = LibraryManagementSystem.allbooks.iterator();
        if (LibraryManagementSystem.allbooks.isEmpty()){
            System.out.println("Sorry, at the moment we don't have any book.");
        }
        while (iterator.hasNext()){
            Books book = iterator.next();
            if (book.getBookId().equals(bookId)){
                iterator.remove();
                System.out.println("This book was deleted from list");
            }
        }
    }
}

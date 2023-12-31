import java.util.Iterator;
public class Admin extends User{
    private String status;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public Admin(int userID,String name, String email, int age, String password, String status) {
        super( userID,name, email, age, password);
        this.status = status;
    }

    public static void deleteBookfromList(int bookId){
        Iterator<Books> iterator = LibraryManagementSystem.allbooks.iterator();
        if (LibraryManagementSystem.allbooks.isEmpty()){
            System.out.println("Sorry, at the moment we don't have any book.");
        }
        while (iterator.hasNext()){
            Books book = iterator.next();
            if (book.getBookId() == (bookId)){
                iterator.remove();
                System.out.println("This book was deleted from list");
            }
        }
    }


}

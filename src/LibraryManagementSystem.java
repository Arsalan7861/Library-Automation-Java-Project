import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LibraryManagementSystem {

    public static List<Books> allbooks = new ArrayList<>();
    public ArrayList<User> borrowers = new ArrayList<>();
    public static List<NormalUser> allusers = new ArrayList<>();
    private ArrayList<Transaction> alltransaction = new ArrayList<>();

    public static List<Books> getAllbooks() {
        return allbooks;
    }

    public static void addUser(NormalUser user) {
        List<NormalUser> existingUsers = readUsersFromFile("users.txt");
        allusers.add(user);
        writeUsersToFile(allusers,"users.txt");
    }

    public void addBorrower(Borrowers borrower) {
        if (borrowers.contains(borrower)) {
            System.out.println("This user has already borrowed a book.");
        } else {
            borrowers.add(borrower);
        }
    }

    public static void addBook(Books book) {
        allbooks.add(book);

    }

    public void borrowBook(Borrowers borrower, Books book, LocalDate borrowdate) {
        if (!allbooks.contains(book)) {
            System.out.println("We apologize.There is no book you're searching");
            return;
        }
        if (!allusers.contains(borrower)) {
            System.out.println("This user is not found in the system");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Unfortunately, the book is not available for now");
            return;
        }
        Transaction transaction = new Transaction(borrower, book, borrowdate);
        alltransaction.add(transaction);
        addBorrower(borrower);

        book.checkout();

        BorrowedBooks.borrowBook(book);

        System.out.println("Book borrowed successfully");

    }

    public void returnBook(Borrowers borrower, Books book, LocalDate returndate) {
        if (!BorrowedBooks.getBorrowedBooks().contains(book)) {
            System.out.println("Sorry,the book is not currently borrowed by the user");
            return;
        }
        Transaction transaction = findTransaction(borrower, book);
        if (transaction == null) {
            System.out.println("Error.The transaction is not found");
        }
        transaction.setReturndate(returndate);
        book.returnBook();
        BorrowedBooks.returnBook(book);
        borrowers.remove(borrower);
        System.out.println("Book returned successfully.");
    }

    private Transaction findTransaction(Borrowers borrower, Books book) {
        for (Transaction transaction : alltransaction) {
            if (transaction.getBorrower().equals(borrower) && transaction.getBook().equals(book)) {
                return transaction;
            }
        }
        return null;
    }

    public void updateBook(String bookId, String nameofBook, int newYear, String newAuthor, String genre) {
        for (Books book : allbooks) {
            if (bookId.equals(book.getBookId())) {
                book.setTitle(nameofBook);
                book.setYearPublished(newYear);
                book.setAuthor(newAuthor);
                book.setGenre(genre);
            }
        }

    }

    public void writeBorrowers() {
        for (User user : borrowers) {
            System.out.print(user.getUserID() + " ");
            System.out.println(user.getName());
        }
    }
    //Writes user information to the file.
    public static void writeUsersToFile(List<NormalUser> users, String usersFilePath){
        List<NormalUser> existingUsers = readUsersFromFile(usersFilePath);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(usersFilePath,true))){
            for (NormalUser user : users){
                if (!containsUsersWithId(existingUsers,user.getUserID())){
                    writer.write(user.getUserID() + ", " + user.getName() + ", " + user.getEmail() + "," + user.getAge() + "," + user.getPassword());
                    writer.newLine();
                }
            }
            }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //Used in writeUsersToFile method checking the user ID if they are equal or not.
    private static boolean containsUsersWithId(List<NormalUser> users, int userId) {
        for (NormalUser user : users) {
            if (user.getUserID() == userId) {
                return true;
            }
        }
        return false;
    }
    //Read user information from file.
    public static List<NormalUser> readUsersFromFile(String usersFilePath) {
        List<NormalUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int userId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    String password = parts[4];
                    users.add(new NormalUser(userId,name,email,age,password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    //Writes book information to the file.
    public static void writeBooksToFile(List<Books> books, String booksFilePath) {
        List<Books> existingBooks = readBooksFromFile(booksFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFilePath, true))) {
            for (Books book : books) {
                if (!containsBookWithId(existingBooks, book.getBookId())) {
                    writer.write(book.getBookId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre() + "," + book.isAvailable() + "," + book.getYearPublished() + ",");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Used in writeBooksToFile method checking the user ID if they are equal or not.
    private static boolean containsBookWithId(List<Books> books, String bookId) {
        for (Books book : books) {
            if (book.getBookId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }
    //Reads book information from file.
    public static List<Books> readBooksFromFile(String booksFilePath) {
        List<Books> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(booksFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String bookId = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    boolean isAvailable = Boolean.parseBoolean(parts[4]);
                    int yearPublished = Integer.parseInt(parts[5]);
                    books.add(new Books(bookId, title, author, genre, isAvailable, yearPublished));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
    //When logging in checks the userId and the password if it is correct or not.
    public static boolean authenticateUserbyPassword(int userId,String password){
        for (NormalUser user : allusers){
            if (user.getUserID() == userId && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    //Gives an ID to new user.
    public static int generateUserId(){
        int newUserId;
        if (allusers.isEmpty()){
            newUserId = 1;
        }
        else {
            newUserId = allusers.getLast().getUserID() + 1;
        }
        return newUserId;
    }
}

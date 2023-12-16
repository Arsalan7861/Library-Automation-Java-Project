import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LibraryManagementSystem {

    public static List<Books> allbooks = new ArrayList<>();
    public ArrayList<User> borrowers = new ArrayList<>();
    public static ArrayList<User> allusers = new ArrayList<>();
    private ArrayList<Transaction> alltransaction = new ArrayList<>();

    public static List<Books> getAllbooks() {
        return allbooks;
    }

    public static void addUser(NormalUser user) {
        allusers.add(user);

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

    public static void writeBooksToFile(List<Books> books, String filePath) {
        List<Books> existingBooks = readBooksFromFile(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
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

    public static List<Books> readBooksFromFile(String filePath) {
        List<Books> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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


    private static boolean containsBookWithId(List<Books> books, String bookId) {
        for (Books book : books) {
            if (book.getBookId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }
}

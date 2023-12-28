import java.util.ArrayList;
import java.util.List;

public class BorrowedBooks extends Books {
    public static List<Books> borrowedBooks = new ArrayList<>();//Borrowed Books' ArrayList.

    public BorrowedBooks(String title, String author, String isbn, String genre, boolean available, int yearPublished) {
        super(title, author, isbn, genre, available, yearPublished);
    }

    public static List<Books> getBorrowedBooks() {
        return borrowedBooks;
    }
    //Add borrowed books to the ArrayList.
    public static void borrowBook(Books book) {
        borrowedBooks.add(book);
    }
    public static void returnBook(Books book) {
        borrowedBooks.remove(book);
    }
}

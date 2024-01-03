import java.util.ArrayList;
public class BorrowedBooks extends Books {
    public static ArrayList<Books> borrowedBooks = new ArrayList<>();//Borrowed Books' ArrayList.

    public BorrowedBooks(int bookId, String title, String author, String genre, boolean available, int yearPublished) {
        super(bookId, author, title, genre, available, yearPublished);
    }

    //Add borrowed books to the ArrayList.
    public static void addBorrowBook(Books book) {
        borrowedBooks.add(book);
    }
    //Remove borrowed books from the ArrayList.
    public static void returnBook(Books book) {
        borrowedBooks.remove(book);
    }
}

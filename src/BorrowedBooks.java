import java.util.ArrayList;
import java.util.List;

public class BorrowedBooks extends Books {
    public static List<Books> borrowedBooks = new ArrayList<>();//Borrowed Books' ArrayList.

    public BorrowedBooks(int bookId, String title, String author, String genre, boolean available, int yearPublished) {
        super(bookId, author, title, genre, available, yearPublished);
    }

    public static List<Books> getBorrowedBooks() {
        return borrowedBooks;
    }
    //Add borrowed books to the ArrayList.
    public static void borrowBook(Books book) {
        borrowedBooks.add(book);
    }
    //Remove borrowed books from the ArrayList.
    public static void returnBook(Books book) {
        borrowedBooks.remove(book);
    }

    //Find books by ID.
    public static Books findBorrowedBookById(int id){
        for (Books book :  borrowedBooks){
            if (book.getBookId() == (id)){
                return book;
            }
        }
        return null;
    }
}

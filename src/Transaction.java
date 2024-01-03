import java.time.LocalDate;
import java.util.ArrayList;

public class Transaction {
    private NormalUser borrower;
    private Books book;
    private LocalDate borrowdate;
    private LocalDate returndate;

    public NormalUser getBorrower() {
        return borrower;
    }
    public Books getBook() {
        return book;
    }
    public LocalDate getBorrowdate() {
        return borrowdate;
    }
    public LocalDate getReturndate() {
        return returndate;
    }
    public void setReturndate(LocalDate returndate){
        this.returndate=returndate;
    }
    public Transaction(NormalUser borrower, Books book, LocalDate borrowdate){
        this.borrower = borrower;
        this.book = book;
        this.borrowdate = borrowdate;
    }
    //Removes the returned book from the ArrayList and updates the Transaction file.
    public static void removeTransaction(int userId, int bookId){
        ArrayList<Transaction> updatedTransactions = LibraryManagementSystem.readTransactionsFromFile(LibraryManagementApp.transactionsFilePath, userId, bookId);
        LibraryManagementSystem.writeTransactionsToFile(updatedTransactions, LibraryManagementApp.transactionsFilePath);
    }
}

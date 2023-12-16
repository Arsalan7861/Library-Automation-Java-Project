import java.time.LocalDate;
public class Transaction {
    private Borrowers borrower;
    private Books book;

    public Borrowers getBorrower() {
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

    private LocalDate borrowdate;
    private LocalDate returndate;
    public void setReturndate(LocalDate returndate){
        this.returndate=returndate;
    }
    public Transaction(Borrowers borrower, Books book, LocalDate borrowdate){
        this.borrower = borrower;
        this.book = book;
        this.borrowdate = borrowdate;

    }

    public boolean isLAte(){
        if (returndate!=null && returndate.isAfter(borrowdate.plusDays(14))){
            return true;
        }
       return false;
    }
    public double calculateTax(){
        if (isLAte()){
            return 0.5*(returndate.getDayOfMonth()-borrowdate.getDayOfMonth());
        }
        return 0.0;
    }
}

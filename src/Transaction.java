import java.time.LocalDate;
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
    public boolean isLAte(){
        if (returndate!=null && returndate.isAfter(borrowdate.plusDays(14))){
            return true;
        }
       return false;
    }
}

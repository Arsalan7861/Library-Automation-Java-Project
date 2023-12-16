public class Books{
    private String title;
    private String author;
    private String bookId;//Book ID number.
    private String genre;//genre of the book.
    private boolean available;
    private int yearPublished;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setBookId(String isbn) {
        this.bookId = isbn;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    public String getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public boolean isAvailable() {
        return available;
    }
    public String getGenre() {
        return genre;
    }
    public int getYearPublished() {
        return yearPublished;
    }


    public Books(String bookId, String title, String author, String genre, boolean available, int yearPublished) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = available;
        this.yearPublished = yearPublished;
    }
    public void checkout(){
        if (available){
            available = false;
        }
        else {
            System.out.println("We apologize.The book is not available");
        }
    }
    public void returnBook(){
        if (!available){
            available = true;
        }
        else {
            System.out.println("The book is already available");
        }
    }
}
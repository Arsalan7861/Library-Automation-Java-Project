public class Books{
    private String title;
    private String author;
    private int bookId;//Book ID number.
    private String genre;//genre of the book.
    private boolean available;
    private int yearPublished;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    public int getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public int getYearPublished() {
        return yearPublished;
    }

    public Books(int bookId, String title, String author, String genre, boolean available, int yearPublished) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = available;
        this.yearPublished = yearPublished;
    }
    //Shows the book's availability.
    public boolean isAvailable() {
        return available;
    }
    //Checks the availability of the book.
    public void checkout(){
        if (available){
            available = false;
        }
        else {
            System.out.println("We apologize.The book is not available");
        }
    }
    //Makes the returned book available.
    public void returnBook(){
        if (!available){
            available = true;
        }
        else {
            System.out.println("The book is already available");
        }
    }

    @Override
    public String toString() {
        return "Book ID = '" + bookId + '\'' +
                ", Title = '" + title + '\'' +
                ", Author = '" + author + '\'' +
                ", Genre = '" + genre + '\'' +
                ", Available = " + available +
                ", Published Year = " + yearPublished;
    }
}
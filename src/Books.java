public class Books{
    private int bookId;//Book ID number.
    private String title;
    private String author;
    private String genre;//genre of the book.
    private boolean available;
    private int yearPublished;

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
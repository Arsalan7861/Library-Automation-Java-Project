import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchBook {
    public static List<Books> searchBooksByYear(ArrayList<Books> books, int year){
        return books.stream().filter(book-> book.getYearPublished()>=year)
                .collect(Collectors.toList());
    }
    public static void searchBooksByGenre(ArrayList<Books> books, String genre){
        for (Books book : books){
            if (book.getGenre().equalsIgnoreCase(genre))
                System.out.print(book.getTitle() + "\t");
        }
    }
    public static List<Books> searchBooksByTitle(char start, char end){
        return LibraryManagementSystem.getAllbooks().stream().filter(books -> {String title = books.getTitle().toLowerCase();
                                                 char firstchar = title.charAt(0);
                                                 return firstchar >= Character.toLowerCase(start) && firstchar<=Character.toLowerCase(end);
                                              }).collect(Collectors.toList());
    }

    public static void findBooksbyAuthors(String author){
        for (Books book : LibraryManagementSystem.allbooks){
            if (book.getAuthor().equalsIgnoreCase(author)){
                System.out.print(book.getBookId() + " ");
                System.out.println(book.getTitle());
            }
        }
    }
}

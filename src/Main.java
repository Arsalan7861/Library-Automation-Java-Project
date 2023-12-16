import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "Books.txt";
        Books book1 = new Books("0001","The tale of two cities","Charles Dickens","historical fiction",true,1859);
        LibraryManagementSystem.addBook(book1);
        Books book2 = new Books("0002","The little prince","Antoine de SAint Exupery","Fantasy",true,1943);
        LibraryManagementSystem.addBook(book2);
        Books book3 = new Books("0003"," Hobbit","J.R TolkienThe","Fantasy",true,1937);
        LibraryManagementSystem.addBook(book3);
        Books book4 = new Books("0004","The Alchemist","Paulo Coelho","Fantasy",true,1988);
        LibraryManagementSystem.addBook(book4);
        Books book5 = new Books("The Bridges of Madison County","Robert Waller","0005","Romance",true,1992);
        Books book6 = new Books("Black Beauty","Anna Sewel","0006","Novel",true,1877);
        Books book7 = new Books("The Ginger Man","J. Donleavy","0007","Novel",true,1955);
        Books book8 = new Books("The day lasts more than 100 years","Chyngyz Aytmatov","0008","Romance",true,1975);
        Books book9 = new Books("The first Teacher","Chyngyz Aytmatov","0009","Novel",true,1987);
        Books book10 = new Books("Crime and Punishment","Fedor Dostoevsky","0010","Romance",true,1889);


        LibraryManagementSystem libraryManagementSystem = new LibraryManagementSystem();


//        System.out.println("//Janra gore kitap arama (Fantasy) ");
//        SearchBook.searchBooksByGenre(LibraryManagementSystem.allbooks,"Fantasy");
//        System.out.println();
//        System.out.println("//Yilina gore kitap arama ");
//        List<Books> byYearbooks = SearchBook.searchBooksByYear(LibraryManagementSystem.allbooks,1948);
//        for (Books book : byYearbooks){
//            System.out.println(book.getTitle());
//        }
//        System.out.println("//Ilk ve son harfine gore kitap arama");
//        List<Books> bytitleBooks = SearchBook.searchBooksByTitle('A','J');
//        for (Books book : bytitleBooks){
//            System.out.println(book.getTitle());
//        }

        System.out.println("//Yazarin kitaplarini listeleme");
        SearchBook.findBooksbyAuthors("Chyngyz Aytmatov");

        System.out.println("//Kitabi guncelleme");
        libraryManagementSystem.updateBook("0001","Golden fish",1832,"A. Pushkin","Fantasy");
        System.out.println(book1.getTitle());

        System.out.println("//Kitabi Silme");
//        Admin.deleteBookfromList("0002");
//        Admin.deleteBookfromList("0003");
//        Admin.deleteBookfromList("0004");
        for (Books book : LibraryManagementSystem.allbooks){
            System.out.println(book.getBookId() + " " + book.getTitle());
        }
        LibraryManagementSystem.writeBooksToFile(LibraryManagementSystem.allbooks,filePath);

        System.out.println("Read: ");
        List<Books> existingBooks = LibraryManagementSystem.readBooksFromFile(filePath);

        System.out.println("Existing Books:");
        for (Books book : existingBooks) {
            System.out.println(book.getBookId() + "," +
                    book.getTitle() + "," +
                    book.getAuthor() + "," +
                    book.getGenre() + "," +
                    book.isAvailable() + "," +
                    book.getYearPublished());
        }

    }

    }

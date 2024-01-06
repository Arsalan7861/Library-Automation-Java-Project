import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class LibraryManagementSystem{
    public static ArrayList<Books> allBooks = new ArrayList<>();
    public static ArrayList<NormalUser> borrowers = new ArrayList<>();
    public static ArrayList<NormalUser> allUsers = new ArrayList<>();
    public static ArrayList<Transaction> allTransactions = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();

    //Adds user to allusers ArrayList.
    public static void addUser(NormalUser user) {
        ArrayList<NormalUser> existingUsers = readUsersFromFile(LibraryManagementApp.usersFilePAth);
        allUsers.add(user);
        writeUsersToFile(allUsers,LibraryManagementApp.usersFilePAth);
    }
    //Add borrowers to the borrowers ArrayList.
    public static void addBorrower(NormalUser borrower) {
        borrowers.add(borrower);
    }
    //Add books to the allbooks ArrayList.
    public static void addBook(Books book) {
        allBooks.add(book);
    }
    //Records the borrowed books and the borrowers to ArrayLists.
    public static void borrowBook(NormalUser borrower, Books book, LocalDate borrowdate) {
        book.checkout();
        Transaction transaction = new Transaction(borrower, book, borrowdate);
        allTransactions.add(transaction);
        addBorrower(borrower);
        BorrowedBooks.addBorrowBook(book);//Adds borrowed books to the ArrayList in BorrowedBooks' class.
    }
    //Removes returned book and borrower from ArrayList.
    public static void returnBook(NormalUser borrower, Books book) {
        BorrowedBooks.returnBook(book);//Removes returned book from the borrowed books ArrayList.
        borrowers.remove(borrower);//Removes borrower from ArrayList.
    }
    //Writes user information to the file.
    public static void writeUsersToFile(ArrayList<NormalUser> users, String usersFilePath){
        ArrayList<NormalUser> existingUsers = readUsersFromFile(usersFilePath);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(usersFilePath,true))){
            for (NormalUser user : users){
                if (!containsUsersWithId(existingUsers,user.getUserID())){
                    writer.write(user.getUserID() + ", " + user.getName() + ", " + user.getEmail() + "," + user.getAge() + "," + user.getPassword());
                    writer.newLine();
                }
            }
            }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //Adding write method after deleting a user
    public static void dwriteUsersToFile(ArrayList<NormalUser> users, String usersFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFilePath))) {
            for (NormalUser user : users) {
                writer.write(user.getUserID() + "," + user.getName() + "," + user.getEmail() + "," + user.getAge() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Used in writeUsersToFile method checking the user ID if they are equal or not.
    private static boolean containsUsersWithId(ArrayList<NormalUser> users, int userId) {
        for (NormalUser user : users) {
            if (user.getUserID() == userId) {
                return true;
            }
        }
        return false;
    }
    //Read user information from file.
    public static ArrayList<NormalUser> readUsersFromFile(String usersFilePath) {
        ArrayList<NormalUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int userId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    String password = parts[4];
                    users.add(new NormalUser(userId,name,email,age,password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    //Writes book information to the file.
    public static void writeBooksToFile(ArrayList<Books> books, String booksFilePath) {
        ArrayList<Books> existingBooks = readBooksFromFile(booksFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFilePath,true))) {
            for (Books book : books) {
                if (!containsBookWithId(existingBooks, book.getBookId())) {//Controls the book with book ID if it exists doesn't write it.
                    writer.write(book.getBookId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre() + "," + book.isAvailable() + "," + book.getYearPublished() + ",");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //It writes books after cleaning the file.
    public static void dwriteBooksToFile(ArrayList<Books> books, String booksFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFilePath))) {
            for (Books book : books) {
                writer.write(book.getBookId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre() + "," + book.isAvailable() + "," + book.getYearPublished() + ",");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Used in writeBooksToFile method checking the user ID if they are equal or not.
    private static boolean containsBookWithId(ArrayList<Books> books, int bookId) {
        for (Books book : books) {
            if (book.getBookId() == (bookId)) {
                return true;
            }
        }
        return false;
    }
    //Reads book information from file.
    public static ArrayList<Books> readBooksFromFile(String booksFilePath) {
        ArrayList<Books> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(booksFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int bookId = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    boolean isAvailable = Boolean.parseBoolean(parts[4]);
                    int yearPublished = Integer.parseInt(parts[5]);
                    books.add(new Books(bookId, title, author, genre, isAvailable, yearPublished));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
    //Writes the borrowed books' info to the Transctions.txt file.
    public static void writeTransactionsToFile(ArrayList<Transaction> transactions, String transactionsFilePath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(transactionsFilePath))){
            for (Transaction transaction : transactions){
                writer.write(transaction.getBorrower().getUserID() + "," + transaction.getBook().getBookId() + "," + transaction.getBorrowdate());
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //Reads the transactions' info from the Transaction.txt file.
    public static ArrayList<Transaction> readTransactionsFromFile(String transactionsFilePath) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String userIdString = parts[0];
                    int userId = Integer.parseInt(userIdString);
                    NormalUser borrower = findUserById(userId);
                    Books book = findBookById(Integer.parseInt(parts[1]));
                    LocalDate borrowDate = LocalDate.parse(parts[2]);
                    transactions.add(new Transaction(borrower, book, borrowDate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    //Read the transactions and remove the returned books. Method overload.
    public static ArrayList<Transaction> readTransactionsFromFile(String transactionsFilePath, int userId, int bookId) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String userIdString = parts[0];
                    int userId1 = Integer.parseInt(userIdString);
                    NormalUser borrower = findUserById(userId1);
                    Books book = findBookById(Integer.parseInt(parts[1]));
                    LocalDate borrowDate = LocalDate.parse(parts[2]);
                    transactions.add(new Transaction(borrower, book, borrowDate));//Add it to the ArrayList.
                }
            }
            //Removes existing transaction.
            Iterator<Transaction> iterator = transactions.iterator();
            while (iterator.hasNext()) {
                Transaction tr = iterator.next();
                if (tr.getBorrower().getUserID() == userId && tr.getBook().getBookId() == bookId) {
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    //When the book is borrowed makes it unavailable.
    public static void updateBookAvailabilityInFile(int bookId, boolean isAvailable){
        Path filePath = Paths.get("books.txt");//Gets the path of the Books file.
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);//Reads it and saves it in lines variable.
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < lines.size(); i++){//Looks for the book and changes its availability to true.
            String line = lines.get(i);
            if (line.startsWith(bookId + ",")){
                lines.set(i, line.replaceFirst(",true,", "," + isAvailable +  ","));//Looks for the true and changes it to false.
                break;
            }
        }
        try {
            Files.write(filePath,lines);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //When the book is returned makes it available. Method overloading.
    public static void updateBookAvailabilityInFile(boolean isAvailable, int bookId){
        Path filePath = Paths.get("books.txt");//Gets the path of the Books file.
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);//Reads it and saves it in lines variable.
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < lines.size(); i++){//Looks for the book and changes its availability to false.
            String line = lines.get(i);
            if (line.startsWith(bookId + ",")){
                lines.set(i, line.replaceFirst(",false,", "," + isAvailable +  ","));//Looks for the true and changes it to true.
                break;
            }
        }
        try {
            Files.write(filePath,lines);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //Updates user's name in the file.
    public static void updateUserNameInFile(int userId, String newName) {
        Path filePath = Paths.get("users.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(userId + ",")) {
                    // Assuming the user information is stored as "userId,userName,email,age,password"
                    String[] parts = line.split(",");
                    parts[1] = newName; // Update the user's name
                    lines.set(i, String.join(",", parts));
                    break;
                }
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Updates user email in file.
    public static void updateUserEmailInFile(int userId, String newEmail) {
        Path filePath = Paths.get("users.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(userId + ",")) {
                    // Assuming the user information is stored as "userId,userName,email,age,password"
                    String[] parts = line.split(",");
                    parts[2] = newEmail; // Update the user's email
                    lines.set(i, String.join(",", parts));
                    break;
                }
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Updates user's age in the file.
    public static void updateUserAgeInFile(int userId, int newAge) {
        Path filePath = Paths.get("users.txt"); // Adjust the file path as needed
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(userId + ",")) {
                    // Assuming the user information is stored as "userId,userName,email,age,password"
                    String[] parts = line.split(",");
                    parts[3] = String.valueOf(newAge); // Update the user's email
                    lines.set(i, String.join(",", parts));
                    break;
                }
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    //updates user's password in the file.
    public static void updateUserPasswordInFile(int userId, String newPassword) {
        Path filePath = Paths.get("users.txt"); // Adjust the file path as needed
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(userId + ",")) {
                    // Assuming the user information is stored as "userId,userName,email,age,password"
                    String[] parts = line.split(",");
                    parts[4] = newPassword; // Update the user's email
                    lines.set(i, String.join(",", parts));
                    break;
                }
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    //When logging in checks the userId and the password if it is correct or not.
    public static boolean authenticateUserByPassword(int userId, String password){
        for (NormalUser user : allUsers){
            if (user.getUserID() == userId && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    //Controls admins ID and password when logging in.
    public static boolean authenticateAdminByPassword(int userId, String password){
        for (Admin admin : LibraryManagementSystem.admins){
            if (admin.getUserID() == userId && admin.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    //Searches the user ID from the User file and returns it.
    public static NormalUser findUserById(int userId){
        ArrayList<NormalUser> allusers = readUsersFromFile(LibraryManagementApp.usersFilePAth);
        for (NormalUser user : allusers){
            if (user.getUserID() == userId){
                return user;
            }
        }
        return null;
    }
    //return book from books file by bookId
    public static Books findBorrowedBookById(int bookId) {
        Path filePath = Paths.get("books.txt");
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == bookId) {
                    // Assuming your constructor is (bookId, title, author, genre, available, yearPublished)
                    return new Books(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]), Integer.parseInt(parts[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Book not found
    }
    //Searches book with id.
    public static Books findBookById(int id){
        for (Books book : allBooks){
            if (book.getBookId() == (id)){
                return book;
            }
        }
        return null;
    }
    //Gives an ID to new user.
    public static int generateUserId(){
        int newUserId;
        if (allUsers.isEmpty()){
            newUserId = 101;
        }
        else {
            newUserId = allUsers.getLast().getUserID() + 1;
        }
        return newUserId;
    }
    //Gives an ID to new added book.
    public static int generateBookId(){
        int bookId;
        if (allBooks.isEmpty()){
            bookId = 11;
        }else {
            bookId = allBooks.getLast().getBookId() + 1;
        }
        return bookId;
    }
}

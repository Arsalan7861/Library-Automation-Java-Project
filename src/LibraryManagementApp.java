import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
public class LibraryManagementApp extends JFrame {
    static final String booksFilePath = "books.txt";
    static final String usersFilePAth = "users.txt";
    static final String transactionsFilePath = "Transactions.txt";
    private JButton loginButton;
    private JButton signUpButton;
    public int timelyUserId;//to keep user's ID when logged in.
    public NormalUser thisUser;

    public LibraryManagementApp() {
        setTitle("Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(false);//Turns off the minimize button.
        setVisible(true);

        ImageIcon icon = new ImageIcon("Images/icon.jpg");
        setIconImage(icon.getImage());//Set an icon to the frame.

        ImageIcon loginIcon = new ImageIcon("Images/loginIcon.jpeg");
        loginButton = new JButton("Login");
        loginButton.setIcon(loginIcon);
        loginButton.setIconTextGap(10);
        loginButton.setFocusPainted(false);//Remove the button focus.

        ImageIcon signupIcon = new ImageIcon("Images/signupIcon.jpeg");
        signUpButton = new JButton("Sign Up");
        signUpButton.setIcon(signupIcon);
        signUpButton.setIconTextGap(10);
        signUpButton.setFocusPainted(false);

        Font defaultFont = new Font("Rockwell", Font.PLAIN, 50);

        loginButton.setFont(defaultFont);
        signUpButton.setFont(defaultFont);

        //Adding actionListener to buttons.
        loginButton.addActionListener(e->  openLoginPage());
        signUpButton.addActionListener(e -> openSignUpPage());
        //Adding background image to the panel.
        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Images/millet.jpg");
                // Scale the image to fit the panel.
                Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.paintIcon(this, g, 0, 0);
            }
        };

        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(loginButton);
        mainPanel.add(signUpButton);
        add(mainPanel);
    }
    //When login button is clicked the tasks that are in login page.
    private void openLoginPage() {
        JFrame loginFrame = new JFrame("Login Page");
        loginFrame.setSize(600, 400);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);

        ImageIcon icon = new ImageIcon("Images/icon.jpg");
        loginFrame.setIconImage(icon.getImage());//Set an icon to the frame.

        JTextField userIdField = new JTextField();
        Font textFieldFont = new Font("Arial", Font.PLAIN, 25);
        userIdField.setFont(textFieldFont);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(textFieldFont);

        JButton loginSubmitButton = new JButton("Submit");
        loginSubmitButton.setFocusPainted(false);//Remove the button focus.

        loginSubmitButton.setFont(textFieldFont);

        Font labelFont = new Font("Rockwell", Font.PLAIN, 25);//Set font for the labels.

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(labelFont);
        loginPanel.add(userIdLabel);
        loginPanel.add(userIdField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        loginPanel.add(new JLabel());//Empty label for spacing
        loginPanel.add(loginSubmitButton);
        loginFrame.add(loginPanel);

        loginSubmitButton.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                char[] passwordsChars = passwordField.getPassword();
                String password = new String(passwordsChars);
                if (LibraryManagementSystem.authenticateAdminByPassword(userId, password)){//Controls admins' IDs and passwords.
                    adminPage();
                    loginFrame.dispose();
                }else if (LibraryManagementSystem.authenticateUserbyPassword(userId, password)){
                    timelyUserId = userId;
                    thisUser = LibraryManagementSystem.findUserById(userId);
                    JOptionPane.showMessageDialog(null, "Login successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    openMainApplicationPage();
                    loginFrame.dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "Invalid userId or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                userIdField.setText("");//Clear the userId input.
                passwordField.setText("");//Clear the password input.
            } catch (NumberFormatException ex) {
                // Handle the case where the user entered a non-numeric value for userId
                JOptionPane.showMessageDialog(null, "Please enter a numeric value.", "Invalid User ID", JOptionPane.ERROR_MESSAGE);
                userIdField.setText(""); // Clear the invalid input
                passwordField.setText("");
            }
        });
        loginFrame.getRootPane().setDefaultButton(loginSubmitButton);//Set the default(Enter) button to loginSubmitButton
        loginFrame.setVisible(true);
    }
    //Sign Up page for creating a new account.
    private void openSignUpPage() {
        JFrame signUpFrame = new JFrame("Sign Up Page");
        signUpFrame.setSize(600, 400);
        signUpFrame.setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("Images/icon.jpg");
        signUpFrame.setIconImage(icon.getImage());//Set an icon to the frame.

        Font textFieldFont = new Font("Arial", Font.PLAIN, 25);
        JTextField nameField = new JTextField();
        nameField.setFont(textFieldFont);
        nameField.setToolTipText("Enter your name.");//When hover over it, shows this.
        JTextField emailField = new JTextField();
        emailField.setFont(textFieldFont);
        emailField.setToolTipText("Should include '@' and ends with '.com' ");
        JTextField ageField = new JTextField();
        ageField.setFont(textFieldFont);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(textFieldFont);

        JButton signUpSubmitButton = new JButton("Submit");
        signUpSubmitButton.setFocusPainted(false);//Remove the button focus.
        signUpSubmitButton.setFont(textFieldFont);

        JPanel signUpPanel = new JPanel(new GridLayout(5, 2));
        Font labelFont = new Font("Rockwell", Font.PLAIN, 25);//Set font for the labels.
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        signUpPanel.add(nameLabel);
        signUpPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        signUpPanel.add(emailLabel);
        signUpPanel.add(emailField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        signUpPanel.add(ageLabel);
        signUpPanel.add(ageField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        signUpPanel.add(passLabel);
        signUpPanel.add(passwordField);

        signUpPanel.add(new JLabel());//Empty label for spacing.
        signUpPanel.add(signUpSubmitButton);
        signUpFrame.add(signUpPanel);

        signUpSubmitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String email = null;
                if (emailField.getText().contains("@") && emailField.getText().endsWith(".com")) {//Controlling the email.
                    email = emailField.getText();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong mail. Should contain '@' and ends with '.com'", "Error", JOptionPane.ERROR_MESSAGE);
                    emailField.setText("");
                }
                int age = Integer.parseInt(ageField.getText());
                String password1 = new String(passwordField.getPassword());//Casting password to String to check if it is empty or not.
                if (emailField.getText().isEmpty() || nameField.getText().isEmpty() || password1.isEmpty()) throw new CustomException();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                int userId = LibraryManagementSystem.generateUserId();//Giving new ID to new user.
                NormalUser newUser = new NormalUser(name, email, age, password);
                newUser.setUserID(userId);
                LibraryManagementSystem.addUser(newUser);
                JOptionPane.showMessageDialog(null, "User registrated successfully");
                signUpFrame.dispose();//Closing the signup frame when registration is completed.
                //Clearing the fields to register another user.
                nameField.setText("");
                emailField.setText("");
                ageField.setText("");
                passwordField.setText("");
                JOptionPane.showMessageDialog(null, "Your User Id: " + userId);//Showing user Id to user.
            } catch (CustomException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage() + ": Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                ageField.setText("");//Clearing the field after the error.
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "String value is not accepted in the age field!", "Error", JOptionPane.ERROR_MESSAGE);
                ageField.setText("");//Clearing the field after the error.
            }
        });
        signUpFrame.getRootPane().setDefaultButton(signUpSubmitButton);//Set the default(Enter) button to signUpSubmitButton.
        signUpFrame.setVisible(true);
    }
    //Method for making button for showing books. Used in two frame.
    private void allBooksButton(JPanel panel){
        Font buttonFont = new Font("Rockwell", Font.BOLD, 20);//Set font for the buttons.
        //Making button for seeing the books.
        ImageIcon allBooksIcon = new ImageIcon("Images/booksIcon.jpg");//Icon for the button.
        JButton booksButton = new JButton("All Books");
        booksButton.setFont(buttonFont);
        booksButton.setFocusPainted(false);
        booksButton.setPreferredSize(new Dimension(200, 80));
        booksButton.setIcon(allBooksIcon);//Set icon to button.
        booksButton.setIconTextGap(10);//Set gap between text and icon.
        panel.add(booksButton);
        //Shows the available books when All books' button is clicked. Also includes search books area.
        booksButton.addActionListener(e -> {
            JFrame bookFrame = new JFrame("Books");
            bookFrame.setResizable(false);
            bookFrame.setSize(700, 600);

            ImageIcon icon = new ImageIcon("Images/icon.jpg");
            bookFrame.setIconImage(icon.getImage());//Set an icon to the frame.

            JPanel bookPanel = new JPanel(new BorderLayout());
            bookPanel.setBackground(Color.CYAN);
            bookFrame.add(bookPanel);

            // Add a search bar with JTextField and JButton
            JPanel searchPanel = new JPanel();
            JTextField searchField = new JTextField(20);

            JButton searchButton = new JButton("Search");
            searchButton.setFocusPainted(false);//Turns off focus on button.

            searchPanel.add(new JLabel("Enter Book ID: "));
            bookPanel.add(searchPanel, BorderLayout.NORTH);
            searchPanel.add(searchField);
            searchPanel.add(searchButton);

            JTextArea booksTextArea = new JTextArea();
            booksTextArea.setEditable(false);
            ArrayList<Books> booksList = LibraryManagementSystem.readBooksFromFile(booksFilePath);
            StringBuilder booksText = new StringBuilder();
            for (Books book : booksList) {
                booksText.append(book.toString()).append("\n");
            }
            booksTextArea.setText(booksText.toString());
            bookPanel.add(new JScrollPane(booksTextArea), BorderLayout.CENTER);//for scrolling books

            //When search button is clicked, writes it to the screen.
            searchButton.addActionListener(e1 -> {
                try {
                    String searchText = searchField.getText();
                    if (!searchText.isEmpty()) {
                        // Finds the searched book from the Books file and write it to the screen.
                        Books foundBook = LibraryManagementSystem.findBookById(Integer.parseInt(searchText));
                        if (foundBook != null) {
                            booksTextArea.setText(foundBook.toString());
                        } else {
                            booksTextArea.setText("Book does not exist!");
                        }
                    } else {
                        // If search field is empty, show all books
                        booksTextArea.setText(booksText.toString());
                    }
                }catch (NumberFormatException e2){
                    JOptionPane.showMessageDialog(null, "Can not be characters!", "Error", JOptionPane.ERROR_MESSAGE);
                    searchField.setText("");//Clearing the search field after error.
                }
            });
            bookFrame.getRootPane().setDefaultButton(searchButton);
            bookFrame.setLocationRelativeTo(null);
            bookFrame.setVisible(true);
        });
    }
    //Admin page.
    private void adminPage(){
        JFrame adminFrame = new JFrame("Admin Page");
        adminFrame.setResizable(false);
        adminFrame.setSize(700, 600);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);

        ImageIcon icon = new ImageIcon("Images/icon.jpg");
        adminFrame.setIconImage(icon.getImage());//Set an icon to the frame.
        //Add background to admin panel.
        JPanel adminPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Images/admin.jpg");
                // Scale the image to fit the panel.
                Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.paintIcon(this, g, 0, 0);
            }
        };
        adminFrame.add(adminPanel);

        allBooksButton(adminPanel);//Invokes all books button method

        Font buttonFont = new Font("Rockwell", Font.BOLD, 20);//Set font for the buttons.
        //Adding button that adds the book.
        ImageIcon addBookIcon = new ImageIcon("Images/addBookIcon.jpeg");//Icon for the button.
        JButton addBook = new JButton("Add Book");
        addBook.setFont(buttonFont);
        addBook.setFocusPainted(false);
        addBook.setPreferredSize(new Dimension(200, 80));
        addBook.setIcon(addBookIcon);//Set icon to button.
        addBook.setIconTextGap(10);//Set gap between text and icon.
        adminPanel.add(addBook);
        //When clicked, It opens the frame for adding the book.
        addBook.addActionListener(e -> {
            //Making new frame for adding new book.
            JFrame addBookFrame = new JFrame("Add Book");
            addBookFrame.setResizable(false);
            addBookFrame.setSize(600, 400);
            addBookFrame.setIconImage(icon.getImage());//Set an icon to the frame.

            JPanel addBookPanel = new JPanel(new GridLayout(5, 2));//Adding panel to frame.
            addBookFrame.add(addBookPanel);

            Font labelFont = new Font("Rockwell", Font.PLAIN, 18);//Set font for the labels.
            Font textFieldFont = new Font("Arial", Font.PLAIN, 18);//Set font for textFields.
            Color labelColor = Color.DARK_GRAY;//Set font color.
            //Adding labels and text fields to the panel.
            JLabel nameLabel = new JLabel("Enter Book's Name:");
            nameLabel.setFont(labelFont);
            nameLabel.setForeground(labelColor);
            addBookPanel.add(nameLabel);

            JTextField nameField = new JTextField();
            nameField.setFont(textFieldFont);
            nameField.setToolTipText("Enter Book's name.");//When hover over it, shows this.
            addBookPanel.add(nameField);

            JLabel authorLabel = new JLabel("Enter Book's Author:");
            authorLabel.setFont(labelFont);
            authorLabel.setForeground(labelColor);
            addBookPanel.add(authorLabel);

            JTextField authorField = new JTextField();
            authorField.setFont(textFieldFont);
            authorField.setToolTipText("Enter Book's author.");//When hover over it, shows this.
            addBookPanel.add(authorField);

            JLabel genreLabel = new JLabel("Enter Book's Genre:");
            genreLabel.setFont(labelFont);
            genreLabel.setForeground(labelColor);
            addBookPanel.add(genreLabel);

            JTextField genreField = new JTextField();
            genreField.setFont(textFieldFont);
            genreField.setToolTipText("Enter Book's genre.");//When hover over it, shows this.
            addBookPanel.add(genreField);

            JLabel yearLabel = new JLabel("Enter Book's Published Year:");
            yearLabel.setFont(labelFont);
            yearLabel.setForeground(labelColor);
            addBookPanel.add(yearLabel);

            JTextField yearField = new JTextField();
            yearField.setFont(textFieldFont);
            yearField.setToolTipText("Enter Book's published year.\nCan't be characters");//When hover over it, shows this.
            addBookPanel.add(yearField);

            addBookPanel.add(new JLabel());//Empty label for spacing.

            JButton addButton = new JButton("Add");//Adding button for adding the book to library.
            addBookPanel.add(addButton);
            addButton.setFocusPainted(false);//Turns off focusing on button.

            //Adds the book to library when the button is clicked.
            addButton.addActionListener(e1 -> {
                try {
                    int bookId = LibraryManagementSystem.generateBookId();//Generates new ID for the book.
                    int publishedYear = Integer.parseInt((yearField.getText()));//Change the published year to integer value.
                    if (nameField.getText().isEmpty() || authorField.getText().isEmpty() || genreField.getText().isEmpty())
                        throw new CustomException();//If the fields are empty throws the exception.
                    try {
                        for (char c : authorField.getText().toCharArray()) {//Checking the author's name if it contains number or not.
                            if (Character.isDigit(c))
                                throw new CustomException();//If it contains a number throws an exception.
                        }
                        Books book = new Books(bookId, nameField.getText(), authorField.getText(), genreField.getText(), true, publishedYear);//Makes a book object.
                        LibraryManagementSystem.addBook(book);//Adds the books to the ArrayList.
                        LibraryManagementSystem.writeBooksToFile(LibraryManagementSystem.allbooks, booksFilePath);//Writes the added book to the file.
                        JOptionPane.showMessageDialog(null, "The book has been added.", "Done", JOptionPane.INFORMATION_MESSAGE);
                        addBookFrame.dispose();//Frame closes after adding the book.
                    } catch (CustomException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage() + ": Author cannot be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                        authorField.setText("");//Clears the field after error.
                    }
                } catch (NumberFormatException exception) {//When String value is entered in year field catches the error.
                    JOptionPane.showMessageDialog(null, "Year can not be characters!", "Error", JOptionPane.ERROR_MESSAGE);
                    yearField.setText("");//Clears the text field after the error.
                } catch (CustomException customException) {//When the fields are empty catches the error.
                    JOptionPane.showMessageDialog(null, customException.getMessage() + ": Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            addBookFrame.getRootPane().setDefaultButton(addButton);
            addBookFrame.setLocationRelativeTo(null);
            addBookFrame.setVisible(true);
        });
        //Adding button that delete book.
        ImageIcon deleteBookIcon = new ImageIcon("Images/deleteBookIcon.jpeg");//Icon for the button.
        JButton deleteBook = new JButton("Delete Book");
        deleteBook.setFont(buttonFont);
        deleteBook.setFocusPainted(false);
        deleteBook.setPreferredSize(new Dimension(200,80));
        deleteBook.setIcon(deleteBookIcon);//Set icon to button.
        deleteBook.setIconTextGap(10);//Set gap between text and icon.
        adminPanel.add(deleteBook);
        deleteBook.addActionListener(e -> {
            //Making new frame for delete book
            JFrame deleteBookFrame = new JFrame("Delete book");
            deleteBookFrame.setResizable(false);
            deleteBookFrame.setSize(400,300);
            deleteBookFrame.setLocationRelativeTo(null);
            deleteBookFrame.setVisible(true);
            deleteBookFrame.setIconImage(icon.getImage());//Set icon to the frame.

            JPanel deleteBookPanel = new JPanel(new GridLayout(2,2));
            deleteBookFrame.add(deleteBookPanel);

            Font labelFont = new Font("Rockwell",Font.PLAIN,22);
            Font textFieldFont = new Font("Arial",Font.BOLD,22);
            Color labelColor = Color.DARK_GRAY;
            //Adding Labels and text fields to the panel
            JLabel idLabel = new JLabel("Enter book's ID:");
            idLabel.setFont(labelFont);
            idLabel.setForeground(labelColor);
            deleteBookPanel.add(idLabel);

            JTextField idField = new JTextField();
            idField.setFont(textFieldFont);
            deleteBookPanel.add(idField);

            deleteBookPanel.add(new JLabel());//Adding label for spacing
            //Adding button for submitting the task
            JButton deleteButton = new JButton("Delete");
            deleteBookPanel.add(deleteButton);
            deleteButton.setFocusPainted(false);
            deleteButton.setFont(textFieldFont);

            //Adding an Action Listener
            deleteButton.addActionListener(e1 -> {
                try {
                    int bookId = Integer.parseInt(idField.getText());
                    ArrayList<Books> books = LibraryManagementSystem.readBooksFromFile(booksFilePath);
                    Iterator<Books> iterator = books.iterator();
                    while (iterator.hasNext()) {
                        Books book = iterator.next();
                        if (book.getBookId() == bookId) {
                            iterator.remove();
                            JOptionPane.showMessageDialog(null, "Book deleted successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                            deleteBookFrame.dispose();
                            break;
                        }
                        if (!iterator.hasNext()) {
                            JOptionPane.showMessageDialog(null, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    LibraryManagementSystem.dwriteBooksToFile(books, booksFilePath);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Can not be characters!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            deleteBookFrame.getRootPane().setDefaultButton(deleteButton);
        });

        //Adding delete user button.
        ImageIcon deleteUserIcon = new ImageIcon("Images/icon1.png");//Icon for the button.
        JButton deleteUser = new JButton("Delete User");
        deleteUser.setFont(buttonFont);
        deleteUser.setFocusPainted(false);
        deleteUser.setPreferredSize(new Dimension(200,80));
        deleteUser.setIcon(deleteUserIcon);//Set icon to button.
        deleteUser.setIconTextGap(10);//Set gap between text and icon.
        adminPanel.add(deleteUser);
        deleteUser.addActionListener(e -> {
            //Making new frame for delete user.
            JFrame deleteUserFrame = new JFrame("Delete user");
            deleteUserFrame.setResizable(false);
            deleteUserFrame.setSize(400, 300);
            deleteUserFrame.setLocationRelativeTo(null);
            deleteUserFrame.setVisible(true);
            deleteUserFrame.setIconImage(icon.getImage());//Set icon to the frame.

            JPanel deleteUserPanel = new JPanel(new GridLayout(2, 2));
            deleteUserFrame.add(deleteUserPanel);

            Font labelFont = new Font("Rockwell", Font.PLAIN, 22);
            Font textFieldFont = new Font("Arial", Font.BOLD, 22);
            Color labelColor = Color.DARK_GRAY;
            //Adding Labels and text fields to the panel
            JLabel idLabel = new JLabel("Enter user's ID:");
            idLabel.setFont(labelFont);
            idLabel.setForeground(labelColor);
            deleteUserPanel.add(idLabel);

            JTextField idField = new JTextField();
            idField.setFont(textFieldFont);
            deleteUserPanel.add(idField);

            deleteUserPanel.add(new JLabel());//Adding label for spacing
            //Adding button for submitting the task
            JButton deleteButton = new JButton("Delete");
            deleteUserPanel.add(deleteButton);
            deleteButton.setFocusPainted(false);
            deleteButton.setFont(textFieldFont);

            deleteButton.addActionListener(e1 -> {
                try {
                    int userId = Integer.parseInt(idField.getText());
                    ArrayList<NormalUser> users = LibraryManagementSystem.readUsersFromFile(usersFilePAth);
                    Iterator<NormalUser> iterator = users.iterator();
                    while (iterator.hasNext()) {
                        NormalUser user = iterator.next();
                        if (user.getUserID() == userId) {
                            iterator.remove();
                            JOptionPane.showMessageDialog(null, "User deleted successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                            deleteUserFrame.dispose();
                            break;
                        }
                        if (!iterator.hasNext()) {
                            JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    LibraryManagementSystem.dwriteUsersToFile(users,usersFilePAth);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Can not be characters!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            deleteUserFrame.getRootPane().setDefaultButton(deleteButton);
        });

        //Adding all users button.
        ImageIcon allUsersIcon = new ImageIcon("Images/allUsersIcon.jpeg");//Icon for the button.
        JButton usersButton = new JButton("All Users");
        usersButton.setFont(buttonFont);
        usersButton.setFocusPainted(false);
        usersButton.setPreferredSize(new Dimension(200, 80));
        usersButton.setIcon(allUsersIcon);//Set icon to button.
        usersButton.setIconTextGap(10);//Set gap between text and icon.
        adminPanel.add(usersButton);
        //Shows the users when All users' button is clicked. Also includes search books area.
        usersButton.addActionListener(e -> {
            JFrame usersFrame = new JFrame("Users");
            usersFrame.setResizable(false);
            usersFrame.setSize(700, 600);
            usersFrame.setIconImage(icon.getImage());//Set icon to the frame.

            JPanel usersPanel = new JPanel(new BorderLayout());
            usersPanel.setBackground(Color.GREEN);
            usersFrame.add(usersPanel);

            // Add a search bar with JTextField and JButton
            JPanel searchPanel = new JPanel();
            JTextField searchField = new JTextField(20);

            JButton searchButton = new JButton("Search");
            searchButton.setFocusPainted(false);//Turns off focus on button.

            searchPanel.add(new JLabel("Enter User ID: "));
            usersPanel.add(searchPanel, BorderLayout.NORTH);
            searchPanel.add(searchField);
            searchPanel.add(searchButton);

            JTextArea usersTextArea = new JTextArea();
            usersTextArea.setEditable(false);
            ArrayList<NormalUser> usersList = LibraryManagementSystem.readUsersFromFile(usersFilePAth);
            StringBuilder usersText = new StringBuilder();
            for (NormalUser user : usersList) {
                usersText.append(user.toString()).append("\n");
            }
            usersTextArea.setText(usersText.toString());
            usersPanel.add(new JScrollPane(usersTextArea), BorderLayout.CENTER);//for scrolling books

            //When search button is clicked, writes it to the screen.
            searchButton.addActionListener(e1 -> {
                try {
                    String searchText = searchField.getText();
                    if (!searchText.isEmpty()) {
                        // Finds the searched book from the Books file and write it to the screen.
                        NormalUser foundUser = LibraryManagementSystem.findUserById(Integer.parseInt(searchText));
                        if (foundUser != null) {
                            usersTextArea.setText(foundUser.toString());
                        } else {
                            usersTextArea.setText("User does not exist!");
                        }
                    } else {
                        // If search field is empty, show all users
                        usersTextArea.setText(usersText.toString());
                    }
                }catch (NumberFormatException e2){
                    JOptionPane.showMessageDialog(null, "Can not be characters.", "Error", JOptionPane.ERROR_MESSAGE);
                    searchField.setText("");//Clearing the search field after error.
                }
            });
            usersFrame.getRootPane().setDefaultButton(searchButton);
            usersFrame.setLocationRelativeTo(null);
            usersFrame.setVisible(true);
        });
    }
    //Normal user page.
    private void openMainApplicationPage() {//Main page after logging in.
        JFrame mainFrame = new JFrame("Main page");
        mainFrame.setSize(700, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        ImageIcon icon = new ImageIcon("Images/icon.jpg");
        mainFrame.setIconImage(icon.getImage());//Set an icon to the frame.
        //Adding background image to the panel.
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Images/mainFrame.jpeg");
                // Scale the image to fit the panel.
                Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.paintIcon(this, g, 0, 0);
            }
        };
        mainFrame.add(panel);
        Font buttonFont = new Font("Rockwell", Font.BOLD, 20);//Set font for the buttons.
        //Making me button that shows the information of the user.
        ImageIcon meIcon = new ImageIcon("Images/icon1.png");//Icon for the button.
        JButton userButton = new JButton("Me");
        userButton.setFocusPainted(false);//Remove the focus button.
        userButton.setPreferredSize(new Dimension(200, 80));//Set size of the button manually.
        userButton.setFont(buttonFont);
        userButton.setIcon(meIcon);//Set an icon to the button.
        userButton.setIconTextGap(10);//Change the gap between text and icon.
        panel.add(userButton);
        //Actions me button do when clicked.
        userButton.addActionListener(e -> {
            for (NormalUser user : LibraryManagementSystem.allusers) {
                if (timelyUserId == user.getUserID()) {
                    JFrame userFrame = new JFrame("User Info");
                    userFrame.setResizable(false);
                    userFrame.setSize(700, 600);
                    userFrame.setIconImage(icon.getImage());//Set an icon to the frame.
                    JPanel userDetailPanel = new JPanel(new GridLayout(7,5,10,10)) {//Change user frame's background.
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            ImageIcon backgroundImage = new ImageIcon("Images/user1.jpeg");
                            // Scale the image to fit the panel.
                            Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon scaledIcon = new ImageIcon(scaledImage);
                            scaledIcon.paintIcon(this, g, 0, 0);
                        }
                    };
                    userDetailPanel.setPreferredSize(new Dimension(400,300));
                    userFrame.add(userDetailPanel);//Adding panel to the frame.
                    Font labelFont = new Font("Rockwell", Font.BOLD, 25);//Set font for the labels.
                    Font updateButtonFont = new Font("Rockwell",Font.BOLD,20);
                    Color labelColor = Color.black;//Set font color.
                    //Adding labels for showing user's information.
                    JLabel userIdLabel = new JLabel("User ID:");
                    userIdLabel.setFont(labelFont);
                    userIdLabel.setForeground(labelColor);
                    userDetailPanel.add(userIdLabel);

                    JLabel idLabel = new JLabel(String.valueOf(user.getUserID()));
                    idLabel.setFont(labelFont);
                    idLabel.setForeground(labelColor);
                    userDetailPanel.add(idLabel);

                    JLabel userIdLabelSpace = new JLabel("");
                    userIdLabelSpace.setFont(labelFont);
                    userIdLabelSpace.setForeground(labelColor);
                    userDetailPanel.add(userIdLabelSpace);

                    JLabel nameLabel = new JLabel("Name:");
                    nameLabel.setFont(labelFont);
                    nameLabel.setForeground(labelColor);
                    userDetailPanel.add(nameLabel);

                    JLabel nameLabel1 = new JLabel(user.getName());
                    nameLabel1.setFont(labelFont);
                    nameLabel1.setForeground(labelColor);
                    userDetailPanel.add(nameLabel1);

                    // Add update button for name
                    JButton updateNameButton = new JButton("Update Name");
                    updateNameButton.setPreferredSize(new Dimension(100,40));
                    updateNameButton.setFocusPainted(false);
                    updateNameButton.setFont(updateButtonFont);
                    userDetailPanel.add(updateNameButton);

                    //Adding update name action listener
                    updateNameButton.addActionListener(e1 -> {
                        JFrame updateNameFrame = new JFrame("Update Name");
                        updateNameFrame.setResizable(false);
                        updateNameFrame.setSize(350, 200);
                        updateNameFrame.setLocationRelativeTo(null);
                        updateNameFrame.setVisible(true);
                        updateNameFrame.setIconImage(icon.getImage());//Set an icon to the frame.


                        JPanel updateNamePanel = new JPanel(new GridLayout(2, 2));
                        updateNameFrame.add(updateNamePanel);

                        Font labelFont1 = new Font("Rockwell", Font.PLAIN, 22);//Set font for the labels.
                        Font textFieldFont = new Font("Arial", Font.PLAIN, 22);//Set font for textFields.
                        Color labelColor1 = Color.DARK_GRAY;//Set font color.
                        //Adding labels and text fields to the panel.
                        JLabel nameLabel2 = new JLabel("Enter Name:");
                        nameLabel2.setFont(labelFont1);
                        nameLabel2.setForeground(labelColor1);
                        updateNamePanel.add(nameLabel2);

                        JTextField nameField = new JTextField();
                        nameField.setFont(textFieldFont);
                        updateNamePanel.add(nameField);

                        updateNamePanel.add(new JLabel());//Adding label for spacing.
                        //Adding button for submitting the task.
                        JButton saveButton = new JButton("Save");
                        updateNamePanel.add(saveButton);
                        saveButton.setFocusPainted(false);
                        saveButton.setFont(textFieldFont);

                        //Adding changed name to a new name.
                        saveButton.addActionListener(e2 -> {
                            try {
                                if (nameField.getText().isEmpty()) throw new CustomException();
                                for (char c : nameField.getText().toCharArray()) {//Controls if it contains integer value or not.
                                    if (Character.isDigit(c)) {
                                        throw new CustomException();
                                    }
                                }
                                nameLabel1.setText(nameField.getText());
                                LibraryManagementSystem.updateUserNameInFile(thisUser.getUserID(), nameField.getText());
                                JOptionPane.showMessageDialog(null, "Name updated successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                                updateNameFrame.dispose();
                            } catch (CustomException exception){
                                JOptionPane.showMessageDialog(null, exception.getMessage() + ": Name cannot be empty and can only be charachters!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        updateNameFrame.getRootPane().setDefaultButton(saveButton);
                    });

                    JLabel emailLabel = new JLabel("Email:");
                    emailLabel.setFont(labelFont);
                    emailLabel.setForeground(labelColor);
                    userDetailPanel.add(emailLabel);

                    JLabel emailLabel1 = new JLabel(user.getEmail());
                    emailLabel1.setFont(labelFont);
                    emailLabel1.setForeground(labelColor);
                    userDetailPanel.add(emailLabel1);

                    JButton updateEmailButton = new JButton("Update Email");
                    updateEmailButton.setPreferredSize(new Dimension(60,40));
                    updateEmailButton.setFocusPainted(false);
                    updateEmailButton.setFont(updateButtonFont);
                    userDetailPanel.add(updateEmailButton);

                    //add  action listener for updating email
                    updateEmailButton.addActionListener(e1 -> {
                        JFrame updateEmailFrame = new JFrame("Update Email");
                        updateEmailFrame.setResizable(false);
                        updateEmailFrame.setSize(350, 200);
                        updateEmailFrame.setLocationRelativeTo(null);
                        updateEmailFrame.setVisible(true);
                        updateEmailFrame.setIconImage(icon.getImage());//Set an icon to the frame.


                        JPanel updateEmailPanel = new JPanel(new GridLayout(2, 2));
                        updateEmailFrame.add(updateEmailPanel);

                        Font labelFont1 = new Font("Rockwell", Font.PLAIN, 22);//Set font for the labels.
                        Font textFieldFont = new Font("Arial", Font.PLAIN, 22);//Set font for textFields.
                        Color labelColor1 = Color.DARK_GRAY;//Set font color.
                        //Adding labels and text fields to the panel.
                        JLabel emailLabel2 = new JLabel("Enter Email:");
                        emailLabel2.setFont(labelFont1);
                        emailLabel2.setForeground(labelColor1);
                        updateEmailPanel.add(emailLabel2);

                        JTextField emailField = new JTextField();
                        emailField.setFont(textFieldFont);
                        updateEmailPanel.add(emailField);

                        updateEmailPanel.add(new JLabel());//Adding label for spacing.
                        //Adding button for submitting the task.
                        JButton saveButton = new JButton("Save");
                        updateEmailPanel.add(saveButton);
                        saveButton.setFocusPainted(false);
                        saveButton.setFont(textFieldFont);

                        //Adding changed name to a new name.
                        saveButton.addActionListener(e2 -> {
                            try {
                                if (emailField.getText().isEmpty() || !emailField.getText().contains("@") || !emailField.getText().endsWith(".com")) throw new CustomException();
                                LibraryManagementSystem.updateUserEmailInFile(thisUser.getUserID(), emailField.getText());
                                emailLabel1.setText(emailField.getText());
                                JOptionPane.showMessageDialog(null, "Email updated successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                                updateEmailFrame.dispose();
                            } catch (CustomException exception) {
                                JOptionPane.showMessageDialog(null, exception.getMessage() + ": Email cannot be empty and should contain '@' with '.com'", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        updateEmailFrame.getRootPane().setDefaultButton(saveButton);
                    });

                    JLabel ageLabel = new JLabel("Age:");
                    ageLabel.setFont(labelFont);
                    ageLabel.setForeground(labelColor);
                    userDetailPanel.add(ageLabel);

                    JLabel ageLabel1 = new JLabel(String.valueOf(user.getAge()));
                    ageLabel1.setFont(labelFont);
                    ageLabel1.setForeground(labelColor);
                    userDetailPanel.add(ageLabel1);

                    JButton updateAgeButton = new JButton("Update Age");
                    updateAgeButton.setPreferredSize(new Dimension(60,40));
                    updateAgeButton.setFocusPainted(false);
                    updateAgeButton.setFont(updateButtonFont);
                    userDetailPanel.add(updateAgeButton);

                    //Adding action listener for updating an age
                    updateAgeButton.addActionListener(e1 -> {
                        JFrame updateAgeFrame = new JFrame("Update Age");
                        updateAgeFrame.setResizable(false);
                        updateAgeFrame.setSize(350, 200);
                        updateAgeFrame.setLocationRelativeTo(null);
                        updateAgeFrame.setVisible(true);
                        updateAgeFrame.setIconImage(icon.getImage());//Set an icon to the frame.


                        JPanel updateAgePanel = new JPanel(new GridLayout(2, 2));
                        updateAgeFrame.add(updateAgePanel);

                        Font labelFont1 = new Font("Rockwell", Font.PLAIN, 22);//Set font for the labels.
                        Font textFieldFont = new Font("Arial", Font.PLAIN, 22);//Set font for textFields.
                        Color labelColor1 = Color.DARK_GRAY;//Set font color.
                        //Adding labels and text fields to the panel.
                        JLabel AgeLabel2 = new JLabel("Enter Age:");
                        AgeLabel2.setFont(labelFont1);
                        AgeLabel2.setForeground(labelColor1);
                        updateAgePanel.add(AgeLabel2);

                        JTextField ageField = new JTextField();
                        ageField.setFont(textFieldFont);
                        updateAgePanel.add(ageField);

                        updateAgePanel.add(new JLabel());//Adding label for spacing.
                        //Adding button for submitting the task.
                        JButton saveButton = new JButton("Save");
                        updateAgePanel.add(saveButton);
                        saveButton.setFocusPainted(false);
                        saveButton.setFont(textFieldFont);

                        //Adding change of name to a new name
                        saveButton.addActionListener(e2 -> {
                            try {
                                if (ageField.getText().isEmpty()) throw new CustomException();
                                LibraryManagementSystem.updateUserAgeInFile(thisUser.getUserID(), Integer.parseInt(ageField.getText()));
                                ageLabel1.setText(ageField.getText());
                                JOptionPane.showMessageDialog(null, "Age updated successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                                updateAgeFrame.dispose();
                            } catch (NumberFormatException exception) {
                                JOptionPane.showMessageDialog(null, "Input only numeric value!", "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (CustomException exception){
                                JOptionPane.showMessageDialog(null,exception.getMessage() + ": Age cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        updateAgeFrame.getRootPane().setDefaultButton(saveButton);
                    });

                    JLabel passwordLabel = new JLabel("Password:");
                    passwordLabel.setFont(labelFont);
                    passwordLabel.setForeground(labelColor);
                    userDetailPanel.add(passwordLabel);

                    JLabel passwordLabel1 = new JLabel(user.getPassword());
                    passwordLabel1.setFont(labelFont);
                    passwordLabel1.setForeground(labelColor);
                    userDetailPanel.add(passwordLabel1);

                    JButton updatePasswordButton = new JButton("Update Password");
                    updatePasswordButton.setPreferredSize(new Dimension(60,40));
                    updatePasswordButton.setFocusPainted(false);
                    updatePasswordButton.setFont(updateButtonFont);
                    userDetailPanel.add(updatePasswordButton);

                    //Add action listener for updating password
                    updatePasswordButton.addActionListener(e1 -> {
                        JFrame updatePasswordFrame = new JFrame("Update Password");
                        updatePasswordFrame.setResizable(false);
                        updatePasswordFrame.setSize(350, 200);
                        updatePasswordFrame.setLocationRelativeTo(null);
                        updatePasswordFrame.setVisible(true);
                        updatePasswordFrame.setIconImage(icon.getImage());//Set an icon to the frame.


                        JPanel updatePasswordPanel = new JPanel(new GridLayout(2, 2));
                        updatePasswordFrame.add(updatePasswordPanel);

                        Font labelFont1 = new Font("Rockwell", Font.PLAIN, 22);//Set font for the labels.
                        Font textFieldFont = new Font("Arial", Font.PLAIN, 22);//Set font for textFields.
                        Color labelColor1 = Color.DARK_GRAY;//Set font color.
                        //Adding labels and text fields to the panel.
                        JLabel passwordLabel2 = new JLabel("Enter Email:");
                        passwordLabel2.setFont(labelFont1);
                        passwordLabel2.setForeground(labelColor1);
                        updatePasswordPanel.add(passwordLabel2);

                        JTextField passwordField = new JTextField();
                        passwordField.setFont(textFieldFont);
                        updatePasswordPanel.add(passwordField);

                        updatePasswordPanel.add(new JLabel());//Adding label for spacing.
                        //Adding button for submitting the task.
                        JButton saveButton = new JButton("Save");
                        updatePasswordPanel.add(saveButton);
                        saveButton.setFocusPainted(false);
                        saveButton.setFont(textFieldFont);

                        //Adding change of name to a new name
                        saveButton.addActionListener(e2 -> {
                            try {
                                if (passwordField.getText().isEmpty()) throw new CustomException();
                                LibraryManagementSystem.updateUserPasswordInFile(thisUser.getUserID(), passwordField.getText());
                                passwordLabel1.setText(passwordField.getText());
                                JOptionPane.showMessageDialog(null, "Password updated successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                                updatePasswordFrame.dispose();
                            } catch (CustomException exception) {
                                JOptionPane.showMessageDialog(null,exception.getMessage() + ": Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        });
                        updatePasswordFrame.getRootPane().setDefaultButton(saveButton);
                    });

                    userFrame.setVisible(true);
                    userFrame.setLocationRelativeTo(null);
                }
            }
        });

        allBooksButton(panel);//Invoke all books button method.

        //Making button for seeing user's books.
        ImageIcon myBooksButton = new ImageIcon("Images/myBooksIcon.jpeg");//Icon for the button.
        JButton mybooksButton = new JButton("My Books");
        mybooksButton.setFont(buttonFont);
        mybooksButton.setFocusPainted(false);
        mybooksButton.setPreferredSize(new Dimension(200, 80));
        mybooksButton.setIcon(myBooksButton);//Set icon to the button.
        mybooksButton.setIconTextGap(10);//Set gap between text and icon.
        panel.add(mybooksButton);
        //Shows the user's books when My books' button is clicked.
        mybooksButton.addActionListener(e -> {
            JFrame myBookFrame = new JFrame("My Books");
            myBookFrame.setResizable(false);
            myBookFrame.setSize(700, 600);
            myBookFrame.setIconImage(icon.getImage());//Set an icon to the frame.

            JPanel mybook = new JPanel(new BorderLayout());
            mybook.setBackground(Color.WHITE);
            myBookFrame.add(mybook);

            JTextArea booksTextArea1 = new JTextArea();
            booksTextArea1.setEditable(false);//Makes it uneditable.
            ArrayList<Transaction> mytransactionsList = LibraryManagementSystem.readTransactionsFromFile(transactionsFilePath);
            StringBuilder booksText1 = new StringBuilder();
            for (Transaction transaction : mytransactionsList) {//Writes borrowed books' info and last delivery date.
                if (transaction.getBorrower().getUserID() == thisUser.getUserID()) {
                    booksText1.append(transaction.getBook().toString() + ", Last Delivery Date = " + transaction.getBorrowdate().plusDays(14)).append("\n");
                }else {
                    booksTextArea1.setText("You haven't borrowed any book yet");
                }
            }
            booksTextArea1.setText(booksText1.toString());
            mybook.add(new JScrollPane(booksTextArea1), BorderLayout.CENTER);//for scrolling books

            myBookFrame.setLocationRelativeTo(null);
            myBookFrame.setVisible(true);
        });

        //Button for borrowing books.
        ImageIcon borrowBookIcon = new ImageIcon("Images/borrowIcon.jpeg");//Icon for the button.
        JButton borrowBook = new JButton("Borrow Book");
        borrowBook.setFont(buttonFont);
        borrowBook.setFocusPainted(false);//Turns off focus of the button.
        borrowBook.setPreferredSize(new Dimension(200, 80));
        borrowBook.setIcon(borrowBookIcon);//Set icon to the button.
        borrowBook.setIconTextGap(10);//Set gap between text and icon.
        panel.add(borrowBook);
        //Actions that the button do when clicking borrow a book button.
        borrowBook.addActionListener(e -> {
            JFrame borrowFrame = new JFrame("Borrow Book");
            borrowFrame.setResizable(false);
            borrowFrame.setSize(400, 300);
            borrowFrame.setLocationRelativeTo(null);
            borrowFrame.setVisible(true);
            borrowFrame.setIconImage(icon.getImage());//Set an icon to the frame.

            JPanel borrowPanel = new JPanel(new GridLayout(2, 2));
            borrowFrame.add(borrowPanel);

            Font labelFont = new Font("Rockwell", Font.PLAIN, 22);//Set font for the labels.
            Font textFieldFont = new Font("Arial", Font.PLAIN, 22);//Set font for textFields.

            JLabel bookIdLabel = new JLabel("Enter Book ID");
            bookIdLabel.setFont(labelFont);
            borrowPanel.add(bookIdLabel);

            JTextField bookIdTextField = new JTextField(10);
            bookIdTextField.setFont(textFieldFont);
            borrowPanel.add(bookIdTextField);

            JLabel emptyLabel = new JLabel();//Label for spacing
            borrowPanel.add(emptyLabel);

            JButton borrowButton = new JButton("Borrow book");
            borrowButton.setFont(textFieldFont);
            borrowButton.setFocusPainted(false);
            borrowPanel.add(borrowButton);
            //When borrowButton is clicked
            borrowButton.addActionListener(e1 -> {
                try {
                    int bookId = Integer.parseInt(bookIdTextField.getText());
                    Books bookToBorrow = LibraryManagementSystem.findBookById(bookId);
                    boolean isAvailable;
                    if (bookToBorrow != null) {//Controls availability of the book.
                        isAvailable = bookToBorrow.isAvailable();//Checks if the book is available or not.
                    } else {
                        isAvailable = false;
                    }
                    if (bookToBorrow != null && isAvailable) {
                        LocalDate thisDate = LocalDate.now();//The date of borrowed book.
                        LibraryManagementSystem.borrowBook(thisUser, bookToBorrow, thisDate);//Records the borrowed book's info.
                        LibraryManagementSystem.writeTransactionsToFile(LibraryManagementSystem.alltransactions, transactionsFilePath);
                        JOptionPane.showMessageDialog(null, "Book borrowed successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                        LibraryManagementSystem.updateBookAvailabilityInFile(bookId, false);//After borrowing the book makes it unavailable.
                        borrowFrame.dispose();//Frame closes when the book is borrowed.
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Can not be characters!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            borrowFrame.getRootPane().setDefaultButton(borrowButton);//Set the default(Enter) button to borrowButton.
            borrowFrame.setLocationRelativeTo(null);//Set location to center.
            borrowFrame.setVisible(true);
        });

        //Adding button for returning book.
        ImageIcon returnBookIcon = new ImageIcon("Images/returnIcon.jpeg");//Icon for the button.
        JButton returnBook = new JButton("Return Book");
        returnBook.setFont(buttonFont);
        returnBook.setFocusPainted(false);
        returnBook.setPreferredSize(new Dimension(200, 80));
        returnBook.setIcon(returnBookIcon);//Set icon to the button.
        returnBook.setIconTextGap(10);//Set gap between text and icon.
        panel.add(returnBook);
        //When return book button is clicked, actions that happen.
        returnBook.addActionListener(e -> {
            JFrame returnFrame = new JFrame("Return Book");
            returnFrame.setResizable(false);
            returnFrame.setSize(400, 300);
            returnFrame.setLocationRelativeTo(null);
            returnFrame.setVisible(true);
            returnFrame.setIconImage(icon.getImage());//Set an icon to the frame.

            JPanel returnPanel = new JPanel(new GridLayout(2, 2));
            returnFrame.add(returnPanel);

            Font labelFont = new Font("Rockwell", Font.PLAIN, 22);//Set font for the labels.
            Font textFieldFont = new Font("Arial", Font.PLAIN, 22);//Set font for textFields.
            Color labelColor = Color.DARK_GRAY;//Set font color.
            //Adding labels and text fields to the panel.
            JLabel idLabel = new JLabel("Enter Book's ID:");
            idLabel.setFont(labelFont);
            idLabel.setForeground(labelColor);
            returnPanel.add(idLabel);

            JTextField idField = new JTextField();
            idField.setFont(textFieldFont);
            returnPanel.add(idField);

            returnPanel.add(new JLabel());//Adding label for spacing.
            //Adding button for submitting the task.
            JButton returnButton = new JButton("Submit");
            returnPanel.add(returnButton);
            returnButton.setFocusPainted(false);
            returnButton.setFont(textFieldFont);

            //Updates the Transaction file after clicking return button.
            returnButton.addActionListener(e1 -> {
                try {
                    int bookId = Integer.parseInt(idField.getText());
                    Books bookToReturn = LibraryManagementSystem.findBorrowedBookById(bookId);//Checks if the book exists or not.
                    boolean isAvailable;
                    int checkUserId = 0;
                    if (bookToReturn != null) {//Controls availability of the book.
                        isAvailable = bookToReturn.isAvailable();//Checks if the book is available or not.
                        ArrayList<Transaction> transactions = LibraryManagementSystem.readTransactionsFromFile(transactionsFilePath);
                        for (Transaction transaction : transactions){//Controls the user for returning the book.
                            if (transaction.getBook().getBookId() == bookToReturn.getBookId()){
                                checkUserId = transaction.getBorrower().getUserID();
                            }
                        }
                    } else {
                        isAvailable = true;
                    }
                    if (bookToReturn != null && !isAvailable && thisUser.getUserID() == checkUserId) {
                        LocalDate thisDate = LocalDate.now();//The date of returned book.
                        LibraryManagementSystem.returnBook(thisUser, bookToReturn, thisDate);//Records the returned book's info.
                        LibraryManagementSystem.updateBookAvailabilityInFile(true, bookId);//After returning the book makes it available.
                        JOptionPane.showMessageDialog(null, "Book returned successfully");
                        Transaction.removeTransaction(thisUser.getUserID(), bookId);//removes the returned book from the file.
                        returnFrame.dispose();//Frame closes when the book is returned.
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(null, "Can not be characters!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            returnFrame.getRootPane().setDefaultButton(returnButton);//Set the default(Enter) button to loginSubmitButton
        });
    }
    public static void main(String[] args){
        Admin admin1 = new Admin(1, "Kazybek", "kazy@gmail.com", 21, "gazi", "admin");//Creating admin.
        LibraryManagementSystem.admins.add(admin1);

        Books book1 = new Books(11, "The tale of two cities", "Charles Dickens", "historical fiction", true, 1859);
        LibraryManagementSystem.addBook(book1);//Add book to book Array List.
        Books book2 = new Books(12, "The little prince", "Antoine de Saint Exupery", "Fantasy", true, 1943);
        LibraryManagementSystem.addBook(book2);
        Books book3 = new Books(13, " Hobbit", "J.R TolkienThe", "Fantasy", true, 1937);
        LibraryManagementSystem.addBook(book3);
        Books book4 = new Books(14, "The Alchemist", "Paulo Coelho", "Fantasy", true, 1988);
        LibraryManagementSystem.addBook(book4);

        LibraryManagementSystem.writeBooksToFile(LibraryManagementSystem.allbooks, booksFilePath);
        LibraryManagementSystem.allbooks = LibraryManagementSystem.readBooksFromFile(booksFilePath);

        LibraryManagementSystem.writeUsersToFile(LibraryManagementSystem.allusers, usersFilePAth);
        LibraryManagementSystem.allusers = LibraryManagementSystem.readUsersFromFile(usersFilePAth);

        EventQueue.invokeLater(() -> {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                             InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
            new LibraryManagementApp();
        });
    }
}
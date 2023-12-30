import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class LibraryManagementApp extends JFrame {
    static final String booksFilePath = "books.txt";
    static final String usersFilePAth = "users.txt";
    static final String transactionsFilePath = "Transactions.txt";
    static final String adminFilePath = "Admins.txt";
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

        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());//Set an icon to the frame.

        loginButton = new JButton("Login");
        loginButton.setFocusPainted(false);//Remove the button focus.

        signUpButton = new JButton("Sign Up");
        signUpButton.setFocusPainted(false);

        Font defaultFont = new Font("Rockwell", Font.PLAIN, 50);

        loginButton.setFont(defaultFont);
        signUpButton.setFont(defaultFont);

        //Adding actionListener to buttons.
        loginButton.addActionListener(e -> openLoginPage());
        signUpButton.addActionListener(e -> openSignUpPage());
        //Adding background image to the panel.
        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("millet.jpg");
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
                }else if (LibraryManagementSystem.authenticateUserbyPassword(userId, password)){
                    timelyUserId = userId;
                    thisUser = LibraryManagementSystem.findUserById(userId);
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    loginFrame.dispose();
                    openMainApplicationPage();
                }else {
                    JOptionPane.showMessageDialog(null, "Invalid userId or password");
                }
                userIdField.setText("");//Clear the userId input.
                passwordField.setText("");//Clear the password input.
            } catch (NumberFormatException ex) {
                // Handle the case where the user entered a non-numeric value for userId
                JOptionPane.showMessageDialog(null, "Invalid userId. Please enter a numeric value.");
                userIdField.setText(""); // Clear the invalid input
                passwordField.setText("");
            }
        });
        loginFrame.getRootPane().setDefaultButton(loginSubmitButton);//Set the default(Enter) button to loginSubmitButton
        loginFrame.setVisible(true);
    }

    private void openSignUpPage() {
        JFrame signUpFrame = new JFrame("Sign Up Page");
        signUpFrame.setSize(600, 400);
        signUpFrame.setLocationRelativeTo(null);

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
                    JOptionPane.showMessageDialog(null, "Wrong mail.");
                    emailField.setText("");
                }
                int age = Integer.parseInt(ageField.getText());
                String password1 = new String(passwordField.getPassword());//Casting password to String to check if it is empty or not.
                if (emailField.getText().isEmpty() || nameField.getText().isEmpty() || password1.isEmpty())
                    throw new CustomException();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                int userId = LibraryManagementSystem.generateUserId();
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
                JOptionPane.showMessageDialog(null, exception.getMessage() + ": Fields cannot be empty!");
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "String value is not accepted in the age field!");
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
        JButton booksButton = new JButton("All Books");
        booksButton.setFont(buttonFont);
        booksButton.setFocusPainted(false);
        booksButton.setPreferredSize(new Dimension(150, 80));
        panel.add(booksButton);
        //Shows the available books when All books' button is clicked. Also includes search books area.
        booksButton.addActionListener(e -> {
            JFrame bookFrame = new JFrame("Books");
            bookFrame.setResizable(false);
            bookFrame.setSize(700, 600);

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
                    JOptionPane.showMessageDialog(null, "Can not be characters.");
                    searchField.setText("");//Clearing the search field after error.
                }
            });
            bookFrame.getRootPane().setDefaultButton(searchButton);
            bookFrame.setLocationRelativeTo(null);
            bookFrame.setVisible(true);
        });
    }

    private void adminPage(){
        JFrame adminFrame = new JFrame("Admin Page");
        adminFrame.setResizable(false);
        adminFrame.setSize(700, 600);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
        //Add background to admin panel.
        JPanel adminPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("admin.jpg");
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
        JButton addBook = new JButton("Add Book");
        addBook.setFont(buttonFont);
        addBook.setFocusPainted(false);
        addBook.setPreferredSize(new Dimension(150, 80));
        adminPanel.add(addBook);
        //When clicked, It opens the frame for adding the book.
        addBook.addActionListener(e -> {
            //Making new frame for adding new book.
            JFrame addBookFrame = new JFrame("Add Book");
            addBookFrame.setResizable(false);
            addBookFrame.setSize(600, 400);
            addBookFrame.setLocationRelativeTo(null);
            addBookFrame.setVisible(true);

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
                        JOptionPane.showMessageDialog(null, "The book has been added.");
                        addBookFrame.dispose();//Frame closes after adding the book.
                    } catch (CustomException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage() + ": Author cannot be a number!");
                        authorField.setText("");//Clears the field after error.
                    }
                } catch (
                        NumberFormatException exception) {//When String value is entered in year field catches the error.
                    JOptionPane.showMessageDialog(null, "Year can not be String");
                    yearField.setText("");//Clears the text field after the error.
                } catch (CustomException customException) {//When the fields are empty catches the error.
                    JOptionPane.showMessageDialog(null, customException.getMessage() + ": Fields cannot be empty!");
                }
            });
        });
    }

    private void openMainApplicationPage() {//Main page after logging in.
        JFrame mainFrame = new JFrame("Main page");
        mainFrame.setSize(700, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        //Adding background image to the panel.
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("R.jpeg");
                // Scale the image to fit the panel.
                Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.paintIcon(this, g, 0, 0);
            }
        };
        mainFrame.add(panel);
        Font buttonFont = new Font("Rockwell", Font.BOLD, 20);//Set font for the buttons.
        //Making me button that shows the information of the user.
        JButton userButton = new JButton("Me");
        userButton.setFocusPainted(false);//Remove the focus button.
        userButton.setPreferredSize(new Dimension(150, 80));//Set size of the button manually.
        userButton.setFont(buttonFont);
        panel.add(userButton);
        //Actions me button do when clicked.
        userButton.addActionListener(e -> {
            for (NormalUser user : LibraryManagementSystem.allusers) {
                if (timelyUserId == user.getUserID()) {
                    JFrame userFrame = new JFrame("User Info");
                    userFrame.setResizable(false);
                    userFrame.setSize(700, 600);
                    JPanel userPAnel = new JPanel(new GridLayout(5, 2)) {//Change user frame's background.
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            ImageIcon backgroundImage = new ImageIcon("user.png");
                            // Scale the image to fit the panel.
                            Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon scaledIcon = new ImageIcon(scaledImage);
                            scaledIcon.paintIcon(this, g, 0, 0);
                        }
                    };
                    userFrame.add(userPAnel);//Adding panel to the frame.
                    Font labelFont = new Font("Rockwell", Font.PLAIN, 25);//Set font for the labels.
                    Color labelColor = Color.white;//Set font color.
                    //Adding labels for showing user's information.
                    JLabel userIdLabel = new JLabel("User ID:");
                    userIdLabel.setFont(labelFont);
                    userIdLabel.setForeground(labelColor);
                    userPAnel.add(userIdLabel);

                    JLabel idLabel = new JLabel(String.valueOf(user.getUserID()));
                    idLabel.setFont(labelFont);
                    idLabel.setForeground(labelColor);
                    userPAnel.add(idLabel);

                    JLabel nameLabel = new JLabel("Name:");
                    nameLabel.setFont(labelFont);
                    nameLabel.setForeground(labelColor);
                    userPAnel.add(nameLabel);

                    JLabel nameLabel1 = new JLabel(user.getName());
                    nameLabel1.setFont(labelFont);
                    nameLabel1.setForeground(labelColor);
                    userPAnel.add(nameLabel1);

                    JLabel emailLabel = new JLabel("Email:");
                    emailLabel.setFont(labelFont);
                    emailLabel.setForeground(labelColor);
                    userPAnel.add(emailLabel);

                    JLabel emailLabel1 = new JLabel(user.getEmail());
                    emailLabel1.setFont(labelFont);
                    emailLabel1.setForeground(labelColor);
                    userPAnel.add(emailLabel1);

                    JLabel ageLabel = new JLabel("Age:");
                    ageLabel.setFont(labelFont);
                    ageLabel.setForeground(labelColor);
                    userPAnel.add(ageLabel);

                    JLabel ageLabel1 = new JLabel(String.valueOf(user.getAge()));
                    ageLabel1.setFont(labelFont);
                    ageLabel1.setForeground(labelColor);
                    userPAnel.add(ageLabel1);

                    JLabel passLabel = new JLabel("Password:");
                    passLabel.setFont(labelFont);
                    passLabel.setForeground(labelColor);
                    userPAnel.add(passLabel);

                    JLabel passLabel1 = new JLabel(user.getPassword());
                    passLabel1.setFont(labelFont);
                    passLabel1.setForeground(labelColor);
                    userPAnel.add(passLabel1);

                    userFrame.setVisible(true);
                    userFrame.setLocationRelativeTo(null);
                }
            }
        });

        allBooksButton(panel);//Invoke all books button method.

        //Making button for seeing user's books.
        JButton mybooksButton = new JButton("My Books");
        mybooksButton.setFont(buttonFont);
        mybooksButton.setFocusPainted(false);
        mybooksButton.setPreferredSize(new Dimension(150, 80));
        panel.add(mybooksButton);
        //Shows the user's books when My books' button is clicked.
        mybooksButton.addActionListener(e -> {
            JFrame mybookFrame = new JFrame("My Books");
            mybookFrame.setResizable(false);
            mybookFrame.setSize(700, 600);

            JPanel mybook = new JPanel(new BorderLayout());
            mybook.setBackground(Color.WHITE);
            mybookFrame.add(mybook);

            JTextArea booksTextArea1 = new JTextArea();
            booksTextArea1.setEditable(false);//Makes it uneditable.
            ArrayList<Transaction> mytransactionsList = LibraryManagementSystem.readTransactionsFromFile(transactionsFilePath);
            StringBuilder booksText1 = new StringBuilder();
            for (Transaction transaction : mytransactionsList) {//Writes borrowed books' info and last delivery date.
                if (transaction.getBorrower().getUserID() == thisUser.getUserID()) {
                    booksText1.append(transaction.getBook().toString() + ", Last Delivery Date = " + transaction.getBorrowdate().plusDays(14)).append("\n");
                }
            }
            booksTextArea1.setText(booksText1.toString());
            mybook.add(new JScrollPane(booksTextArea1), BorderLayout.CENTER);//for scrolling books

            mybookFrame.setLocationRelativeTo(null);
            mybookFrame.setVisible(true);
        });

        //Button for borrowing books.
        JButton borrowBook = new JButton("Borrow a Book");
        borrowBook.setFont(buttonFont);
        borrowBook.setPreferredSize(new Dimension(150, 80));
        panel.add(borrowBook);
        //Actions that the button do when clicking borrow a book button.
        borrowBook.addActionListener(e -> {
            JFrame borrowFrame = new JFrame();
            borrowFrame.setResizable(false);
            borrowFrame.setSize(700, 600);
            borrowFrame.setLocationRelativeTo(null);
            borrowFrame.setVisible(true);

            JPanel borrowPanel = new JPanel();
            borrowFrame.add(borrowPanel);

            JLabel bookIdLabel = new JLabel("Enter Book ID");
            JTextField bookIdTextField = new JTextField(10);
            JButton borrowButton = new JButton("Borrow book");

            borrowPanel.add(bookIdLabel);
            borrowPanel.add(bookIdTextField);
            borrowPanel.add(borrowButton);
            //When borrowButton is clicked
            borrowButton.addActionListener(e1 -> {
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
                    JOptionPane.showMessageDialog(null, "Book borrowed successfully");
                    LibraryManagementSystem.updateBookAvailabilityInFile(bookId, false);//After borrowing the book makes it unavailable.
                    borrowFrame.dispose();//Frame closes when the book is borrowed.
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found");
                }
            });
            borrowFrame.getRootPane().setDefaultButton(borrowButton);//Set the default(Enter) button to borrowButton.
            borrowFrame.setLocationRelativeTo(null);//Set location to center.
            borrowFrame.setVisible(true);
        });



        //Adding button for returning book.
        JButton returnBook = new JButton("Return Book");
        returnBook.setFont(buttonFont);
        returnBook.setFocusPainted(false);
        returnBook.setPreferredSize(new Dimension(150, 80));
        panel.add(returnBook);
        //When return book button is clicked, actions that happen.
        returnBook.addActionListener(e -> {
            JFrame returnFrame = new JFrame("Return Book");
            returnFrame.setResizable(false);
            returnFrame.setSize(400, 300);
            returnFrame.setLocationRelativeTo(null);
            returnFrame.setVisible(true);

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
                int bookId = Integer.parseInt(idField.getText());
                Books bookToReturn = LibraryManagementSystem.findBorrowedBookById(bookId);//Checks if the book exists or not.
                boolean isAvailable;
                if (bookToReturn != null) {//Controls availability of the book.
                    isAvailable = bookToReturn.isAvailable();//Checks if the book is available or not.
                } else {
                    isAvailable = true;
                }

                if (bookToReturn != null && !isAvailable) {
                    LocalDate thisDate = LocalDate.now();//The date of returned book.
                    LibraryManagementSystem.returnBook(thisUser, bookToReturn, thisDate);//Records the returned book's info.
                    LibraryManagementSystem.updateBookAvailabilityInFile(true, bookId);//After returning the book makes it available.
                    JOptionPane.showMessageDialog(null, "Book returned successfully");
                    Transaction.removeTransaction(thisUser.getUserID(), bookId);//removes the returned book from the file.
                    returnFrame.dispose();//Frame closes when the book is returned.
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found");
                }
            });
            returnFrame.getRootPane().setDefaultButton(returnButton);//Set the default(Enter) button to loginSubmitButton
        });
    }
    public static void main(String[] args){
        Admin admin1 = new Admin(1, "Kazybek", "kazy@gmail.com", 21, "gazi", "admin");//Creating admin.
        LibraryManagementSystem.admins.add(admin1);
        Books book1 = new Books(101, "The tale of two cities", "Charles Dickens", "historical fiction", true, 1859);
        LibraryManagementSystem.addBook(book1);//Add book to book Array List.
        Books book2 = new Books(102, "The little prince", "Antoine de Saint Exupery", "Fantasy", true, 1943);
        LibraryManagementSystem.addBook(book2);
        Books book3 = new Books(103, " Hobbit", "J.R TolkienThe", "Fantasy", true, 1937);
        LibraryManagementSystem.addBook(book3);
        Books book4 = new Books(104, "The Alchemist", "Paulo Coelho", "Fantasy", true, 1988);
        LibraryManagementSystem.addBook(book4);

        LibraryManagementSystem.alltransactions = LibraryManagementSystem.readTransactionsFromFile(transactionsFilePath);

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
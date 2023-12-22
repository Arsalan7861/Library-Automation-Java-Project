import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LibraryManagementApp extends JFrame {
        static String booksFilePath = "books.txt";
        static String usersFilePAth = "users.txt";
        private JButton loginButton;
        private JButton signUpButton;

        public LibraryManagementApp() {
            setTitle("Library");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(700,600);
            setLocationRelativeTo(null);
            setResizable(false);//Turns off the minimize button.
            setVisible(true);

            ImageIcon icon = new ImageIcon("icon.jpg");
            setIconImage(icon.getImage());//Set an icon to the frame.

            loginButton = new JButton("Login");
            signUpButton = new JButton("Sign Up");

            Font defaultFont = new Font("Arial",Font.PLAIN,50);

            loginButton.setFont(defaultFont);
            signUpButton.setFont(defaultFont);

            //Adding actionListener to buttons.
            loginButton.addActionListener(e -> openLoginPage());
            signUpButton.addActionListener(e -> openSignUpPage());
            //Adding background image to the panel.
            JPanel mainPanel = new JPanel(){
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Arsalan Khroush\\OneDrive\\Desktop\\Library Automation Java Project\\millet.jpg");
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
            User admin = new Admin(100, "Arsalan", "arsalan@gmail.com", 21, "12345", "admin");
            JFrame loginFrame = new JFrame("Login Page");
            loginFrame.setSize(300,200);

            JTextField userIdField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JButton loginSubmitButton = new JButton("Submit");

            JPanel loginPanel = new JPanel(new GridLayout(3,2));
            loginPanel.add(new JLabel("User ID:"));
            loginPanel.add(userIdField);
            loginPanel.add(new JLabel("Password:"));
            loginPanel.add(passwordField);
            loginPanel.add(new JLabel());//Empty label for spacing
            loginPanel.add(loginSubmitButton);

            loginFrame.add(loginPanel);

            loginSubmitButton.addActionListener(e -> {
                try {
                    int userId = Integer.parseInt(userIdField.getText());
                    char[] passwordsChars = passwordField.getPassword();
                    String password = new String(passwordsChars);

                    if (LibraryManagementSystem.authenticateUserbyPassword(userId, password)) {
//                        JOptionPane.showMessageDialog(null, "Login successful!");
                        loginFrame.dispose();
                        openMainApplicationPage();
                    } else {
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
            loginFrame.setVisible(true);
        }

        private void openSignUpPage() {
            JFrame signUpFrame = new JFrame("Sign Up Page");
            signUpFrame.setSize(300,200);
            JTextField nameField = new JTextField();
            nameField.setToolTipText("Enter your name.");//When hover over it, shows this.
            JTextField emailField = new JTextField();
            emailField.setToolTipText("Should include '@' and ends with '.com' ");
            JTextField ageField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JButton signUpSubmitButton = new JButton("Submit");

            JPanel signUpPanel = new JPanel(new GridLayout(5,2));
            signUpPanel.add(new JLabel("Name:"));
            signUpPanel.add(nameField);
            signUpPanel.add(new JLabel("Email:"));
            signUpPanel.add(emailField);
            signUpPanel.add(new JLabel("Age:"));
            signUpPanel.add(ageField);
            signUpPanel.add(new JLabel("Password:"));
            signUpPanel.add(passwordField);
            signUpPanel.add(new JLabel());//empty
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
                    if(emailField.getText().isEmpty() || nameField.getText().isEmpty() || password1.isEmpty()) throw new CustomException();
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
                    openMainApplicationPage();
                }catch (CustomException exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage() + ": Name, email and password fields cannot be empty!");
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(null, "String value is not accepted as an age!");
                    ageField.setText("");//Clearing the field after the error.
                }
            });
            signUpFrame.setVisible(true);
        }
        private void openMainApplicationPage(){
            JFrame mainFrame = new JFrame("Main page");
            mainFrame.setSize(700, 600);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
            mainFrame.setResizable(false);

            JPanel panel = new JPanel();
            mainFrame.add(panel);

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            JMenuItem bookMenu = new JMenuItem("Books");
            ImageIcon bookIcon = new ImageIcon("book.png");
            bookMenu.setIcon(bookIcon);
            bookMenu.setIconTextGap(10);
            bookMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false); // Make it non-editable
            // Set the font for the JTextArea
            Font customFont = new Font("Arial", Font.PLAIN, 18);
            textArea.setFont(customFont);
            mainFrame.add(textArea);

            bookMenu.addActionListener(e -> {
                ArrayList<Books> books = LibraryManagementSystem.readBooksFromFile(booksFilePath);
                for (Books book : books) {
                    textArea.append(book.toString() + "\n");
                }
                mainFrame.pack();//Set the frame according to the text.
                mainFrame.setLocationRelativeTo(null); // Center the frame
                mainFrame.setResizable(false);
            });

            JMenuItem exitMenuItem = new JMenuItem("Exit");
            ImageIcon exitIcon = new ImageIcon("exit.jpg");
            exitMenuItem.setIcon(exitIcon);
            exitMenuItem.setIconTextGap(10);
            exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
            exitMenuItem.addActionListener(e -> {
                mainFrame.dispose();
            });

            menu.add(bookMenu);
            menu.addSeparator();//Adds line between menu items.
            menu.add(exitMenuItem);
            menuBar.add(menu);
            mainFrame.setJMenuBar(menuBar);
        }

        public static void main(String[] args) {

            LibraryManagementSystem.allbooks=LibraryManagementSystem.readBooksFromFile(booksFilePath);
            LibraryManagementSystem.allusers=LibraryManagementSystem.readUsersFromFile(usersFilePAth);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LibraryManagementApp();
                }
            });
            LibraryManagementSystem.writeBooksToFile(LibraryManagementSystem.allbooks,booksFilePath);
            LibraryManagementSystem.writeUsersToFile(LibraryManagementSystem.allusers,usersFilePAth);
        }
    }


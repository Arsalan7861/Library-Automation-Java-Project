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
    public int timelyUserId;//to keep user's ID when logged in.

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
        loginButton.setFocusPainted(false);//Remove the button focus.
        signUpButton = new JButton("Sign Up");
        signUpButton.setFocusPainted(false);

        Font defaultFont = new Font("Rockwell",Font.PLAIN, 50);

        loginButton.setFont(defaultFont);
        signUpButton.setFont(defaultFont);

        //Adding actionListener to buttons.
        loginButton.addActionListener(e -> openLoginPage());
        signUpButton.addActionListener(e -> openSignUpPage());
        //Adding background image to the panel.
        JPanel mainPanel = new JPanel(){
            protected void paintComponent(Graphics g){
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
        loginFrame.setSize(600,400);
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

        JPanel loginPanel = new JPanel(new GridLayout(3,2));

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

                if (LibraryManagementSystem.authenticateUserbyPassword(userId, password)) {
                    timelyUserId = userId;
                    JOptionPane.showMessageDialog(null, "Login successful!");
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
        //Set the default(Enter) button to loginSubmitButton
        loginFrame.getRootPane().setDefaultButton(loginSubmitButton);

        loginFrame.setVisible(true);
    }

    private void openSignUpPage() {
        JFrame signUpFrame = new JFrame("Sign Up Page");
        signUpFrame.setSize(600,400);
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

        JPanel signUpPanel = new JPanel(new GridLayout(5,2));
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
                JOptionPane.showMessageDialog(null, "Your User Id: " + userId);//Showing user Id to user.
            }catch (CustomException exception){
                JOptionPane.showMessageDialog(null, exception.getMessage() + ": Name, email and password fields cannot be empty!");
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "String value is not accepted in the age field!");
                ageField.setText("");//Clearing the field after the error.
            }
        });
        //Set the default(Enter) button to signUpSubmitButton
        signUpFrame.getRootPane().setDefaultButton(signUpSubmitButton);

        signUpFrame.setVisible(true);
    }
    private void openMainApplicationPage(){
        JFrame mainFrame = new JFrame("Main page");
        mainFrame.setSize(700, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

//        JPanel panel = new JPanel();
        //Adding background image to the panel.
        JPanel panel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("R.jpeg");
                // Scale the image to fit the panel.
                Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.paintIcon(this, g, 0, 0);
            }
        };
        mainFrame.add(panel);

        JButton userButton = new JButton("Me");
        userButton.setFocusPainted(false);//Remove the focus button.
        panel.add(userButton);

        userButton.addActionListener(e -> {
            for (NormalUser user: LibraryManagementSystem.allusers){
                if (timelyUserId == user.getUserID()){
                    JFrame userFrame = new JFrame("User Info");
                    userFrame.setResizable(false);
                    userFrame.setSize(700, 600);

                    JPanel userPAnel = new JPanel(new GridLayout(5,2)){
                        protected void paintComponent(Graphics g){//Change the background od user panel.
                            super.paintComponent(g);
                            ImageIcon backgroundImage = new ImageIcon("user.png");
                            // Scale the image to fit the panel.
                            Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon scaledIcon = new ImageIcon(scaledImage);
                            scaledIcon.paintIcon(this, g, 0, 0);
                        }
                    };
                    userFrame.add(userPAnel);

                    Font labelFont = new Font("Rockwell", Font.PLAIN, 25);//Set font for the labels.
                    Color labelColor = Color.white;
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
    }

    public static void main(String[] args) {
        Admin admin = new Admin(1313, "Kazybek", "kazy@gmail.com", 21, "123", "admin");

        LibraryManagementSystem.allbooks=LibraryManagementSystem.readBooksFromFile(booksFilePath);
        LibraryManagementSystem.allusers=LibraryManagementSystem.readUsersFromFile(usersFilePAth);
        EventQueue.invokeLater(() -> {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
            new LibraryManagementApp();
        });
        LibraryManagementSystem.writeBooksToFile(LibraryManagementSystem.allbooks,booksFilePath);
        LibraryManagementSystem.writeUsersToFile(LibraryManagementSystem.allusers,usersFilePAth);
    }
}


package gui;

import db.DBReadUser;
import schema.User;
import util.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * GUI for viewing user log in
 * Users can log in by successfully filling the form
 * Users have to first register an account before logging in
 * To log in, press the Login button
 * To register an account instead, press the Register Instead button
 */
public class LoginForm extends JFrame implements ActionListener {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final JLabel result;

    public LoginForm() {
        // Set the title and dimensions of the frame
        setTitle("Login Form");
        setBounds(300, 90, 500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Define the title of the form
        JLabel title = new JLabel("Login Form");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(150, 30);
        container.add(title);

        // Define the name label
        JLabel username = new JLabel("Username");
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setSize(100, 20);
        username.setLocation(50, 100);
        container.add(username);

        // Define the name text field where users will input their username
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setSize(190, 20);
        nameField.setLocation(150, 100);
        container.add(nameField);

        // Define the password label
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setSize(100, 20);
        password.setLocation(50, 150);
        container.add(password);

        // Define the password field where users will input their password
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setSize(190, 20);
        passwordField.setLocation(150, 150);
        container.add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
        loginButton.setSize(100, 20);
        loginButton.setLocation(150, 200);
        loginButton.addActionListener(this);
        container.add(loginButton);

        // Register button
        registerButton = new JButton("Register Instead");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 15));
        registerButton.setSize(150, 20);
        registerButton.setLocation(270, 200);
        registerButton.addActionListener(this);
        container.add(registerButton);

        // Result label which displays success/error messages to the user
        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN, 15));
        result.setSize(500, 25);
        result.setLocation(50, 250);
        container.add(result);

        setVisible(true);
    }

    // Method to define actions for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // get information from GUI
            String nameText = nameField.getText();
            String passwordText = new String(passwordField.getPassword());
            User loggedInUser;
            try {
                // try retrieving the user with the input username and password from DB
                DBReadUser readUser = new DBReadUser();
                loggedInUser = readUser.selectUser(nameText,passwordText);
                // if no result is retrieved, then display the appropriate error message
                if (loggedInUser == null) {
                    result.setText("Username or password is incorrect");
                } else {
                    // if a result was retrieved, save the user details to the current session
                    // then open the dashboard
                    UserSession session = UserSession.getInstance();
                    session.setUser(loggedInUser);
                    this.setVisible(false);
                    this.dispose();
                    new Dashboard();
                }
            // sometimes, the connection to DB can fail
            } catch (SQLException ex) {
                result.setText("Connection to database failed");
            }
        } else if (e.getSource() == registerButton) {
            // change to the register page
            this.setVisible(false);
            this.dispose();
            new RegistrationForm();
        }
    }
}

package gui;

import db.DBInsertUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * GUI for viewing registering new user
 * Users can register a new user to log in with if they successfully fill out the form
 * To register an account after filling out the form, click the register button
 * To log in after the account is register, click the Login Instead button
 */

public class RegistrationForm extends JFrame implements ActionListener {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JButton registerButton;
    private final JButton loginButton;
    private final JLabel result;

    public RegistrationForm() {
        // Set the title and dimensions of the frame
        setTitle("Registration Form");
        setBounds(300, 90, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Define the title of the form
        JLabel title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(150, 30);
        container.add(title);

        // Define the name label
        JLabel name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        name.setSize(100, 20);
        name.setLocation(50, 100);
        container.add(name);

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

        // Confirm Password label
        JLabel confirmPassword = new JLabel("Confirm");
        confirmPassword.setFont(new Font("Arial", Font.PLAIN, 15));
        confirmPassword.setSize(100, 20);
        confirmPassword.setLocation(50, 200);
        container.add(confirmPassword);

        // Confirm Password password field
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 15));
        confirmPasswordField.setSize(190, 20);
        confirmPasswordField.setLocation(150, 200);
        container.add(confirmPasswordField);

        // Register button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 15));
        registerButton.setSize(100, 20);
        registerButton.setLocation(150, 250);
        registerButton.addActionListener(this);
        container.add(registerButton);

        // Login Button
        loginButton = new JButton("Login Instead");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
        loginButton.setSize(150, 20);
        loginButton.setLocation(270, 250);
        loginButton.addActionListener(this);
        container.add(loginButton);

        // Result label which displays success/error messages to the user
        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN, 15));
        result.setSize(500, 25);
        result.setLocation(50, 300);
        container.add(result);

        setVisible(true);
    }

    // Method to define actions for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // get information from GUI
            String nameText = nameField.getText();
            String passwordText = new String(passwordField.getPassword());
            String confirmPasswordText = new String(confirmPasswordField.getPassword());

            // check that username and password are not empty
            // also check that password and confirm password is matching
            // if not, set the appropriate error message
            if (nameText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
                result.setText("Please fill in all fields.");
            } else if (!passwordText.equals(confirmPasswordText)) {
                result.setText("Passwords do not match");
            } else {
                // if username and password is valid, insert the user into the DB
                // if the insertion fails, set the appropriate error message
                try {
                    DBInsertUser insertUser = new DBInsertUser();
                    insertUser.insertUser(nameText, passwordText);
                    result.setText(STR."Registration Successful. Welcome, \{nameText}!");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    result.setText("Username already exists");
                } catch (SQLException ex) {
                    result.setText("Connection to database failed");
                }
            }
        } else if (e.getSource() == loginButton) {
            // return to the dashboard
            this.setVisible(false);
            this.dispose();
            // Show the login form
            new LoginForm();
        }
    }
}


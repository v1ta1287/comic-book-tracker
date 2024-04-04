package gui;

import db.DBInsertUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegistrationForm extends JFrame implements ActionListener {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JButton registerButton;
    private final JButton loginButton;
    private final JLabel result;

    // Constructor
    public RegistrationForm() {
        setTitle("Registration Form");
        setBounds(300, 90, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Components of the Form
        Container container = getContentPane();
        container.setLayout(null);

        // Title
        JLabel title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(150, 30);
        container.add(title);

        // Name
        JLabel name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        name.setSize(100, 20);
        name.setLocation(50, 100);
        container.add(name);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setSize(190, 20);
        nameField.setLocation(150, 100);
        container.add(nameField);

        // Password
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setSize(100, 20);
        password.setLocation(50, 150);
        container.add(password);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setSize(190, 20);
        passwordField.setLocation(150, 150);
        container.add(passwordField);

        // Confirm Password
        JLabel confirmPassword = new JLabel("Confirm");
        confirmPassword.setFont(new Font("Arial", Font.PLAIN, 15));
        confirmPassword.setSize(100, 20);
        confirmPassword.setLocation(50, 200);
        container.add(confirmPassword);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 15));
        confirmPasswordField.setSize(190, 20);
        confirmPasswordField.setLocation(150, 200);
        container.add(confirmPasswordField);

        // Submit Button
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

        // Result
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
            String nameText = nameField.getText();
            String passwordText = new String(passwordField.getPassword());
            String confirmPasswordText = new String(confirmPasswordField.getPassword());

            if (nameText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
                result.setText("Please fill in all fields.");
            } else if (!passwordText.equals(confirmPasswordText)) {
                result.setText("Passwords do not match");
            } else {
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
            this.setVisible(false);
            this.dispose();
            // Show the login form
            new LoginForm();
        }
    }
}


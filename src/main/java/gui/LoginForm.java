package gui;

import db.DBReadUser;
import schema.User;
import util.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginForm extends JFrame implements ActionListener {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final JLabel result;

    public LoginForm() {
        setTitle("Login Form");
        setBounds(300, 90, 500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container container = getContentPane();
        container.setLayout(null);

        JLabel title = new JLabel("Login Form");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(150, 30);
        container.add(title);

        JLabel username = new JLabel("Username");
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setSize(100, 20);
        username.setLocation(50, 100);
        container.add(username);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setSize(190, 20);
        nameField.setLocation(150, 100);
        container.add(nameField);

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

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
        loginButton.setSize(100, 20);
        loginButton.setLocation(150, 200);
        loginButton.addActionListener(this);
        container.add(loginButton);

        registerButton = new JButton("Register Instead");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 15));
        registerButton.setSize(150, 20);
        registerButton.setLocation(270, 200);
        registerButton.addActionListener(this);
        container.add(registerButton);

        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN, 15));
        result.setSize(500, 25);
        result.setLocation(50, 250);
        container.add(result);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Here, you can add the logic to verify the login credentials
            String nameText = nameField.getText();
            String passwordText = new String(passwordField.getPassword());
            User loggedInUser;
            try {
                DBReadUser readUser = new DBReadUser();
                loggedInUser = readUser.selectUser(nameText,passwordText);
                if (loggedInUser == null) {
                    result.setText("Username or password is incorrect");
                } else {
                    UserSession session = UserSession.getInstance();
                    session.setUser(loggedInUser);
                    this.setVisible(false);
                    this.dispose();
                    // Show the login form
                    new Dashboard();
                }

            } catch (SQLException ex) {
                result.setText("Connection to database failed");
            }
            // In a real application, you would check the credentials against a database or another data source
        } else if (e.getSource() == registerButton) {
            this.setVisible(false);
            this.dispose();
            // Show the login form
            new RegistrationForm();
        }
    }
}

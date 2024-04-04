package gui;

import db.DBInsertComic;
import util.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

/**
 * GUI for adding a new comic to the DB
 * Users can add a new comic after successfully filling out the form
 * The GUI will inform users if they made any mistake filling the form
 * Users can press the Submit button to add comic to DB
 * Users are also able to return to the dashboard with the Go Back button
 */

public class ComicNewForm extends JFrame implements ActionListener {
    private final JTextField nameField;
    private final JTextArea descriptionField;
    private final JTextField ratingField;
    private final JComboBox categoryField;
    private final JComboBox recommendedField;
    private final JButton submitButton;
    private final JButton dashboardButton;
    private final JLabel result;

    public ComicNewForm() {
        // Set the title and dimensions of the frame
        setTitle("New Comic");
        setBounds(300, 50, 400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Title
        JLabel title = new JLabel("New Comic Form");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(120, 30);
        container.add(title);

        // Name label
        JLabel name = new JLabel("Name: ");
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        name.setSize(100, 20);
        name.setLocation(50, 100);
        container.add(name);

        // Name input field
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setSize(190, 20);
        nameField.setLocation(150, 100);
        container.add(nameField);

        // Description label
        JLabel description = new JLabel("Description: ");
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setSize(100, 20);
        description.setLocation(50, 150);
        container.add(description);

        // Description input field
        descriptionField = new JTextArea();
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 15));
        descriptionField.setSize(190, 80);
        descriptionField.setLocation(150, 150);
        container.add(descriptionField);

        // Rating label
        JLabel rating = new JLabel("Rating (0-10): ");
        rating.setFont(new Font("Arial", Font.PLAIN, 15));
        rating.setSize(100, 20);
        rating.setLocation(50, 260);
        container.add(rating);

        // Rating input field
        ratingField = new JTextField();
        ratingField.setFont(new Font("Arial", Font.PLAIN, 15));
        ratingField.setSize(190, 20);
        ratingField.setLocation(150, 260);
        container.add(ratingField);

        // Category label
        JLabel category = new JLabel("Category: ");
        category.setFont(new Font("Arial", Font.PLAIN, 15));
        category.setSize(100, 20);
        category.setLocation(50, 310);
        container.add(category);

        // Category drop down menu
        String[] categories = {"Manga", "Manwha", "Manhua", "Western", "Other"};
        categoryField = new JComboBox(categories);
        categoryField.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryField.setSize(190, 30);
        categoryField.setLocation(150, 310);
        container.add(categoryField);

        // recommended label
        JLabel recommended = new JLabel("Enjoy it?: ");
        recommended.setFont(new Font("Arial", Font.PLAIN, 15));
        recommended.setSize(100, 20);
        recommended.setLocation(50, 370);
        container.add(recommended);

        // recommended drop down menu
        String[] recommend = {"Yes", "No"};
        recommendedField = new JComboBox(recommend);
        recommendedField.setFont(new Font("Arial", Font.PLAIN, 12));
        recommendedField.setSize(190, 30);
        recommendedField.setLocation(150, 370);
        container.add(recommendedField);

        // label to show user error/success message
        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN, 15));
        result.setSize(500, 25);
        result.setLocation(50, 430);
        container.add(result);

        // Button to add a new comic after filling the form
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        submitButton.setSize(100, 20);
        submitButton.setLocation(50, 490);
        submitButton.addActionListener(this);
        container.add(submitButton);

        // Button to return to dashboard
        dashboardButton = new JButton("Go back");
        dashboardButton.setFont(new Font("Arial", Font.PLAIN, 15));
        dashboardButton.setSize(100, 20);
        dashboardButton.setLocation(220, 490);
        dashboardButton.addActionListener(this);
        container.add(dashboardButton);

        setVisible(true);
    }

    // Method to define actions for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            boolean validComic = true; // Determine if the form was filled correctly

            // retrieve values from GUI
            String nameText = nameField.getText();
            String descriptionText = descriptionField.getText();
            String ratingText = ratingField.getText();
            String categoryText = (String) categoryField.getSelectedItem();
            String recommendedText = (String) recommendedField.getSelectedItem();

            // convert recommended to a boolean integer so SQL can understand (0 or 1)
            int recommended;
            if (Objects.equals(recommendedText, "Yes")) {
                recommended = 1;
            } else {
                recommended = 0;
            }

            // check if name is empty
            if (nameText.isEmpty()) {
                result.setText("Name cannot be empty");
                validComic = false;
            }

            // check that rating is an integer between 0-10
            int rating = 0;
            try {
                rating = Integer.parseInt(ratingText);
            } catch (NumberFormatException error) {
                result.setText("Rating must be an integer between 0-10");
                validComic = false;
            }
            if (rating < 0 || rating > 10) {
                result.setText("Rating must be an integer between 0-10");
                validComic = false;
            }

            // if the form was filled out correctly, continue to insert into DB
            if (validComic){
                try {
                    // call DB class to perform SQL insertion
                    // Userid is required for Comic foreign key, so UserSession is used
                    // set appropriate success/error messages
                    UserSession session = UserSession.getInstance();
                    int sessionId = session.getUser().getUserid();
                    DBInsertComic insertComic = new DBInsertComic();
                    insertComic.insertComic(nameText, descriptionText, rating, categoryText, recommended, sessionId);
                    result.setText("Comic successfully added!");
                } catch (SQLIntegrityConstraintViolationException error) {
                    result.setText("A comic with this name already exists");
                } catch (SQLException error) {
                    result.setText("Connection to DB failed");
                }
            }
        } else if (e.getSource() == dashboardButton) {
            // go to dashboard
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        }
    }
}

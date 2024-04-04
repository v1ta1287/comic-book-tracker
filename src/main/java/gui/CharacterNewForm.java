package gui;

import db.DBInsertCharacter;
import schema.Comic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

/**
 * GUI for adding a new character to the DB
 * Users can add a new character after successfully filling out the form
 * The GUI will inform users if they made any mistake filling the form
 * Users can press the Submit button to add character to DB
 * Users are also able to return to the dashboard with the Go Back button
 */
public class CharacterNewForm extends JFrame implements ActionListener {
    private final JTextField nameField;
    private final JTextArea descriptionField;
    private final JTextField ageField;
    private final JComboBox statusField;
    private final JComboBox comicField;
    private final JButton submitButton;
    private final JButton dashboardButton;
    private final JLabel result;
    private final ArrayList<Comic> comics;
    public CharacterNewForm(ArrayList<Comic> comics) {
        // Retrieve the list of characters from the dashboard DB call
        this.comics = comics;

        // Set the title and dimensions of the frame
        setTitle("New Character");
        setBounds(300, 50, 400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Title
        JLabel title = new JLabel("New Character Form");
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

        // Age label
        JLabel age = new JLabel("Age (integer): ");
        age.setFont(new Font("Arial", Font.PLAIN, 15));
        age.setSize(100, 20);
        age.setLocation(50, 260);
        container.add(age);

        // Age input field
        ageField = new JTextField();
        ageField.setFont(new Font("Arial", Font.PLAIN, 15));
        ageField.setSize(190, 20);
        ageField.setLocation(150, 260);
        container.add(ageField);

        // Status label
        JLabel status = new JLabel("Status: ");
        status.setFont(new Font("Arial", Font.PLAIN, 15));
        status.setSize(100, 20);
        status.setLocation(50, 310);
        container.add(status);

        // Status dropdown menu
        String[] statuses = {"Alive", "Dead", "Unknown"};
        statusField = new JComboBox(statuses);
        statusField.setFont(new Font("Arial", Font.PLAIN, 12));
        statusField.setSize(190, 30);
        statusField.setLocation(150, 310);
        container.add(statusField);

        // Comic label
        JLabel comicLabel = new JLabel("From comic: ");
        comicLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        comicLabel.setSize(100, 20);
        comicLabel.setLocation(50, 370);
        container.add(comicLabel);

        // Comic drop down menu
        // First retrieve the names of all the comics from the ArrayList<Comic> comics
        ArrayList<String> comicsList = new ArrayList<String>();
        for (Comic comic : comics) {
            comicsList.add(comic.getName());
        }

        // Then use the comic names to populate the drop down menu
        comicField = new JComboBox(new DefaultComboBoxModel<String>(comicsList.toArray(new String[0])));
        comicField.setFont(new Font("Arial", Font.PLAIN, 12));
        comicField.setSize(190, 30);
        comicField.setLocation(150, 370);
        container.add(comicField);

        // label to show user error/success message
        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN, 15));
        result.setSize(500, 25);
        result.setLocation(50, 430);
        container.add(result);

        // Button to add a new character after filling the form
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
            boolean validCharacter = true; // Determine if the form was filled correctly

            // retrieve values from GUI
            String nameText = nameField.getText();
            String descriptionText = descriptionField.getText();
            String ageText = ageField.getText();
            String statusText = (String) statusField.getSelectedItem();
            int comicIndex = comicField.getSelectedIndex();

            // check if name is empty
            if (nameText.isEmpty()) {
                result.setText("Name cannot be empty");
                validCharacter = false;
            }

            // check if age is an integer
            int age = 0;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException error) {
                result.setText("Age must be an integer");
                validCharacter = false;
            }

            // check if there are any comics available
            if (comicIndex == -1) {
                result.setText("You need to first add a Comic!");
                validCharacter = false;
            }

            // if the form was filled out correctly, continue to insert into DB
            if (validCharacter){
                try {
                    // call DB class to perform SQL insertion
                    // set appropriate success/error messages
                    int comicId = comics.get(comicIndex).getComicId();
                    DBInsertCharacter insertCharacter = new DBInsertCharacter();
                    insertCharacter.insertCharacter(nameText, descriptionText, age, statusText, comicId);
                    result.setText("Character successfully added!");
                } catch (SQLIntegrityConstraintViolationException error) {
                    result.setText("A character with this name already exists");
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

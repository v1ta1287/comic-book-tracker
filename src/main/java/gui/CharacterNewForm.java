package gui;

import db.DBInsertCharacter;
import db.DBInsertComic;
import schema.Comic;
import util.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Objects;

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
        this.comics = comics;
        setTitle("New Character");
        setBounds(300, 50, 400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container container = getContentPane();
        container.setLayout(null);

        JLabel title = new JLabel("New Character Form");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(120, 30);
        container.add(title);

        JLabel name = new JLabel("Name: ");
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        name.setSize(100, 20);
        name.setLocation(50, 100);
        container.add(name);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setSize(190, 20);
        nameField.setLocation(150, 100);
        container.add(nameField);

        JLabel description = new JLabel("Description: ");
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setSize(100, 20);
        description.setLocation(50, 150);
        container.add(description);

        descriptionField = new JTextArea();
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 15));
        descriptionField.setSize(190, 80);
        descriptionField.setLocation(150, 150);
        container.add(descriptionField);

        JLabel age = new JLabel("Age (integer): ");
        age.setFont(new Font("Arial", Font.PLAIN, 15));
        age.setSize(100, 20);
        age.setLocation(50, 260);
        container.add(age);

        ageField = new JTextField();
        ageField.setFont(new Font("Arial", Font.PLAIN, 15));
        ageField.setSize(190, 20);
        ageField.setLocation(150, 260);
        container.add(ageField);

        JLabel status = new JLabel("Status: ");
        status.setFont(new Font("Arial", Font.PLAIN, 15));
        status.setSize(100, 20);
        status.setLocation(50, 310);
        container.add(status);

        String[] statuses = {"Alive", "Dead", "Unknown"};
        statusField = new JComboBox(statuses);
        statusField.setFont(new Font("Arial", Font.PLAIN, 12));
        statusField.setSize(190, 30);
        statusField.setLocation(150, 310);
        container.add(statusField);

        JLabel comicLabel = new JLabel("From comic: ");
        comicLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        comicLabel.setSize(100, 20);
        comicLabel.setLocation(50, 370);
        container.add(comicLabel);

        ArrayList<String> comicsList = new ArrayList<String>();
        for (Comic comic : comics) {
            comicsList.add(comic.getName());
        }

        comicField = new JComboBox(new DefaultComboBoxModel<String>(comicsList.toArray(new String[0])));
        comicField.setFont(new Font("Arial", Font.PLAIN, 12));
        comicField.setSize(190, 30);
        comicField.setLocation(150, 370);
        container.add(comicField);

        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN, 15));
        result.setSize(500, 25);
        result.setLocation(50, 430);
        container.add(result);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        submitButton.setSize(100, 20);
        submitButton.setLocation(50, 490);
        submitButton.addActionListener(this);
        container.add(submitButton);

        dashboardButton = new JButton("Go back");
        dashboardButton.setFont(new Font("Arial", Font.PLAIN, 15));
        dashboardButton.setSize(100, 20);
        dashboardButton.setLocation(220, 490);
        dashboardButton.addActionListener(this);
        container.add(dashboardButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            boolean validCharacter = true;
            String nameText = nameField.getText();
            String descriptionText = descriptionField.getText();
            String ageText = ageField.getText();
            String statusText = (String) statusField.getSelectedItem();
            int comicIndex = comicField.getSelectedIndex();

            if (nameText.isEmpty()) {
                result.setText("Name cannot be empty");
                validCharacter = false;
            }

            int age = 0;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException error) {
                result.setText("Age must be an integer");
                validCharacter = false;
            }

            if (comicIndex == -1) {
                result.setText("You need to first add a Comic!");
                validCharacter = false;
            }

            if (validCharacter){
                try {
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
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        }
    }
}

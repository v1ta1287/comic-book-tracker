package gui;

import db.DBDeleteCharacter;
import schema.Character;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * GUI for viewing the details of a comic book
 * Users are able to delete the character from the DB
 * Users are also able to return to the dashboard
 */
public class CharacterViewForm extends JFrame implements ActionListener {
    private final JButton dashboardButton;
    private final JButton deleteButton;
    private final Character character;

    public CharacterViewForm(Character character) {
        // Retrieve the list of characters from the dashboard DB call
        this.character = character;

        // Set the title and dimensions of the frame
        setTitle(character.getName());
        setBounds(300, 90, 400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Show character name
        JLabel name = new JLabel(character.getName());
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(300, 30);
        name.setLocation(150, 30);
        container.add(name);

        // Show character description
        JLabel description = new JLabel(STR."<html>Description: \{character.getDescription()}</html>");
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setSize(300, 150);
        description.setLocation(50, 50);
        container.add(description);

        // Show character age
        JLabel rating = new JLabel(STR."Age: \{character.getAge()}");
        rating.setFont(new Font("Arial", Font.PLAIN, 15));
        rating.setSize(300, 30);
        rating.setLocation(50, 200);
        container.add(rating);

        // Show character category
        JLabel category = new JLabel(STR."Status: \{character.getStatus()}");
        category.setFont(new Font("Arial", Font.PLAIN, 15));
        category.setSize(300, 30);
        category.setLocation(50, 250);
        container.add(category);

        // Show which comic the character belongs to
        JLabel inComic = new JLabel(STR."Comic: \{character.getComicName()}");
        inComic.setFont(new Font("Arial", Font.PLAIN, 15));
        inComic.setSize(300, 30);
        inComic.setLocation(50, 300);
        container.add(inComic);

        // Button to return to dashboard
        dashboardButton = new JButton("Go Back");
        dashboardButton.setFont(new Font("Arial", Font.PLAIN, 15));
        dashboardButton.setSize(100, 20);
        dashboardButton.setLocation(50, 400);
        dashboardButton.addActionListener(this);
        container.add(dashboardButton);

        // Button to delete character
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 15));
        deleteButton.setSize(150, 20);
        deleteButton.setLocation(180, 400);
        deleteButton.addActionListener(this);
        container.add(deleteButton);

        setVisible(true);
    }

    // Method to define actions for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardButton) {
            // go to dashboard
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        } else if (e.getSource() == deleteButton) {
            // call DB class to perform SQL deletion
            try {
                DBDeleteCharacter deleteCharacter = new DBDeleteCharacter();
                deleteCharacter.deleteCharacter(character.getCharacterId());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            // go to dashboard
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        }
    }
}

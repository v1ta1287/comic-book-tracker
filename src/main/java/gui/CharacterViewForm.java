package gui;

import db.DBDeleteCharacter;
import schema.Character;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * GUI for viewing a single character
 */
public class CharacterViewForm extends JFrame implements ActionListener {
    private final JButton dashboardButton;
    private final JButton deleteButton;
    private final Character character;

    public CharacterViewForm(Character character) {
        this.character = character;
        setTitle(character.getName());
        setBounds(300, 90, 400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container container = getContentPane();
        container.setLayout(null);

        JLabel name = new JLabel(character.getName());
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(300, 30);
        name.setLocation(150, 30);
        container.add(name);

        JLabel description = new JLabel(STR."<html>Description: \{character.getDescription()}</html>");
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setSize(300, 150);
        description.setLocation(50, 50);
        container.add(description);

        JLabel rating = new JLabel(STR."Age: \{character.getAge()}");
        rating.setFont(new Font("Arial", Font.PLAIN, 15));
        rating.setSize(300, 30);
        rating.setLocation(50, 200);
        container.add(rating);

        JLabel category = new JLabel(STR."Status: \{character.getStatus()}");
        category.setFont(new Font("Arial", Font.PLAIN, 15));
        category.setSize(300, 30);
        category.setLocation(50, 250);
        container.add(category);

        JLabel recommended = new JLabel(STR."Comic: \{character.getComicName()}");
        recommended.setFont(new Font("Arial", Font.PLAIN, 15));
        recommended.setSize(300, 30);
        recommended.setLocation(50, 300);
        container.add(recommended);

        dashboardButton = new JButton("Go Back");
        dashboardButton.setFont(new Font("Arial", Font.PLAIN, 15));
        dashboardButton.setSize(100, 20);
        dashboardButton.setLocation(50, 400);
        dashboardButton.addActionListener(this);
        container.add(dashboardButton);

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 15));
        deleteButton.setSize(150, 20);
        deleteButton.setLocation(180, 400);
        deleteButton.addActionListener(this);
        container.add(deleteButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardButton) {
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        } else if (e.getSource() == deleteButton) {
            try {
                DBDeleteCharacter deleteCharacter = new DBDeleteCharacter();
                deleteCharacter.deleteCharacter(character.getCharacterId());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        }
    }
}

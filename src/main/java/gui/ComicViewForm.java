package gui;

import db.DBDeleteComic;
import schema.Comic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * GUI for viewing the details of a comic book
 * Users are able to delete the comic book from the DB
 * Users are also able to return to the dashboard
 */
public class ComicViewForm extends JFrame implements ActionListener {
    private final JButton dashboardButton;
    private final JButton deleteButton;
    private final Comic comic;

    public ComicViewForm(Comic comic) {
        // Retrieve the list of comics from the dashboard DB call
        this.comic = comic;

        // Set the title and dimensions of the frame
        setTitle(comic.getName());
        setBounds(300, 90, 400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Show the comic name
        JLabel name = new JLabel(comic.getName());
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(300, 30);
        name.setLocation(150, 30);
        container.add(name);

        // Show the comic description
        JLabel description = new JLabel(STR."<html>Description: \{comic.getDescription()}</html>");
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setSize(300, 150);
        description.setLocation(50, 50);
        container.add(description);

        // Show the comic rating
        JLabel rating = new JLabel(STR."Rating: \{comic.getRating()}");
        rating.setFont(new Font("Arial", Font.PLAIN, 15));
        rating.setSize(300, 30);
        rating.setLocation(50, 200);
        container.add(rating);

        // Show the comic category
        JLabel category = new JLabel(STR."Category: \{comic.getCategory()}");
        category.setFont(new Font("Arial", Font.PLAIN, 15));
        category.setSize(300, 30);
        category.setLocation(50, 250);
        container.add(category);

        // Show if the comic is recommended
        JLabel recommended = new JLabel(STR."Recommend: \{comic.isRecommended()}");
        recommended.setFont(new Font("Arial", Font.PLAIN, 15));
        recommended.setSize(300, 30);
        recommended.setLocation(50, 300);
        container.add(recommended);

        // Button to return to dashboard
        dashboardButton = new JButton("Go Back");
        dashboardButton.setFont(new Font("Arial", Font.PLAIN, 15));
        dashboardButton.setSize(100, 20);
        dashboardButton.setLocation(50, 400);
        dashboardButton.addActionListener(this);
        container.add(dashboardButton);

        // Button to delete current comic
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
                DBDeleteComic deleteComic = new DBDeleteComic();
                deleteComic.deleteComic(comic.getComicId());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            // return to dashboard
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        }
    }
}

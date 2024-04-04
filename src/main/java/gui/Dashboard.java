package gui;

import db.DBReadCharacters;
import db.DBReadComics;
import schema.Character;
import schema.Comic;
import schema.User;
import util.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * GUI for accessing all the functionality of the program
 * Once a user signs in, they can access their dashboard
 * They are able to view all the comics and characters they've added
 * They are also able to add new comics and characters
 * There is also an option to log out and change users
 */
public class Dashboard extends JFrame implements ActionListener {

    private final JButton viewAllComicsButton;
    private final JButton viewAllCharactersButton;
    private final JButton newComicButton;
    private final JButton newCharacterButton;
    private final JButton logoutButton;

    public Dashboard() {
        // Set the title and dimensions of the frame
        setTitle("Dashboard");
        setBounds(300, 90, 500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Define the title of the form
        // Include the current user's name in the title by using UserSession
        UserSession session = UserSession.getInstance();
        User currentUser = session.getUser();
        JLabel title = new JLabel(STR."Welcome \{currentUser.getUsername()}");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(175, 30);
        container.add(title);

        // Button for viewing all comics
        viewAllComicsButton = new JButton("View All Comics");
        viewAllComicsButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewAllComicsButton.setSize(150, 50);
        viewAllComicsButton.setLocation(75, 100);
        viewAllComicsButton.addActionListener(this);
        container.add(viewAllComicsButton);

        // Button for viewing all characters
        viewAllCharactersButton = new JButton("View Characters");
        viewAllCharactersButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewAllCharactersButton.setSize(150, 50);
        viewAllCharactersButton.setLocation(250, 100);
        viewAllCharactersButton.addActionListener(this);
        container.add(viewAllCharactersButton);

        // Button for adding a new comic
        newComicButton = new JButton("New Comic");
        newComicButton.setFont(new Font("Arial", Font.PLAIN, 12));
        newComicButton.setSize(150, 50);
        newComicButton.setLocation(75, 200);
        newComicButton.addActionListener(this);
        container.add(newComicButton);

        // Button for adding a new character
        newCharacterButton = new JButton("New Character");
        newCharacterButton.setFont(new Font("Arial", Font.PLAIN, 12));
        newCharacterButton.setSize(150, 50);
        newCharacterButton.setLocation(250, 200);
        newCharacterButton.addActionListener(this);
        container.add(newCharacterButton);

        // Button for logging out
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutButton.setSize(150, 50);
        logoutButton.setLocation(75, 300);
        logoutButton.addActionListener(this);
        container.add(logoutButton);

        setVisible(true);
    }

    // Method to define actions for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAllComicsButton) {
            this.setVisible(false);
            this.dispose();
            // retrieve all the comics belonging to the current user from database
            // into an array which is then parsed into the ViewAllComics page
            ArrayList<Comic> comics;
            UserSession session = UserSession.getInstance();
            int sessionid = session.getUser().getUserid();
            try {
                DBReadComics readComics = new DBReadComics();
                comics = readComics.selectComics(sessionid);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            new ViewAllComics(comics);
        } else if (e.getSource() == viewAllCharactersButton ) {
            this.setVisible(false);
            this.dispose();
            // retrieve all the characters belonging to the current user from database
            // into an array which is then parsed into the ViewAllComics page
            ArrayList<Character> characters;
            UserSession session = UserSession.getInstance();
            int sessionid = session.getUser().getUserid();
            try {
                DBReadCharacters readCharacters = new DBReadCharacters();
                characters = readCharacters.selectCharacters(sessionid);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            new ViewAllCharacters(characters);

        } else if (e.getSource() == newComicButton) {
            // change to new comic page
            this.setVisible(false);
            this.dispose();
            new ComicNewForm();
        } else if (e.getSource() == newCharacterButton ) {
            this.setVisible(false);
            this.dispose();
            // retrieve all the comics belonging to the current user from database
            // into an array which is then parsed into the CharacterNewForm page
            ArrayList<Comic> comics;
            UserSession session = UserSession.getInstance();
            int sessionid = session.getUser().getUserid();
            try {
                DBReadComics readComics = new DBReadComics();
                comics = readComics.selectComics(sessionid);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            new CharacterNewForm(comics);
        }
        else if (e.getSource() == logoutButton) {
            // change to login page
            this.setVisible(false);
            this.dispose();
            new LoginForm();
        }
    }
}

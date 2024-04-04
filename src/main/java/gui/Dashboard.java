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

public class Dashboard extends JFrame implements ActionListener {

    private final JButton viewAllComicsButton;
    private final JButton viewAllCharactersButton;
    private final JButton newComicButton;
    private final JButton newCharacterButton;
    private final JButton logoutButton;

    public Dashboard() {
        setTitle("Dashboard");
        setBounds(300, 90, 500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container container = getContentPane();
        container.setLayout(null);

        UserSession session = UserSession.getInstance();
        User currentUser = session.getUser();
        JLabel title = new JLabel(STR."Welcome \{currentUser.getUsername()}");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(175, 30);
        container.add(title);

        viewAllComicsButton = new JButton("View All Comics");
        viewAllComicsButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewAllComicsButton.setSize(150, 50);
        viewAllComicsButton.setLocation(75, 100);
        viewAllComicsButton.addActionListener(this);
        container.add(viewAllComicsButton);

        viewAllCharactersButton = new JButton("View Characters");
        viewAllCharactersButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewAllCharactersButton.setSize(150, 50);
        viewAllCharactersButton.setLocation(250, 100);
        viewAllCharactersButton.addActionListener(this);
        container.add(viewAllCharactersButton);

        newComicButton = new JButton("New Comic");
        newComicButton.setFont(new Font("Arial", Font.PLAIN, 12));
        newComicButton.setSize(150, 50);
        newComicButton.setLocation(75, 200);
        newComicButton.addActionListener(this);
        container.add(newComicButton);

        newCharacterButton = new JButton("New Character");
        newCharacterButton.setFont(new Font("Arial", Font.PLAIN, 12));
        newCharacterButton.setSize(150, 50);
        newCharacterButton.setLocation(250, 200);
        newCharacterButton.addActionListener(this);
        container.add(newCharacterButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutButton.setSize(150, 50);
        logoutButton.setLocation(75, 300);
        logoutButton.addActionListener(this);
        container.add(logoutButton);


        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAllComicsButton) {
            this.setVisible(false);
            this.dispose();
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
            this.setVisible(false);
            this.dispose();
            // Show the login form
            new ComicNewForm();
        } else if (e.getSource() == newCharacterButton ) {
            this.setVisible(false);
            this.dispose();
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
            this.setVisible(false);
            this.dispose();
            // Show the login form
            new LoginForm();
        }
    }
}

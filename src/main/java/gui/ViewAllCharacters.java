package gui;

import schema.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GUI for viewing all available characters
 * This page will display all characters retrieved from the database
 * that are related to the current user session
 * Select a character by clicking on the character name in the list
 * To view further details of the character, click the View Details button
 * To go back to the dashboard, click the Go Back button
 */
public class ViewAllCharacters extends JFrame implements ActionListener {
    private final JList<String> list;
    private final DefaultListModel<String> listModel;
    private final JButton viewButton;
    private final JButton dashboardButton;
    ArrayList<Character> characters;

    public ViewAllCharacters(ArrayList<Character> characters) {
        // Retrieve the list of characters from the dashboard DB call
        this.characters = characters;

        // Set the title and dimensions of the frame
        setTitle("View Characters");
        setBounds(300, 90, 400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Define the container of the frame, this will hold the other components
        Container container = getContentPane();
        container.setLayout(null);

        // Define a listModel to hold the names of all the characters
        listModel = new DefaultListModel<>();
        characters.forEach(character->listModel.addElement(character.getName()));

        // Create a JList to display the names of all the characters
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create a scrollable pane to contain the JList
        // This allows for the JList to be scrollable if there are too many characters
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setSize(300, 200);
        scrollPane.setLocation(40, 30);
        container.add(scrollPane);

        // Define a button for viewing details of selected character
        viewButton = new JButton("View Details");
        container.add(viewButton);
        viewButton.setSize(130, 50);
        viewButton.setLocation(40, 250);
        viewButton.addActionListener(this);

        // Define a button to return back to the dashboard
        dashboardButton = new JButton("Go Back");
        container.add(dashboardButton);
        dashboardButton.setSize(130, 50);
        dashboardButton.setLocation(210, 250);
        dashboardButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewButton){
            // after clicking the view button, get the selected character
            // open a new page showing the details of that selected character
            int index = list.getSelectedIndex();
            if (index > -1){
                this.setVisible(false);
                this.dispose();
                new CharacterViewForm(characters.get(index));
            }
        } else if (e.getSource() == dashboardButton) {
            // return to the dashboard
            this.setVisible(false);
            this.dispose();
            new Dashboard();
        }

    }

}

package db;

import schema.Character;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Establishes connection to database and performs an SQL SELECT query for all characters
 * belonging to a user
 */
public class DBReadCharacters extends DBConnection{
    public DBReadCharacters() throws SQLException {

    }

    public ArrayList<Character> selectCharacters(int userid) throws SQLException {
        PreparedStatement readStatement =
                connection.prepareStatement(
                        STR."SELECT Characters.*, Comics.Name AS ComicName FROM Characters JOIN Comics ON Characters.Comicid = Comics.Comicid JOIN Users ON Comics.Userid = Users.Userid WHERE Users.Userid = \{userid} ;"
                );
        ResultSet resultSet = readStatement.executeQuery();

        // Build Character objects with retrieved data and fill the ArrayList<Character>
        ArrayList<Character> selectedCharacters = new ArrayList<>();
        while (resultSet.next()) {
            int characterId = resultSet.getInt("Characterid");
            String characterName = resultSet.getString("Name");
            String characterDescription = resultSet.getString("Description");
            String comicName = resultSet.getString("ComicName");
            int characterAge = resultSet.getInt("Age");
            String characterStatus = resultSet.getString("Status");
            Character currCharacter = new Character(characterId,characterName, characterDescription, comicName, characterAge, characterStatus);
            selectedCharacters.add(currCharacter);
        }

        connection.close();
        return selectedCharacters;
    }
}

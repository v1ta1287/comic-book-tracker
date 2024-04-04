package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Establishes connection to database and performs an SQL DELETE query for a character
 */
public class DBDeleteCharacter extends DBConnection{
    public DBDeleteCharacter() throws SQLException {

    }
    public void deleteCharacter(int characterId) throws SQLException {
        PreparedStatement deleteStatement =
                connection.prepareStatement(
                        STR."DELETE FROM Characters WHERE Characterid = '\{characterId}'");
        deleteStatement.executeUpdate();
        connection.close();
    }
}

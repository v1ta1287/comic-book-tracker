package db;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Establishes connection to database and performs an SQL DELETE query for a comic
 * Note that when a comic is deleted, all related characters are also deleted
 */
public class DBDeleteComic extends DBConnection{
    public DBDeleteComic() throws SQLException {

    }

    public void deleteComic(int comicId) throws SQLException {
        PreparedStatement deleteStatement;
        deleteStatement =
                connection.prepareStatement(
                        STR."DELETE FROM Characters WHERE Comicid = '\{comicId}'");
        deleteStatement.executeUpdate();
        deleteStatement =
                connection.prepareStatement(
                        STR."DELETE FROM Comics WHERE Comicid = '\{comicId}'");
        deleteStatement.executeUpdate();
        connection.close();
    }
}

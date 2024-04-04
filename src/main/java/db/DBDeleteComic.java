package db;


import java.sql.PreparedStatement;
import java.sql.SQLException;

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

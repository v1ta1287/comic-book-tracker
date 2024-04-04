package db;

import schema.Comic;
import schema.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBReadComics extends DBConnection{
    public DBReadComics() throws SQLException {

    }

    public ArrayList<Comic> selectComics(int userid) throws SQLException {
        PreparedStatement readStatement =
                connection.prepareStatement(
                        STR."SELECT * FROM Comics WHERE Userid = '\{userid}';");
        ResultSet resultSet = readStatement.executeQuery();

        ArrayList<Comic> selectedComics = new ArrayList<>();
        while (resultSet.next()) {
            int comicId = resultSet.getInt("Comicid");
            String comicName = resultSet.getString("Name");
            String comicDescription = resultSet.getString("Description");
            int comicRating = resultSet.getInt("Rating");
            String comicCategory = resultSet.getString("Category");
            boolean comicRecommended = resultSet.getBoolean("Recommended");
            Comic currComic = new Comic(comicId, comicName, comicDescription, comicRating, comicCategory, comicRecommended);
            selectedComics.add(currComic);
        }

        connection.close();
        return selectedComics;
    }
}

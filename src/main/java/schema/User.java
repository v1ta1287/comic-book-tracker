package schema;

/**
 * Class representing Users table from DB
 * userid: Unique identifier of the user
 * username: Username
 */
public class User {
    private int userid;
    private String username;
    public User(int userid, String username) {
        this.userid = userid;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public int getUserid() {return userid; }

}

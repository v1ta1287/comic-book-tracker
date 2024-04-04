package util;

import schema.User;

/**
 * Singleton that keeps track of which user has logged in
 * Multiple functionalities require the userid of the current user
 */
public class UserSession {
    private static UserSession single_instance = null;
    public User user;

    // Private constructor restricted to this class itself
    private UserSession() {

    }

    // Static method to create instance of Singleton class
    public static UserSession getInstance() {
        if (single_instance == null)
            single_instance = new UserSession();

        return single_instance;
    }

    // User login sets user session
    public void setUser(User user) {
        this.user = user;
    }

    // Multiple DB calls require userid of the current user session
    public User getUser() {
        return user;
    }


}

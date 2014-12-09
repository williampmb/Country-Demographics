package country.demographics.forms;

/**
 *
 * @author Jim Lexen
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private int userType;
    
    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
    
    public String toString() {
        return username;
    }
    
    public String toStringData() {
        return "USER: name=" + username + ", type=" + userType + ", id=" + userId;
    }
}

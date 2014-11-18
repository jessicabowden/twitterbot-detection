/**
 * Created by Jessica on 18/11/2014.
 */
public class User {
    String username;
    boolean isBot;
    long user_id;
    boolean isVerified;

    public User(String username, boolean isBot, long user_id) {
        this.username = username;
        this.isBot = isBot;
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        if (isVerified() == true) {
            this.isBot = false;
        }
        this.isBot = isBot;
    }

    public long getUser_id() {
        return user_id;
    }

    public boolean isVerified() {
        return isVerified;
    }
}

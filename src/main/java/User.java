/**
 * Created by Jessica on 18/11/2014.
 */
public class User {
    String username;
    long user_id;
    boolean isVerified;
    BotStatus botStatus;

    public User(String username, BotStatus botStatus, long user_id) {
        this.username = username;
        this.botStatus = botStatus;
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public BotStatus getBotStatus() {
        return botStatus;
    }

    public void setBotStatus(BotStatus botStatus) {
        this.botStatus = botStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BotStatus botStatus() {
        return botStatus;
    }

    public void setBot(BotStatus botStatus) {
        this.botStatus = botStatus;
    }

    public boolean isVerified() {
        return isVerified;
    }
}

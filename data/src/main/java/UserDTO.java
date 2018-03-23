import org.json.JSONObject;

/**
 * Created by Jessica on 18/11/2014.
 */
public class UserDTO {
    String username;
    long user_id;
    boolean isVerified;
    BotStatus botStatus;
    long friendCount;
    long followerCount;
    String name;
    String description;
    String urlInDescription;
    String language;
    String timeZone;
    String location;
    boolean defaultImage;
    long statusCount;
    long favouriteCount;

    public UserDTO createUserFromJson(JSONObject jsonObject, BotStatus type) {
        UserDTO userDTO = new UserDTO();

        try {
            userDTO.setUsername(jsonObject.get("screen_name").toString());
            userDTO.setUser_id(jsonObject.getLong("id"));
            userDTO.setVerified(jsonObject.getBoolean("verified"));
            userDTO.setBotStatus(type);
            userDTO.setFriendCount(jsonObject.getInt("friends_count"));
            userDTO.setFollowerCount(jsonObject.getInt("followers_count"));
            userDTO.setName(jsonObject.get("name").toString());
            userDTO.setDescription(jsonObject.get("description").toString());
            userDTO.setDefaultImage(jsonObject.getBoolean("default_profile_image"));
            userDTO.setStatusCount(jsonObject.getInt("statuses_count"));
            userDTO.setFavouriteCount(jsonObject.getInt("favourites_count"));
            if ((!jsonObject.get("time_zone").equals(null))) {
                userDTO.setTimeZone(jsonObject.getString("time_zone"));
            }
            if (!(jsonObject.get("location").equals(null))) {
                userDTO.setLocation(jsonObject.get("location").toString());
            }
            if (!(jsonObject.get("lang").equals(null))) {
                userDTO.setLanguage(jsonObject.get("lang").toString());
            }
            if (!(jsonObject.get("url").equals(null))) {
                userDTO.setUrlInDescription(jsonObject.get("url").toString());
            }

        }
        catch (NullPointerException e) {}

        return userDTO;
    }

    public UserDTO(String username, long user_id, boolean isVerified, BotStatus botStatus, long friendCount, long followerCount, String name, String description, String urlInDescription, String language, String timeZone, String location, boolean defaultImage, long statusCount, long favouriteCount) {
        this.username = username;
        this.user_id = user_id;
        this.isVerified = isVerified;
        this.botStatus = botStatus;
        this.friendCount = friendCount;
        this.followerCount = followerCount;
        this.name = name;
        this.description = description;
        this.urlInDescription = urlInDescription;
        this.language = language;
        this.timeZone = timeZone;
        this.location = location;
        this.defaultImage = defaultImage;
        this.statusCount = statusCount;
        this.favouriteCount = favouriteCount;
    }

    public UserDTO() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setBotStatus(BotStatus botStatus) {
        this.botStatus = botStatus;
    }

    public void setFriendCount(long friendCount) {
        this.friendCount = friendCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrlInDescription(String urlInDescription) {
        this.urlInDescription = urlInDescription;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDefaultImage(boolean defaultImage) {
        this.defaultImage = defaultImage;
    }

    public void setStatusCount(long statusCount) {
        this.statusCount = statusCount;
    }

    public void setFavouriteCount(long favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public BotStatus getBotStatus() {
        return botStatus;
    }

    public long getFriendCount() {
        return friendCount;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlInDescription() {
        return urlInDescription;
    }

    public String getLanguage() {
        return language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getLocation() {
        return location;
    }

    public boolean isDefaultImage() {
        return defaultImage;
    }

    public long getStatusCount() {
        return statusCount;
    }

    public long getFavouriteCount() {
        return favouriteCount;
    }

    public boolean equals(UserDTO user) {
        try {
            if (getUser_id() != user.getUser_id()) {
                return false;
            } else if (getUsername() != user.getUsername()) {
                return false;
            } else if (isVerified() != user.isVerified()) {
                return false;
            } else if (!(getBotStatus().equals(user.getBotStatus()))) {
                return false;
            } else if (getFriendCount() != user.getFriendCount()) {
                return false;
            } else if (getFollowerCount() != user.getFollowerCount()) {
                return false;
            } else if (!(getName().equals(user.getName()))) {
                return false;
            } else if (!(getDescription().equals(user.getDescription()))) {
                return false;
            } else if (!(getUrlInDescription().equals(user.getUrlInDescription()))) {
                return false;
            } else if (!(getLanguage().equals(user.getLanguage()))) {
                return false;
            } else if (!(getTimeZone().equals(user.getTimeZone()))) {
                return false;
            } else if (getLocation().equals(user.getLocation())) {
                return false;
            } else if (isDefaultImage() != user.isDefaultImage()) {
                return false;
            } else if (getStatusCount() != user.getStatusCount()) {
                return false;
            } else if (getFavouriteCount() != user.getFavouriteCount()) {
                return false;
            }
        }
        catch (NullPointerException e) {}

        return true;
    }
}

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
* Created by Jessica on 05/02/2015.
*/
public class RetrieveData {
    CreateConnection createConnection = new CreateConnection();
    Statement statement;

    public RetrieveData() {
        createConnection.initialiseConnection();
        statement = createConnection.getStatement();
    }

    public ArrayList<Long> getBots() {
        ArrayList<Long> bots = Lists.newArrayList();

        String query = "SELECT * FROM user WHERE is_bot = '" + BotStatus.TRUE + "'";
        ResultSet resultSet;

        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next() == true) {
                while (resultSet.next()) {
                    bots.add(resultSet.getLong("user_id"));
                }
                return bots;
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't execute query: " + e);
        }
        finally {
            createConnection.close();
        }

        return null;
    }

    public ArrayList<Long> getNonBots() {
        ArrayList<Long> nonbots = Lists.newArrayList();

        String query = "SELECT * FROM user WHERE is_bot = '" + BotStatus.FALSE + "'";
        ResultSet resultSet;

        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                nonbots.add(resultSet.getLong("user_id"));
            }
            return nonbots;
        }
        catch (SQLException e) {
            System.out.println("Couldn't execute query: " + e);
        }
        finally {
            createConnection.close();
        }

        return null;
    }

    public ArrayList<String> getTweets(Long userId) {
        String sql = "SELECT * FROM tweet WHERE tweeters_id = ?";
        ResultSet resultSet;
        ArrayList<String> statuses = Lists.newArrayList();

        try {
            PreparedStatement preparedStatement = createConnection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                statuses.add(resultSet.getString("status"));
            }
            return statuses;
        }
        catch (SQLException e) {
            System.out.println("Couldn't execute query: " + e);
        }
        finally {
            createConnection.close();
        }

        return null;
    }

    public String getUserScreenName(long userId) {
        String sql = "SELECT username FROM user WHERE user_id = ?";
        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = createConnection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("status");
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't execute query: " + e);
        }
        finally {
            createConnection.close();
        }

        return null;
    }

    public Long getUserId(String screenname) {
        String sql = "SELECT user_id FROM user WHERE screen_name = ?";
        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = createConnection.prepStatement(sql);
            preparedStatement.setString(1, screenname);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getLong("user_id");
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't execute query: " + e);
        }
        finally {
            createConnection.close();
        }

        return null;
    }

    public ArrayList<Long> getFriendsAndFollowers(Long userID) {
        String sql = "SELECT friend_count, follower_count FROM user WHERE user_id = ?";
        ResultSet resultSet;

        ArrayList<Long> results = Lists.newArrayList();

        try {
            PreparedStatement preparedStatement = createConnection.prepStatement(sql);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                results.add(resultSet.getLong("follower_count"));
                results.add(resultSet.getLong("friend_count"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            createConnection.close();
        }

        return results;
    }

    protected boolean checkForUser(long userId)  {
        boolean exists = false;
        ResultSet resultSet;
        String sql = "SELECT COUNT(1) FROM user WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = createConnection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("COUNT(1)") == 1) {
                    exists = true;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            createConnection.close();
        }

        return exists;

    }

    protected boolean checkForTweet(long tweetId)  {
        boolean exists = false;
        ResultSet resultSet;
        String sql = "SELECT COUNT(1) FROM tweet WHERE tweet_id = ?";

        try {
            PreparedStatement preparedStatement = createConnection.prepStatement(sql);
            preparedStatement.setLong(1, tweetId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("COUNT(1)") == 1) {
                    exists = true;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            createConnection.close();
        }

        return exists;

    }

    protected Integer queryForCount(String query) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt("COUNT(1)");
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't execute query " + e);
        }
//        finally {
//            createConnection.close();
//        }

        return null;
    }

    protected Integer queryForCountTemp(String query) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt("COUNT(hashtag)");
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't execute query " + e);
        }
        //        finally {
        //            createConnection.close();
        //        }

        return null;
    }

    public UserDTO getUser(long userId) {
        if (!checkForUser(userId)) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        String sql = "SELECT * FROM user WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = createConnection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userDTO.setUser_id(userId);
                userDTO.setUsername(resultSet.getString("screen_name"));
                userDTO.setVerified(resultSet.getBoolean("is_verified"));
                userDTO.setBotStatus(BotStatus.valueOf(resultSet.getString("is_bot")));
                userDTO.setFriendCount(resultSet.getLong("friend_count"));
                userDTO.setFollowerCount(resultSet.getLong("follower_count"));
                userDTO.setName(resultSet.getString("name"));
                userDTO.setDescription(resultSet.getString("description"));
                userDTO.setUrlInDescription(resultSet.getString("url_in_description"));
                userDTO.setLanguage(resultSet.getString("lang"));
                userDTO.setTimeZone(resultSet.getString("time_zone"));
                userDTO.setLocation(resultSet.getString("location"));
                userDTO.setDefaultImage(resultSet.getBoolean("default_img"));
                userDTO.setStatusCount(resultSet.getLong("status_count"));
                userDTO.setFavouriteCount(resultSet.getLong("favourite_count"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            createConnection.close();
        }

        return userDTO;
    }

    public void checkForUser(String username) {
        String sql = "SELECT COUNT(1) FROM user WHERE screen_name = '" + username + "'";

        if (queryForCount(sql) == 0) {
            System.out.println(username);
        }
    }

    public static void main(String[] args) {
        //read in humansTest.txt
        RetrieveData retrieveData = new RetrieveData();
        Utilities utilities = new Utilities();
        ArrayList<String> bots = utilities.fileToArray("botsTest.txt");

        for (String bot : bots) {
            retrieveData.checkForUser(bot);
        }
    }
}

//import java.sql.*;
//
///**
// * Created by Jessica on 18/11/2014.
// */
//public class InsertIntoTables {
//    Connection connection = null;
//    Statement statement;
//
//    public void initialiseConnection() {
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/testtweets?", "root", "");
//            statement = connection.createStatement();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean checkForUser(long userid)  {
//        boolean exists = false;
//        ResultSet resultSet = null;
//        try {
//            System.out.println(statement);
//            resultSet = statement.executeQuery("SELECT COUNT(1) FROM user WHERE user_id = " + userid);
//            //perhaps add the other logic in the second try/catch in this, and return if null, instead of the space between the two try/catch.
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (resultSet != null) {
//            try {
//                while (resultSet.next()) {
//                    if (resultSet.getInt("COUNT(1)") == 1) {
//                        exists = true;
//                    }
//                }
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return exists;
//
//    }
//
//    private void insertIntoUserTable(User user) {
//        if (checkForUser(user.getUser_id()) == false) {
//            String sql = "INSERT INTO user (username, is_bot, user_id) VALUES (" + "'" + user.getUsername() + "'" + ',' + user.isBot() + ',' + user.getUser_id() + ')';
//            try {
//                statement.executeUpdate(sql);
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    protected void insertIntoTweetTable(Tweet singleTweet) {
//        String status = singleTweet.getStatus();
//        long tweetid = singleTweet.getTweet_id();
//        long userid = singleTweet.getTweeters_id();
//        String url = singleTweet.getUrl();
//
//        if (checkForUser(userid) == false) {
//            User user = singleTweet.getUser();
//            insertIntoUserTable(user);
//        }
//
//        String quotemarks = "\"";
//        if (status.contains(quotemarks)) {
//            status = status.replace(quotemarks, "'");
//        }
//        if (status.contains("\n")) {
//            status = status.replace("\n", " ");
//        }
//
//        String sql = "INSERT INTO tweet (tweet_id, status, tweeters_id, url) VALUES (" + tweetid + ',' + "\"" + status + "\"" + ',' + userid + ',' + "\"" + url + "\"" + ')';
//
//        try {
//            statement.executeUpdate(sql);
//        }
//        catch (SQLException e) {
//            System.out.println("-----------FAILED TO INSERT-------------");
//            System.out.println(sql);
//
//            e.printStackTrace();
//        }
//    }
//}

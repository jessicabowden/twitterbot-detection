import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
 * Created by Jessica on 23/03/2015.
 */
public class Testing {
    CreateConnection connection = new CreateConnection();

    public ArrayList<Long> gatherBots() {
        String sql = "select user_id from user where is_bot = 'TRUE'";

        ArrayList<Long> bots = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bots.add(resultSet.getLong("user_id"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return bots;
    }

    public ArrayList<Long> gatherHumans() {
        String sql = "select user_id from user where is_bot = 'FALSE'";

        ArrayList<Long> humans = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                humans.add(resultSet.getLong("user_id"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return humans;
    }

    public ArrayList<Long> gatherFirstHalfBots() {
        String sql = "select user_id from user where is_bot = 'TRUE' LIMIT 200";

        ArrayList<Long> bots = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bots.add(resultSet.getLong("user_id"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return bots;
    }

    public ArrayList<Long> gatherFirstHalfHumans() {
        String sql = "select user_id from user where is_bot = 'FALSE' LIMIT 238";

        ArrayList<Long> humans = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                humans.add(resultSet.getLong("user_id"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return humans;
    }

    public ArrayList<Long> gatherBotsTesting() {
        String sql = "select user_id from user where is_bot = 'TRUE' LIMIT 300 OFFSET 201";

        ArrayList<Long> bots = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bots.add(resultSet.getLong("user_id"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return bots;
    }

    public ArrayList<Long> gatherHumansTesting() {
        String sql = "select user_id from user where is_bot = 'FALSE' LIMIT 300 OFFSET 239";

        ArrayList<Long> humans = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                humans.add(resultSet.getLong("user_id"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return humans;
    }

    public void duplicateTweets() {
        AnalyzeRepetition analyzeRepetition = new AnalyzeRepetition();
        RetrieveData retrieveData = new RetrieveData();

        ArrayList<Long> bots = gatherBots();
        ArrayList<Long> humans = gatherHumans();

        System.out.println("bots");
        for (Long bot : bots) {
            Integer totalTweets = retrieveData.queryForCount("SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(bot));
            Integer repeats = analyzeRepetition.numberOfDuplicateTweets(new Long(bot));
            System.out.println("=" + repeats + "/" + totalTweets);
        }

        System.out.println("humans");
        for (Long human : humans) {
            Integer totalTweets = retrieveData.queryForCount("SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(human));
            Integer repeats = analyzeRepetition.numberOfDuplicateTweets(new Long(human));
            System.out.println("=" + repeats + "/" + totalTweets);
        }

    }

    public void duplicateHashtags() {
        AnalyzeRepetition analyzeRepetition = new AnalyzeRepetition();
        RetrieveData retrieveData = new RetrieveData();

        ArrayList<Long> bots = gatherBots();
        ArrayList<Long> humans = gatherHumans();

        System.out.println("bots");
        for (Long bot : bots) {
            Integer totalHashtags = retrieveData.queryForCountTemp(
                    "SELECT COUNT(hashtag) FROM tweet, hashtag, user WHERE tweet.tweet_id = hashtag.tweet_id AND tweet.tweeters_id = user.user_id AND tweet.tweeters_id = "
                            + bot);
            Integer repeats = analyzeRepetition.numberOfDuplicateHashtags(new Long(bot));
            System.out.println("=" + repeats + "/" + totalHashtags);
        }

        System.out.println("humans");
        for (Long human : humans) {
            Integer totalHashtags = retrieveData.queryForCountTemp(
                    "SELECT COUNT(hashtag) FROM tweet, hashtag, user WHERE tweet.tweet_id = hashtag.tweet_id AND tweet.tweeters_id = user.user_id AND tweet.tweeters_id = "
                            + human);
            Integer repeats = analyzeRepetition.numberOfDuplicateHashtags(new Long(human));
            System.out.println("=" + repeats + "/" + totalHashtags);
        }

    }

    public void numRTs() {
        TweetTypeCounter tweetTypeCounter = new TweetTypeCounter();
        RetrieveData retrieveData = new RetrieveData();

        ArrayList<Long> bots = gatherBots();
        ArrayList<Long> humans = gatherHumans();

        System.out.println("bots");
        for (Long bot : bots) {
            Integer totalTweets = retrieveData.queryForCount("SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(bot));
            Integer rts = tweetTypeCounter.numberOfRetweets(bot);
            System.out.println("=" + rts + "/" + totalTweets);
        }

        System.out.println("humans");
        for (Long human : humans) {
            Integer totalTweets = retrieveData.queryForCount(
                    "SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(human));
            Integer rts = tweetTypeCounter.numberOfRetweets(human);
            System.out.println("=" + rts + "/" + totalTweets);
        }

    }

    public void numHashtags() {
        TweetTypeCounter tweetTypeCounter = new TweetTypeCounter();
        RetrieveData retrieveData = new RetrieveData();

        ArrayList<Long> bots = gatherBots();
        ArrayList<Long> humans = gatherHumans();

        System.out.println("bots");
        for (Long bot : bots) {
            Integer totalTweets = retrieveData.queryForCount("SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(bot));
            Integer hashtags = tweetTypeCounter.numberOfHashtags(bot);
            System.out.println("=" + hashtags + "/" + totalTweets);
        }

        System.out.println("humans");
        for (Long human : humans) {
            Integer totalTweets = retrieveData.queryForCount(
                    "SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(human));
            Integer hashtags = tweetTypeCounter.numberOfHashtags(human);
            System.out.println("=" + hashtags + "/" + totalTweets);
        }

    }

    public void numOfLinks() {
        TweetTypeCounter tweetTypeCounter = new TweetTypeCounter();
        RetrieveData retrieveData = new RetrieveData();

        ArrayList<Long> bots = gatherBots();
        ArrayList<Long> humans = gatherHumans();

        System.out.println("bots");
        for (Long bot : bots) {
            Integer totalTweets = retrieveData.queryForCount("SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(bot));
            Integer hashtags = tweetTypeCounter.numberOfLinks(bot);
            System.out.println("=" + hashtags + "/" + totalTweets);
        }

        System.out.println("humans");
        for (Long human : humans) {
            Integer totalTweets = retrieveData.queryForCount(
                    "SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + new Long(human));
            Integer hashtags = tweetTypeCounter.numberOfLinks(human);
            System.out.println("=" + hashtags + "/" + totalTweets);
        }

    }

    public static void main(String[] args) {
        UrlAnalyzer urlAnalyzer = new UrlAnalyzer();
        MapUtils mapUtils = new MapUtils();

        Testing testing = new Testing();
        testing.numOfLinks();

//        AnalyzeRepetition analyzeRepetition = new AnalyzeRepetition();
//        RetrieveData retrieveData = new RetrieveData();
//
//        Integer totalTweets = retrieveData.queryForCount("SELECT COUNT(1) FROM tweet WHERE tweeters_id = " + 14721673);
//        Integer repeats = analyzeRepetition.numberOfDuplicateTweets(new Long(14721673));
//        System.out.println(totalTweets + "/" + repeats);

//        ArrayList<Long> bots = testing.gatherBots();
//        ArrayList<Long> humans = testing.gatherHumans();
//
//        HashMap<String, Integer> botSources = Maps.newHashMap();
//        HashMap<String, Integer> humanSources = Maps.newHashMap();
//
//        for (Long bot : bots) {
//            String current = urlAnalyzer.mostCommonSource(bot);
//
//            if (botSources.containsKey(current)) {
//                botSources.replace(current, botSources.get(current) + 1);
//            }
//            else {
//                botSources.put(current, 1);
//            }
//        }
//
//        for (Long human : humans) {
//            String current = urlAnalyzer.mostCommonSource(human);
//
//            if (humanSources.containsKey(current)) {
//                humanSources.replace(current, humanSources.get(current) + 1);
//            }
//            else {
//                humanSources.put(current, 1);
//            }
//        }
//
//        System.out.println(botSources);
//        System.out.println(humanSources);
    }
}

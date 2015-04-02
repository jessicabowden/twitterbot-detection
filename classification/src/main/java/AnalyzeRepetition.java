import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jessica on 21/03/2015.
 */
public class AnalyzeRepetition {
    CreateConnection connection = new CreateConnection();

    public Integer numberOfDuplicateHashtags(Long userId) {
        String sql = "SELECT SUM(cnt) AS total_duplicate_hashtags FROM (SELECT COUNT(hashtag) AS cnt, tweeters_id, hashtag "
                + "FROM tweet, hashtag, user WHERE tweet.tweet_id = hashtag.tweet_id AND tweet.tweeters_id = user.user_id "
                + "AND tweet.tweeters_id = ? "
                + "GROUP BY hashtag HAVING (cnt > 1)) AS x";
        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("total_duplicate_hashtags");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return 0;
    }

    public Integer numberOfDuplicateTweets(Long userId) {
        String sql = "SELECT SUM(cnt) AS total_duplicate_tweets FROM (SELECT COUNT(status) AS cnt, tweeters_id "
                + "FROM tweet, user WHERE tweet.tweeters_id = user.user_id AND tweet.tweeters_id = ? "
                + "GROUP BY status HAVING (cnt > 1)) AS x";

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("total_duplicate_tweets");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return 0;
    }
}

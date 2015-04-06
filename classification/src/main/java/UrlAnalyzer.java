import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
 * Created by Jessica on 21/03/2015.
 */
public class UrlAnalyzer {
    CreateConnection connection = new CreateConnection();

    //returns everything that is stored about a URL so that domain + expanded url can be extracted from this for use in other methods
    private ArrayList<String> listOfUrls(Long userId) {
        String sql = "SELECT url, expanded_url, domain, tweet_id FROM tweet, url, user WHERE  tweet.tweet_id = url.tweet_id AND "
                + "tweet.tweeters_id = ? ORDER BY tweet.tweeters_id";

        ArrayList<String> urls = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                urls.add(resultSet.getString("url"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return urls;
    }

    public String mostCommonDomain(Long userId) {
        String sql = "SELECT DISTINCT domain, COUNT(domain), tweeters_id "
                + "FROM tweet, url WHERE tweet.tweet_id = url.tweet_id AND tweet.tweeters_id = ? "
                + "GROUP BY domain ORDER BY COUNT(domain) DESC LIMIT 1";

        String domain = "";

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                domain = resultSet.getString("domain");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return domain;
    }
}

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

    public void mostCommonDomain(Long userId) {
        String sql = "SELECT DISTINCT domain, COUNT(domain), tweeters_id "
                + "FROM tweet, url WHERE tweet.tweet_id = url.tweet_id AND tweet.tweeters_id = ? "
                + "GROUP BY domain ORDER BY COUNT(domain) DESC LIMIT 1";

        ArrayList<String> domains = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                domains.add(resultSet.getString("domain"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }
    }

    //this doesn't really belong in url analyzer!
    public String mostCommonSource(Long userId) {
        String sql = "SELECT source, COUNT(source) FROM tweet WHERE tweeters_id = ? GROUP BY source ORDER BY COUNT(source) DESC LIMIT 1";

        String source = "";
        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                source = resultSet.getString("source");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return source;
    }
}

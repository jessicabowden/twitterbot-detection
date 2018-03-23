import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jessica on 02/04/2015.
 */
public class Source {
    CreateConnection connection = new CreateConnection();

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

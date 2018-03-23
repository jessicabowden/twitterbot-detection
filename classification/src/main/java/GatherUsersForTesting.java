import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
 * Created by Jessica on 23/03/2015.
 */
public class GatherUsersForTesting {
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
}

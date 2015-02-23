import java.sql.*;

/**
 * Created by Jessica on 05/02/2015.
 */
public class CreateConnection {
    Connection connection = null;
    Statement statement;

    public void initialiseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/tweets?", "root", "");
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public PreparedStatement prepStatement(String sql) {
        initialiseConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preparedStatement;
    }

    public void close() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
        }
    }
}

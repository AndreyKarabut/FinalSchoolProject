package serviceSQL;

import user.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;

public class SelectRows {
    public static float select(User user, Connection connection, String tableName, String fields) throws SQLException {
        String request = format("SELECT " + fields + " FROM " + tableName + " WHERE user_id = %d;",
                user.getId());
        System.out.println(request);

        return getStatements(connection, request);
    }

    public static float selectCallPutMoney(User user, Connection connection, float money) throws SQLException {
        String request = "select putmoney(" + user.getId() + ", " + money +") as balance;";
        return getStatements(connection, request);
    }
    public static float selectCallTakeMoney(User user, Connection connection, float money) throws SQLException {
        String request = "select takemoney(" + user.getId() + ", " + money +") as balance";
        return getStatements(connection, request);
    }
    public static ResultSet selectWithOperationList(String userId, Connection connection, String date1, String date2) throws SQLException {
        String s1 = (date1 == null)? "": "and date_time > timestamp '" + date1 + "'";
        String s2 = (date2 == null)? "": "and date_time < timestamp '" + date2 + "'";
        String request = "SELECT * FROM operationlist WHERE user_id = " + userId + s1 + s2 + ";";
        Statement statement = connection.createStatement();
        try {
            return statement.executeQuery(request);
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }
    }

    private static float getStatements(Connection connection, String request) throws SQLException{
        Statement statement = connection.createStatement();
        try {
            ResultSet resultSet = statement.executeQuery(request);
            float balance = 0;
            while (resultSet.next()){
                balance = resultSet.getFloat("balance");
            }
            return balance;
        } catch (SQLException e){
            System.out.println(e);
            return 0.0f;
        }

    }


}

package serviceSQL;

import user.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;

public class InsertRows {
    public static void insert(User user, Connection connection, String tableName) throws SQLException {
        String string = format("INSERT INTO " + tableName + " (user_id, balance) " + "VALUES (%d,%f);",
                user.getId(),
                user.getBalance());
        System.out.println(string);

        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(string);
        System.out.println("добавили " + rows + " строк");
    }
}

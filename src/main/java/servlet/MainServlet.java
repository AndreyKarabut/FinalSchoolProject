package servlet;

import answer.ErrAnswer;
import com.google.gson.Gson;
import serviceSQL.ConnectionSQL;
import serviceSQL.SelectRows;
import user.User;
import service.ReadProperty;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MainServlet {

    public static String getBalance(String userid) throws SQLException, ClassNotFoundException {

        Gson gson = new Gson();
        User user = new User(Integer.parseInt(userid));

        Connection connection = takeConnection();
        if (connection == null) {
            String description = "ОШИБКА: Невозможно подключиться к БД!";
            ErrAnswer errAnswer = new ErrAnswer(-1, description);
            return gson.toJson(errAnswer);
        } else {

            float balance = SelectRows.select(user, connection, "balance", "balance");

            user.setBalance(balance);

            return gson.toJson(user);

        }
    }

    public static String putMoney(String userId, String money) throws ClassNotFoundException, SQLException {
        User user;
        ErrAnswer errAnswer;
        Gson gson = new Gson();
        float fmoney;
        Connection connection = takeConnection();
        try {
            int id = Integer.parseInt(userId);
            user = new User(id);
        } catch (NumberFormatException E)
        {
            String description = "ОШИБКА: Неверный формат id пользователя";
            errAnswer = new ErrAnswer(-1, description);
            return gson.toJson(errAnswer);
        }
        try {
            fmoney = Float.parseFloat(money);
        } catch (NumberFormatException E){
            String description = "ОШИБКА: Неверный формат ввода денег";
            errAnswer = new ErrAnswer(-2, description);
            return gson.toJson(errAnswer);
        }

        float balancechanged = SelectRows.selectCallPutMoney(user, connection, fmoney);
        user.setBalance(balancechanged);
        return gson.toJson(user);

    }

    public static String takeMoney(String userId, String money) throws ClassNotFoundException, SQLException {
        User user;
        ErrAnswer errAnswer;
        Gson gson = new Gson();
        float fmoney;
        Connection connection = takeConnection();
        try {
            int id = Integer.parseInt(userId);
            user = new User(id);
        } catch (NumberFormatException E)
        {
            String description = "ОШИБКА: Неверный формат id пользователя";
            errAnswer = new ErrAnswer(-1, description);
            return gson.toJson(errAnswer);
        }

        try {
            fmoney = Float.parseFloat(money);
        } catch (NumberFormatException E){
            String description = "ОШИБКА: Неверный формат ввода денег";
            errAnswer = new ErrAnswer(-2, description);
            return gson.toJson(errAnswer);
        }

        float balance = SelectRows.select(user, connection, "balance", "balance");
        if (Float.isNaN(balance)) {
            String description = "ОШИБКА: Пользователя нет в БД!";
            errAnswer = new ErrAnswer(-1, description);
            return gson.toJson(errAnswer);
        }
        if (fmoney > balance){
            String description = "ОШИБКА: Недостаточно средств!";
            errAnswer = new ErrAnswer(0, description);
            return gson.toJson(errAnswer);
        } else {
            float balancechanged = SelectRows.selectCallTakeMoney(user, connection, fmoney);
            user.setBalance(balancechanged);
            return gson.toJson(user);
        }

    }
    private static Connection takeConnection() throws ClassNotFoundException {
            Properties property = ReadProperty.read("src/main/resources/application.properties");
            if (property == null) {
                return (null);
            } else{
                String host = property.getProperty("dbhost");
                String database = property.getProperty("database");
                String dbuser = property.getProperty("dbuser");
                String dbpass = property.getProperty("dbpass");
                return ConnectionSQL.getConnection(host, database, dbuser, dbpass, false);
            }

    }

}

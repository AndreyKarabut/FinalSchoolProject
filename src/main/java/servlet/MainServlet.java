package servlet;

import answer.ErrAnswer;
import answer.OperationList;
import com.google.gson.Gson;
import serviceSQL.ConnectionSQL;
import serviceSQL.SelectRows;
import user.User;
import service.ReadProperty;


import java.sql.*;
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

    public static String getOperationList(String userId, String date1, String date2) throws ClassNotFoundException, SQLException {
        String dateforreqest1;
        String dateforreqest2;
        if(date1.length() == 14) {
            dateforreqest1 = splitLineIntoPairs(date1);
        } else {
            dateforreqest1 = null;
        }
        if(date2.length() == 14) {
            dateforreqest2 = splitLineIntoPairs(date2);
        } else {
            dateforreqest2 = null;
        }
        Connection connection = takeConnection();
        ResultSet setAnswer = SelectRows.selectWithOperationList(userId, connection, dateforreqest1, dateforreqest2);
        StringBuilder answer = new StringBuilder("");
        Gson gson = new Gson();
        while (setAnswer.next()) {
            int operation_id = setAnswer.getInt("operation_id");
            int user_id  = setAnswer.getInt("user_id");
            int operation_type = setAnswer.getInt("operation_type");
            float summary = setAnswer.getFloat("summary");
            Timestamp date = setAnswer.getTimestamp("date_time");
            OperationList operationList = new OperationList(operation_id, user_id, operation_type, summary, date);
            answer.append(gson.toJson(operationList));
        }
        if (answer.toString() == ""){
            String description = "Нет данных по выбранным параметрам";
            ErrAnswer errAnswer = new ErrAnswer(5, description);
            return gson.toJson(errAnswer);
        } else{
            return answer.toString();
        }

    }
    private static String splitLineIntoPairs(String s){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 14 ; i++){
            stringBuilder.append(s.charAt(i));
            if (i == 3 || i == 5){
                stringBuilder.append("-");
            } else if(i == 7){
                stringBuilder.append(" ");
            } else if(i == 9 || i == 11){
                stringBuilder.append(":");
            }
        }
        return stringBuilder.toString();
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

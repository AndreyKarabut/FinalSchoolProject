package serviceSQL;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionSQL {

    private static String conok = "Ok";
    private static String conerr = "Err";
    private static Connection sharedConnection;

    public static Connection getConnection(String host ,String database, String user, String password, boolean forceNew) throws ClassNotFoundException {
        String URL = "jdbc:postgresql://" + host + "/" + database + "?user=" + user + "&password=" + password;
        Connection connect = sharedConnection;
        try {
            Class.forName("org.postgresql.Driver");
            if (forceNew || connect == null) {
                connect = DriverManager.getConnection(URL);
                if (sharedConnection == null) {
                    sharedConnection = connect;
                }
            }
            System.out.println(conok);
            return connect;
        } catch (Exception e) {
            System.out.println(conerr);
            return null;
        }
    }
}

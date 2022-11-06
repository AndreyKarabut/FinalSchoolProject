package serviceSQL;

import user.User;

import static java.lang.String.format;

public class CallingProcedure {

    public void callPutMoney(User user, float money){
        String string = format("call putmoney(%d, %f);", user.getId(), money);
    }
    public static void callTakeMoney(User user, float money){
        String string = format("SELECT takemoney(%d, %f);", user.getId(), money);
    }
}

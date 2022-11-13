
import servlet.MainServlet;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args){
        port(8080);
        get("/getBalance/:userId",(request, response)-> MainServlet.getBalance(request.params("userId")));
        get("/putMoney/:userId&money", (request, response) -> {
            Map<String, String> params = parseParams(request);
            return MainServlet.putMoney(params.get("userId"), params.get("money"));
        });
        get("/takeMoney/:userId&money", (request, response) -> {
            Map<String, String> params = parseParams(request);
            return MainServlet.takeMoney(params.get("userId"), params.get("money"));
        });
        get("/getOperationList/:userId&date1&date2", (request, response) -> {
            Map<String, String> params = parseParams(request);
            return MainServlet.getOperationList(params.get("userId"), params.get("date1"), params.get("date2")); });
    }

    static Map<String, String> parseParams(spark.Request req) {
        Map<String, String> params = new HashMap();
        String value = req.params("userId&money");
        if (value == null){
            value = req.params("userId&date1&date2");
        }
        String[] strParams = value.split("&");
        if (strParams.length == 2) {
            params.put("userId", strParams[0]);
            params.put("money", strParams[1]);
        } else if(strParams.length == 3){
            params.put("userId", strParams[0]);
            params.put("date1", strParams[1]);
            params.put("date2", strParams[2]);
        }
        return params;
    }

}

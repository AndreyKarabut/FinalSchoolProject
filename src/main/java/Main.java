
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
            return MainServlet.putMoney(params.get("p1"), params.get("p2"));
        });
        get("/takeMoney/:userId&money", (request, response) -> {
            Map<String, String> params = parseParams(request);
            return MainServlet.takeMoney(params.get("p1"), params.get("p2"));
        });
        get("/getOperationList/:p1&p2&p3", (request, response) -> {
            Map<String, String> params = parseParams(request);
            return MainServlet.getOperationList(params.get("p1"), params.get("p2"), params.get("p3")); });
        get("/transfermoney/:p1&p2&p3",(request, response) -> {
            Map<String, String> params = parseParams(request);
            return MainServlet.transferMoney(params.get("p1"), params.get("p2"), params.get("p3"));
        });
    }

    static Map<String, String> parseParams(spark.Request req) {
        Map<String, String> params = new HashMap();
        String value = req.params("userId&money");
        if (value == null){
            value = req.params("p1&p2&p3");
        }
        String[] strParams = value.split("&");
        if (strParams.length == 2) {
            params.put("p1", strParams[0]);
            params.put("p2", strParams[1]);
        } else if(strParams.length == 3){
            params.put("p1", strParams[0]);
            params.put("p2", strParams[1]);
            params.put("p3", strParams[2]);
        }
        return params;
    }

}

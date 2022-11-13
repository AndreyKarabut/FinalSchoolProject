package answer;

import java.sql.Date;
import java.sql.Timestamp;

public class OperationList {
    int operation_id;
    int user_id;
    int operation_type;
    float summary;
    Timestamp date;

    public OperationList(int operation_id, int user_id, int operation_type, float summary, Timestamp date) {
        this.operation_id = operation_id;
        this.user_id = user_id;
        this.operation_type = operation_type;
        this.summary = summary;
        this.date = date;
    }
}

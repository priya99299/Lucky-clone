package firstapp.example.lipsclone.api.Models;

public class FeeTransactionRequest {
    private String action="api";
    private String page= "student_fee_transaction";
    private String sid;
    private String f_id;
    private String session;
    private String college;

    public FeeTransactionRequest( String action , String page ,String sid, String f_id, String session, String college) {
        this.action = action;
        this.page = page;
        this.sid = sid;
        this.f_id = f_id;
        this.session = session;
        this.college = college;
    }

    // Getters and setters (if needed)
}

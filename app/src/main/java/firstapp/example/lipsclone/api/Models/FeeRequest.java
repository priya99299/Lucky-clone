package firstapp.example.lipsclone.api.Models;

public class FeeRequest {
    private String action="api";
    private String page="student_fee";
    private String sid;
    private String f_id;
    private String session;
    private String college;

    public FeeRequest(String action , String page,String sid, String f_id, String session, String college) {
        this.action = action;
        this.page = page;
        this.sid = sid;
        this.f_id = f_id;
        this.session = session;
        this.college = college;
    }
}



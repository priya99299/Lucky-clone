package firstapp.example.lipsclone.api.Models.Time_table;



public class StudentTimeTableRequest {
    public String action = "api";
    public String page="student_time_table";
    public String sid;
    public String session;
    public String college;

    public StudentTimeTableRequest( String action,String page, String sid, String session, String college) {
        this.action=action;
        this.page = page;
        this.sid = sid;
        this.session = session;
        this.college = college;
    }
}


package firstapp.example.lipsclone.api.Models;

public class StudentTimeTableRequest {
    public String action = "api";
    public String college;
    public String page = "student_time_table";
    public String session;
    public String sid;

    public StudentTimeTableRequest(String sid, String session, String college) {
        this.sid = sid;
        this.session = session;
        this.college = college;
    }
}


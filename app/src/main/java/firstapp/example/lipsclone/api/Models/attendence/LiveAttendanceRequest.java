package firstapp.example.lipsclone.api.Models.attendence;

public class LiveAttendanceRequest {
    private String action = "api";
    private String page = "student_attendence_live";
    private String college;
    private String session;
    private String s_id;
    private String f_id;
    private String sem;

    public LiveAttendanceRequest() {
        // Empty constructor for manual setting
    }

    public LiveAttendanceRequest(String college, String session, String s_id, String f_id, String sem) {
        this.college = college;
        this.session = session;
        this.s_id = s_id;
        this.f_id = f_id;
        this.sem = sem;
    }

    // Getters
    public String getAction() { return action; }
    public String getPage() { return page; }
    public String getCollege() { return college; }
    public String getSession() { return session; }
    public String getS_id() { return s_id; }
    public String getF_id() { return f_id; }
    public String getSem() { return sem; }

    // Setters
    public void setAction(String action) { this.action = action; }
    public void setPage(String page) { this.page = page; }
    public void setCollege(String college) { this.college = college; }
    public void setSession(String session) { this.session = session; }
    public void setS_id(String s_id) { this.s_id = s_id; }
    public void setF_id(String f_id) { this.f_id = f_id; }
    public void setSem(String sem) { this.sem = sem; }
}


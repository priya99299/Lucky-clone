package firstapp.example.lipsclone.api.Models.attendence;



public class SaveAttendanceRequest {
    private String action;
    private String page;
    private String college;
    private String session;
    private String s_id;
    private String a_id;

    public SaveAttendanceRequest(String action, String page, String college, String session, String s_id, String a_id) {
        this.action = action;
        this.page = page;
        this.college = college;
        this.session = session;
        this.s_id = s_id;
        this.a_id = a_id;
    }

    // Getters if needed
}


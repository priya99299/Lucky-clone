package firstapp.example.lipsclone.api.Models.acadmic;

public class AcademicCalendarRequest {
    private String action="api";
    private String page="academic_calender";
    private String s_id;
    private String session;
    private String college;
    private String f_id;

    public AcademicCalendarRequest(String s_id, String session, String college, String f_id) {
        this.action = action;
        this.page = page;
        this.s_id = s_id;
        this.session = session;
        this.college = college;
        this.f_id = f_id;
    }

    // Getters and setters (if needed)
}

package firstapp.example.lipsclone.api.Models.attendence;


public class LectureAttendanceRequest {
    private String action;
    private String page;
    private String s_id;
    private String college;
    private String f_id;
    private String from;
    private String to;
    private String sem;

    public LectureAttendanceRequest(String action, String page, String s_id, String college, String f_id, String from, String to, String sem) {
        this.action = action;
        this.page = page;
        this.s_id = s_id;
        this.college = college;
        this.f_id = f_id;
        this.from = from;
        this.to = to;
        this.sem = sem;
    }

    @Override
    public String toString() {
        return "{" +
                "\"action\":\"" + action + '\"' +
                ", \"page\":\"" + page + '\"' +
                ", \"s_id\":\"" + s_id + '\"' +
                ", \"college\":\"" + college + '\"' +
                ", \"f_id\":\"" + f_id + '\"' +
                ", \"from\":\"" + from + '\"' +
                ", \"to\":\"" + to + '\"' +
                ", \"sem\":\"" + sem + '\"' +
                '}';
    }
}




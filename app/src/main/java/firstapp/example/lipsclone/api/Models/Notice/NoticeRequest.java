package firstapp.example.lipsclone.api.Models.Notice;


public class NoticeRequest {
    private String action="api";
    private String page="student_notice";
    private String sid;
    private String session;
    private String college;

        public NoticeRequest(String action, String page, String sid, String session, String college) {
        this.action = action;
        this.page = page;
        this.sid = sid;
        this.session = session;
        this.college = college;
    }

    // getters/setters if needed
}


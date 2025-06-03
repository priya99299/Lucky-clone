package firstapp.example.lipsclone.api.Models;


public class NoticeRequest {
    private String action;
    private String page;
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


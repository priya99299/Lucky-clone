package firstapp.example.lipsclone.api.Models.Messages;

public class Messages {
    private String action = "api";
    private String page = "msg_details";
    private String s_id;
    private String session;
    private String college;
    private String remark;
    private String type;

    public Messages(String s_id, String session, String college, String remark, String type) {
        this.action=action;
        this.page=page;
        this.s_id = s_id;
        this.session = session;
        this.college = college;
        this.remark = remark;
        this.type = type;
    }
}

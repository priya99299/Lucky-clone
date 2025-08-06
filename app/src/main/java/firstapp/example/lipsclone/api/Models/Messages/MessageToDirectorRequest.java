package firstapp.example.lipsclone.api.Models.Messages;

public class MessageToDirectorRequest {
    private String action="api";
    private String page="msg_director";
    private String s_id;
    private String session;
    private String college;
    private String remark;

    public MessageToDirectorRequest(String s_id, String session, String college, String remark) {
        this.action = "api";
        this.page = "msg_director";
        this.s_id = s_id;
        this.session = session;
        this.college = college;
        this.remark = remark;
    }

    // Getters (optional if needed)
    public String getAction() {
        return action;
    }

    public String getPage() {
        return page;
    }

    public String getS_id() {
        return s_id;
    }

    public String getSession() {
        return session;
    }

    public String getCollege() {
        return college;
    }

    public String getRemark() {
        return remark;
    }
}

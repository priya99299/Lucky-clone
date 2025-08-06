package firstapp.example.lipsclone.api.Models.Messages;


import com.google.gson.annotations.SerializedName;

public class DirectorMessageItem {
    private String c_id;
    private String cdate;
    private String ctime;
    private String msg;
    private String readstatus;
    private String revert;

    @SerializedName("remark")
    private String remark;

    @SerializedName("created_at")
    private String createdAt;

    public String getC_id() { return c_id; }
    public String getCdate() { return cdate; }
    public String getCtime() { return ctime; }
    public String getMsg() { return msg; }
    public String getReadstatus() { return readstatus; }
    public String getRevert() { return revert; }
    public String getRemark() {
        return remark;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

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

    // Getters
    public String getC_id() { return c_id; }
    public String getCdate() { return cdate; }
    public String getCtime() { return ctime; }
    public String getMsg() { return msg; }
    public String getReadstatus() { return readstatus; }
    public String getRevert() { return revert; }
    public String getRemark() { return remark; }
    public String getCreatedAt() { return createdAt; }

    // ✅ Setters (add these)
    public void setC_id(String c_id) { this.c_id = c_id; }
    public void setCdate(String cdate) { this.cdate = cdate; }
    public void setCtime(String ctime) { this.ctime = ctime; }
    public void setMsg(String msg) { this.msg = msg; }
    public void setReadstatus(String readstatus) { this.readstatus = readstatus; }
    public void setRevert(String revert) { this.revert = revert; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}

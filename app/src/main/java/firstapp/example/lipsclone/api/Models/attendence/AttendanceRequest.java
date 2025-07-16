package firstapp.example.lipsclone.api.Models.attendence;

import com.google.gson.annotations.SerializedName;

public class AttendanceRequest {

    @SerializedName("action")
    private String action = "api";

    @SerializedName("page")
    private String page = "student_attendence_present";

    @SerializedName("sem")
    private String sem = "";

    @SerializedName("s_id")
    private String sId;

    @SerializedName("session")
    private String session;

    @SerializedName("college")
    private String college;

    // ✅ Constructor
    public AttendanceRequest(String sId, String session, String college) {
        this.sId = sId;
        this.session = session;
        this.college = college;
    }

    // ✅ Optional Setter (if needed to override)
    public void setSem(String sem) {
        this.sem = sem;
    }

    // ✅ Optional Getters
    public String getAction() {
        return action;
    }

    public String getPage() {
        return page;
    }

    public String getSem() {
        return sem;
    }

    public String getSId() {
        return sId;
    }

    public String getSession() {
        return session;
    }

    public String getCollege() {
        return college;
    }
}

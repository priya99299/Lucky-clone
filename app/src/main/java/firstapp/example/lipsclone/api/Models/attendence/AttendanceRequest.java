package firstapp.example.lipsclone.api.Models.attendence;




import com.google.gson.annotations.SerializedName;

public class AttendanceRequest {
    @SerializedName("action")
    private final String action = "api";

    @SerializedName("page")
    private final String page = "student_attendence_present";

    @SerializedName("f_id")
    private String fId;

    @SerializedName("sem")
    private String sem = "";

    @SerializedName("s_id")
    private String sId;

    @SerializedName("session")
    private String session;

    @SerializedName("college")
    private String college;

    public AttendanceRequest(String sId, String session, String college, String fId) {
        this.sId = sId;
        this.session = session;
        this.college = college;
        this.fId = fId;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }
}

package firstapp.example.lipsclone.api.Models;


import com.google.gson.annotations.SerializedName;

public class StudentDocumentRequest {
    @SerializedName("action")
    private final String action = "api";

    @SerializedName("page")
    private final String page = "student_document";

    @SerializedName("s_id")
    private String s_id;

    @SerializedName("session")
    private String session;

    @SerializedName("college")
    private String college;

    public StudentDocumentRequest(String s_id, String session, String college) {
        this.s_id = s_id;
        this.session = session;
        this.college = college;
    }

}

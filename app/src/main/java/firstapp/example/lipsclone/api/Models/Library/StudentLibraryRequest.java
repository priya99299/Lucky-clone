package firstapp.example.lipsclone.api.Models.Library;

import com.google.gson.annotations.SerializedName;

public class StudentLibraryRequest {
    @SerializedName("action")
    String action;

    @SerializedName("page")
    String page;

    @SerializedName("s_id")
    String s_id;

    @SerializedName("session")
    String session;

    @SerializedName("college")
    String college;

    public StudentLibraryRequest(String action, String page, String s_id, String session, String college) {
        this.action = action;
        this.page = page;
        this.s_id = s_id;
        this.session = session;
        this.college = college;
    }
}

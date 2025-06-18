package firstapp.example.lipsclone.api.Models;


import com.google.gson.annotations.SerializedName;

public class StudentDocumentRequest {
    @SerializedName("action")
    private final String action = "api";

    @SerializedName("page")
    private final String page = "student_document";

    @SerializedName("s_id")
    private String s_id;
    @SerializedName("docname")
    private String docname;

    @SerializedName("file")
    private String file;


    @SerializedName("session")
    private String session;

    @SerializedName("college")
    private String college;

    public StudentDocumentRequest(String s_id, String file, String docname) {
        this.s_id = s_id;
        this.session = session;
        this.docname=docname;
        this.file=file;
    }

}

package firstapp.example.lipsclone.api.Models.Documents;


public class StudentDocument {
    private String action = "api";
    private String page= "student_document";
    private String s_id;
    private String session;
    private String college;

    public StudentDocument(String action, String page, String s_id, String session, String college) {
        this.action = action;
        this.page = page;
        this.s_id = s_id;
        this.session = session;
        this.college = college;
    }
}


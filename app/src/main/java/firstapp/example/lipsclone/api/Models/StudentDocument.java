package firstapp.example.lipsclone.api.Models;



import com.google.gson.annotations.SerializedName;



public class StudentDocument {
    String action = "api";
    String page = "student_document";
    String session;
    String college;
    String mobile;

    public StudentDocument(String session, String college, String mobile) {
        this.session = session;
        this.college = college;
        this.mobile = mobile;
    }
}


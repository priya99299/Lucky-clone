package firstapp.example.lipsclone.api.Models.Lecture;

public class LectureRequest {
    String action = "api";
    String page = "student_lecture";
    String sid;
    String f_id;
    String session;
    String college;
    String sem;

    public LectureRequest(String sid, String f_id,String sem, String session, String college) {
        this.sid = sid;
        this.sem=sem;
        this.f_id = f_id;
        this.session = session;
        this.college = college;
    }
}

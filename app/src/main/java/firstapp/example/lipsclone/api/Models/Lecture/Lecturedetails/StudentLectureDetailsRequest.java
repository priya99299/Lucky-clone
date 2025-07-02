package firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails;

public class StudentLectureDetailsRequest {
    public String action = "api";
    public String page = "student_lecture_details";
    public String p_id;
    public String session;
    public String college;

    public StudentLectureDetailsRequest(String p_id) {
        this.p_id = p_id;
    }
}
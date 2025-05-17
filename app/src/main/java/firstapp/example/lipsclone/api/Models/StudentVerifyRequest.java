package firstapp.example.lipsclone.api.Models;

public class StudentVerifyRequest {
    String action = "api";
    String page = "student_varify";
    String session;
    String college;
    String mobile;
    String otp;

    public StudentVerifyRequest(String session, String college, String mobile, String otp) {
        this.session = session;
        this.college = college;
        this.mobile = mobile;
        this.otp = otp;
    }
}


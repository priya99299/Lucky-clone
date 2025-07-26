package firstapp.example.lipsclone.api.Models.attendence;

import java.util.List;

public class LectureAttendanceResponse {
    public boolean error;
    public String message;
    public List<AttendanceItem> response;

    public static class AttendanceItem {
        public String subject;
        public String total_lecture_held;
        public String total_lecture_attnd;
    }
}

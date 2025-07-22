package firstapp.example.lipsclone.api.Models.attendence;

public class LiveAttendanceResponse {
    private LiveAttendanceData response = new LiveAttendanceData(); // default response with status="1"

    public LiveAttendanceData getResponse() {
        return response;
    }

    public static class LiveAttendanceData {
        private String status = "0"; // ✅ HARDCODED here

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

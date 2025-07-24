package firstapp.example.lipsclone.api.Models.attendence;



public class LiveAttendanceResponse {
    private boolean success;
    private boolean error;
    private ResponseData response;

    public boolean isSuccess() { return success; }
    public boolean isError() { return error; }
    public ResponseData getResponse() { return response; }

    public class ResponseData {
        private String status;
        private String a_id;
        private String mark_status;
        private String mark_date;
        private String msg;
        private double time_left;

        public String getStatus() { return status; }
        public String getA_id() { return a_id; }
        public String getMark_status() { return mark_status; }
        public String getMark_date() { return mark_date; }
        public String getMsg() { return msg; }
        public double getTime_left() { return time_left; }
    }
}

package firstapp.example.lipsclone.api.Models;

public class AppConfigResponse {
    public boolean success;
    public boolean error;
    public String name;
    public String class_name;

    public ResponseData response;


    public class ResponseData {
        public String otp;
        public String msg;
        public String session;
        public String college;
        public String name;
        public String class_name;
        public String pic;
        public String admno;
        public String fname;
        public String  mname;
        public String mobile1;
        public String   address2;
    }
}

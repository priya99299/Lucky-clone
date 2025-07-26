package firstapp.example.lipsclone.api.Models;

import java.util.List;

public class AppConfigResponse {
    public boolean success;
    public boolean error;
    public String name;
    public String class_name;

    public ResponseData response;
    public String file;
    public String docname;


    public class ResponseData {
        public String otp;
        public String msg;
        public String session;
        public String f_id;
        public String college;
        public String s_id;
        public String name;
        public String  sem;
        public String class_name;
        public String pic;
        public String admno;
        public String fname;
        public String  mname;
        public String mobile1;
        public String   address2;
        public String file;
        public String docname;

    }
}

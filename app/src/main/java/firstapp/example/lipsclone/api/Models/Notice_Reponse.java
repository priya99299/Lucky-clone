package firstapp.example.lipsclone.api.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notice_Reponse {

        public boolean success;
        public boolean error;
        public List<Notice> response;

        public static class Notice {
            public String noticeId;
            public String noticeNo;
            public String noticeDate;
            public String title;
            public String description;
            public String category;
            public String noticeBy;
            public String issuedBy;
            public String emp;
            public String header;
            public String read;
        }
    }



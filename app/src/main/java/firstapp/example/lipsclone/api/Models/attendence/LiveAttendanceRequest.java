    package firstapp.example.lipsclone.api.Models.attendence;


    public class LiveAttendanceRequest {
        private String action;
        private String page;
        private String college;
        private String session;
        private String s_id;
        private String f_id;
        private String sem;

        public LiveAttendanceRequest(String action, String page, String college, String session, String s_id, String f_id) {
            this.action = action;
            this.page = page;
            this.college = college;
            this.session = session;
            this.s_id = s_id;
            this.f_id = f_id;
            this.sem = sem;
        }

        // Getters
        public String getAction() { return action; }
        public String getPage() { return page; }
        public String getCollege() { return college; }
        public String getSession() { return session; }
        public String getS_id() { return s_id; }
        public String getF_id() { return f_id; }
        public String getSem() { return sem; }
    }


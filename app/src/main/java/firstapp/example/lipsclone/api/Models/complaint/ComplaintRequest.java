package firstapp.example.lipsclone.api.Models.complaint;



public class ComplaintRequest {
    private String action="api";
    private String page="complain_list_bystudent";
    private String s_id;
    private String session;
    private String college;
    private String sem;
    private String f_id;
    private String cname;
    private String csubject;
    private String cdescription;

    public ComplaintRequest(String s_id, String session, String college,
                            String sem, String f_id, String cname,
                            String csubject, String cdescription) {
        this.action=action;
        this.s_id = s_id;
        this.session = session;
        this.college = college;
        this.sem = sem;
        this.f_id = f_id;
        this.cname = cname;
        this.csubject = csubject;
        this.cdescription = cdescription;
    }

    // Optional: Constructor for fetching complaints
    public ComplaintRequest(String s_id, String session, String college) {
        this.action=action;
        this.s_id = s_id;
        this.session = session;
        this.college = college;
    }

    // Getters (if needed for serialization)
    public String getS_id() { return s_id; }
    public String getSession() { return session; }
    public String getCollege() { return college; }
    public String getSem() { return sem; }
    public String getF_id() { return f_id; }
    public String getCname() { return cname; }
    public String getCsubject() { return csubject; }
    public String getCdescription() { return cdescription; }
}


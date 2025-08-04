package firstapp.example.lipsclone.api.Models.complaint;

public class ComplaintSubmitRequest {
    private String action = "api";
    private String page = "complain_submit";
    private String s_id;
    private String session;
    private String college;
    private String title;
    private String desp;
    private String img;

    public ComplaintSubmitRequest(String s_id, String session, String college,
                                  String title, String desp, String img) {
        this.s_id = s_id;
        this.session = session;
        this.college = college;
        this.title = title;
        this.desp = desp;
        this.img = img;
    }
}


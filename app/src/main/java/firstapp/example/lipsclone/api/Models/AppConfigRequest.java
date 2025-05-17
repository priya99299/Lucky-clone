package firstapp.example.lipsclone.api.Models;

public class AppConfigRequest {
    String action = "api";
    String page = "app_config";
    String s_id;
    String session;
    String college;
    String f_id;

    public AppConfigRequest(String s_id, String session, String college, String f_id) {
        this.s_id = s_id;
        this.session = session;
        this.college = college;
        this.f_id = f_id;
    }
}

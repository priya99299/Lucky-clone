package firstapp.example.lipsclone.api;

public class LoginRequest {
    private String mobile;
    private String session;
    private String college;

    // Constructor
    public LoginRequest(String mobile, String session) {
        this.mobile = mobile;
        this.session = session;
        }

    // Getter and setter methods
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}



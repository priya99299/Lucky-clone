package firstapp.example.lipsclone.api.Login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("action")
    private final String action = "api";

    @SerializedName("page")
    private final String page = "login_student";

    @SerializedName("mobile")
    private String mobile;

    public LoginRequest(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
}

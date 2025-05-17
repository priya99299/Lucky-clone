package firstapp.example.lipsclone.api.Models;

public class AppConfigRequest {
    public boolean success;
    public ResponseData response;

    public static class ResponseData {
        public String otp;      // only if you also use this model to fetch OTP
        public String message;  // optional: e.g., "OTP verified"
    }
}

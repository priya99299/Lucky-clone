package firstapp.example.lipsclone.api.Models.complaint;

public class ComplaintSubmitResponse {
    private boolean success;
    private boolean error;
    private String message;

    public boolean isSuccess() { return success; }
    public boolean isError() { return error; }
    public String getMessage() { return message; }
}

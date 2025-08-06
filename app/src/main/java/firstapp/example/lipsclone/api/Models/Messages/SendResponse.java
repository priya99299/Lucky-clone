package firstapp.example.lipsclone.api.Models.Messages;

public class SendResponse {
    private boolean success;
    private boolean error;
    private String message;

    public boolean isSuccess() { return success; }
    public boolean isError() { return error; }
    public String getMessage() { return message; }
}

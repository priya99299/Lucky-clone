package firstapp.example.lipsclone.api.Models.Messages;

import java.util.List;

public class MessageResponse {
    private boolean success;
    private boolean error;
    private List<DirectorMessageItem> response;

    public List<DirectorMessageItem> getResponse() {
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }
}

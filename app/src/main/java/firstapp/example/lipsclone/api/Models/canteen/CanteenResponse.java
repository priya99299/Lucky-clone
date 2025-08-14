package firstapp.example.lipsclone.api.Models.canteen;


import java.util.List;

public class CanteenResponse {
    private boolean success;
    private boolean error;
    private List<CanteenItem> response;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }

    public List<CanteenItem> getResponse() {
        return response;
    }
}

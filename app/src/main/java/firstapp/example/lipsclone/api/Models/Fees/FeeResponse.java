package firstapp.example.lipsclone.api.Models.Fees;

public class FeeResponse {
    private boolean success;
    private boolean error;
    private FeeData response;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }

    public FeeData getResponse() {
        return response;
    }
}


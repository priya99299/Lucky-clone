package firstapp.example.lipsclone.api.Models;

import java.util.List;

public class FeeTransactionResponse {
    private boolean success;
    private boolean error;
    private List<FeeTransactionItem> response;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }

    public List<FeeTransactionItem> getResponse() {
        return response;
    }
}

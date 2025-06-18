package firstapp.example.lipsclone.api.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private boolean error;

    @SerializedName("response")
    private List<DocumentModel> response;

    public boolean isSuccess() {
        return success;
    }

    public List<DocumentModel> getResponse() {
        return response;
    }
}


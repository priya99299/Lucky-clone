package firstapp.example.lipsclone.api.Models.Downloads;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StudentDocumentResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private boolean error;

    @SerializedName("response")
    private List<DocumentModel> response;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }

    public List<DocumentModel> getResponse() {
        return response;
    }
}

package firstapp.example.lipsclone.api.Models.complaint;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComplaintResponse {
    @SerializedName("response")
    private List<Complaint> response;

    public List<Complaint> getResponse() {
        return response;
    }
}


package firstapp.example.lipsclone.api.Models.complaint;

import java.util.List;

public class ComplaintCategoryResponse {
    private boolean success;
    private boolean error;
    private List<ComplaintCategory> response;

    public boolean isSuccess() { return success; }
    public boolean isError() { return error; }
    public List<ComplaintCategory> getResponse() { return response; }
}

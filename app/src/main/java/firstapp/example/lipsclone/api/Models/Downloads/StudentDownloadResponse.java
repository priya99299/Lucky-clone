package firstapp.example.lipsclone.api.Models.Downloads;


import java.util.List;

public class StudentDownloadResponse {
    private boolean success;
    private boolean error;
    private List<DownloadItem> response;

    public boolean isSuccess() { return success; }
    public boolean isError() { return error; }
    public List<DownloadItem> getResponse() { return response; }
}

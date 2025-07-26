package firstapp.example.lipsclone.api.Models.attendence;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class AttendanceResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private boolean error;

    @SerializedName("response")
    private List<AttendanceData> response;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }

    public List<AttendanceData> getResponse() {
        return response;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResponse(List<AttendanceData> response) {
        this.response = response;
    }
}

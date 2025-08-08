package firstapp.example.lipsclone.api.Models.acadmic;

import java.util.List;

import java.util.List;

public class AcademicCalendarResponse {
    private boolean success;
    private boolean error;
    private List<AcademicEvent> response;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }

    public List<AcademicEvent> getResponse() {
        return response;
    }
}

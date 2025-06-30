package firstapp.example.lipsclone.api.Models.Time_table;

import com.google.gson.annotations.SerializedName;

public class PeriodByDay {
    @SerializedName("Subject")
    public String Subject;

    @SerializedName("Faculty_name")
    public String Faculty;

    @SerializedName("Location")
    public String Room;

    @SerializedName("Time")
    public String Time;
    public String getSubject() { return Subject; }
    public String getTime() { return Time; }
    public String getFacultyName() { return Faculty; }
    public String getLocation() { return Room; }
}



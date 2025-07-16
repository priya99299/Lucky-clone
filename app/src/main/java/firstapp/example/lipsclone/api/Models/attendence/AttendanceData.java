package firstapp.example.lipsclone.api.Models.attendence;

import com.google.gson.annotations.SerializedName;

public class AttendanceData {

    @SerializedName("Month")
    public String month;

    @SerializedName("Present")
    public String present;

    @SerializedName("Total_Class")
    public String totalClass;

    public AttendanceData() {
        // Default constructor for Gson
    }

    public AttendanceData(String month, String present, String totalClass) {
        this.month = month;
        this.present = present;
        this.totalClass = totalClass;
    }
}

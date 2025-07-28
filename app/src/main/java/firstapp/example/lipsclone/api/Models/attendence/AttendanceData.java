package firstapp.example.lipsclone.api.Models.attendence;

import com.google.gson.annotations.SerializedName;

public class AttendanceData {

    @SerializedName("Month")
    private String month;

    @SerializedName("Present")
    private String present;

    @SerializedName("Total_Class")
    private String totalClass;

    public AttendanceData(String month, String present, String totalClass) {
        this.month = month;
        this.present = present;
        this.totalClass = totalClass;
    }

    public String getMonth() {
        return month;
    }

    public String getPresent() {
        return present;
    }

    public String getTotalClass() {
        return totalClass;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public void setTotalClass(String totalClass) {
        this.totalClass = totalClass;
    }
}

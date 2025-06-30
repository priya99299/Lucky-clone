package firstapp.example.lipsclone.api.Models.Time_table;

import com.google.gson.annotations.SerializedName;

public class CourseDetails {
    @SerializedName("Course Name")
    public String courseName;

    @SerializedName("Semester")
    public String semester;

    @SerializedName("Created By")
    public String createdBy;

    @SerializedName("Total Periods")
    public String totalPeriods;
}

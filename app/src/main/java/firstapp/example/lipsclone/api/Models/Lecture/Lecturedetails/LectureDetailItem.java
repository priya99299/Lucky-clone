package firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails;

import com.google.gson.annotations.SerializedName;

public class LectureDetailItem {

    @SerializedName("mainTopic")
    private String mainTopic;

    @SerializedName("subTopic")
    private String subTopic;

    @SerializedName("completedOn")
    private String completedOn;

    // Getters
    public String getMainTopic() {
        return mainTopic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public String getCompletedOn() {
        return completedOn;
    }
}

package firstapp.example.lipsclone.api.Models.complaint;

import com.google.gson.annotations.SerializedName;

public class Complaint {

    @SerializedName("c_id")
    private String cId;

    @SerializedName("cdate")
    private String date;

    @SerializedName("title")
    private String title;

    @SerializedName("desp")
    private String description;

    @SerializedName("status")
    private String status;

    @SerializedName("remark")
    private String remark;

    @SerializedName("img")
    private String image;

    @SerializedName("revert")
    private String revert;

    @SerializedName("revert_read")
    private String revertRead;

    // Getters
    public String getCId() { return cId; }
    public String getDate() { return date; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getRemark() { return remark; }
    public String getImage() { return image; }
    public String getRevert() { return revert; }
    public String getRevertRead() { return revertRead; }
}

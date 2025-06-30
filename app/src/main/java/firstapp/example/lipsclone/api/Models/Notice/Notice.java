package firstapp.example.lipsclone.api.Models.Notice;

import com.google.gson.annotations.SerializedName;

// Notice.java
public class Notice {
    @SerializedName("noticeId")
    public String noticeId;

    @SerializedName("noticeNo")
    public String noticeNo;

    @SerializedName("noticeDate")
    public String noticeDate;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("category")
    public String category;

    @SerializedName("noticeBy")
    public String noticeBy;

    @SerializedName("issuedBy")
    public String issuedBy;

    @SerializedName("emp")
    public String emp;

    @SerializedName("header")
    public String header;

    @SerializedName("read")
    public String read;
}



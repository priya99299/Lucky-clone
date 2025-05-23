package firstapp.example.lipsclone.api.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocumentModel {
    @SerializedName("d_id")
    private String d_id;

    @SerializedName("docname")
    private String docname;

    @SerializedName("file")
    private String file;

    @SerializedName("status")
    private String status;

    // Add getters
    public String getD_id() { return d_id; }
    public String getDocname() { return docname; }
    public String getFile() { return file != null ? file : ""; }
    public String getStatus() { return status != null ? status : ""; }
}


package firstapp.example.lipsclone.api.Models;

import com.google.gson.annotations.SerializedName;

public class DocumentModel {

    @SerializedName("d_id")
    private String dId;

    @SerializedName("docname")
    private String docname;

    @SerializedName("s_id")
    private String sId;

    @SerializedName("file")
    private String file;

    @SerializedName("type")
    private String type;

    @SerializedName("storedat")
    private String storedAt;

    @SerializedName("status")
    private String status;

    @SerializedName("returndate")
    private String returnDate;

    @SerializedName("remark")
    private String remark;

    // Getters
    public String getDId() {
        return dId;
    }

    public String getDocname() {
        return docname;
    }

    public String getSId() {
        return sId;
    }

    public String getFile() {
        return file;
    }

    public String getType() {
        return type;
    }

    public String getStoredAt() {
        return storedAt;
    }

    public String getStatus() {
        return status;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getRemark() {
        return remark;
    }
}

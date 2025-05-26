package firstapp.example.lipsclone.api.Models;

public class DocumentModel {
    private String docname;
    private String docurl;

    public DocumentModel(String docname, String docurl) {
        this.docname = docname;
        this.docurl = docurl;
    }

    public String getDocname() {
        return docname;
    }

    public String getDocurl() {
        return docurl;
    }
}

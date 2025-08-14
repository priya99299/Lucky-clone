package firstapp.example.lipsclone.api.Models.canteen;






public class CanteenMenuRequest {
    private String action;
    private String page;

    public CanteenMenuRequest(String action, String page) {
        this.action = action;
        this.page = page;
    }
}

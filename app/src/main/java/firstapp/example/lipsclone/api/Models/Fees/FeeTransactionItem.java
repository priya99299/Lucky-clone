package firstapp.example.lipsclone.api.Models.Fees;

public class FeeTransactionItem {
    private String transactionId;
    private String transactionDate;
    private String ledgerId;
    private String amount;
    private String particulars;
    private String type;
    private String relatedTo;
    private String msg;
    private String table;

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public String getAmount() {
        return amount;
    }

    public String getParticulars() {
        return particulars;
    }

    public String getType() {
        return type;
    }

    public String getRelatedTo() {
        return relatedTo;
    }

    public String getMsg() {
        return msg;
    }

    public String getTable() {
        return table;
    }

    public boolean isPreviousYearInfo() {
        return msg != null && table != null;
    }
}

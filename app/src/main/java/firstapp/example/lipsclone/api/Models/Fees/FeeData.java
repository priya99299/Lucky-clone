package firstapp.example.lipsclone.api.Models.Fees;


public class FeeData {
    private String transactionDetails;
    private int totalDue;
    private String openingBalance;
    private int runningBalance;
    private String totalDeposite;
    private String nextDueDate;
    private int totalLateFine;
    private String groupId;
    private String startingSession;
    private String message;

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public int getTotalDue() {
        return totalDue;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public int getRunningBalance() {
        return runningBalance;
    }

    public String getTotalDeposite() {
        return totalDeposite;
    }

    public String getNextDueDate() {
        return nextDueDate;
    }

    public int getTotalLateFine() {
        return totalLateFine;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getStartingSession() {
        return startingSession;
    }

    public String getMessage() {
        return message;
    }
}



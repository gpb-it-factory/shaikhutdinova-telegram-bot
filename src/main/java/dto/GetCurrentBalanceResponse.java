package dto;

public class GetCurrentBalanceResponse {
    private long accountId;
    private String accountName;
    private double balance;

    public GetCurrentBalanceResponse(long accountId, String accountName, double balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

    public GetCurrentBalanceResponse() {
    }

    public long getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "GetCurrentBalanceResponse{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}

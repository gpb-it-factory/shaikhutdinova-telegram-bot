package dto;

public class GetCurrentBalanceRequest {

    private long userId;

    public GetCurrentBalanceRequest(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }


    public void setUserId(long userId) {
        this.userId = userId;
    }
}

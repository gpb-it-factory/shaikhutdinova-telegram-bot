package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferResponse {
    private String transferId;

    public TransferResponse(String transferId) {
        this.transferId = transferId;
    }

    public TransferResponse() {
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }
}
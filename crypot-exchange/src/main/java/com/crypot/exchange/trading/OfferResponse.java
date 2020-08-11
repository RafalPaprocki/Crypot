package com.crypot.exchange.trading;

import java.util.List;

public class OfferResponse {
    String status;
    String offerId;
    Boolean completed;

    public OfferResponse(String status, String offerId, Boolean completed) {
        this.status = status;
        this.offerId = offerId;
        this.completed = completed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}

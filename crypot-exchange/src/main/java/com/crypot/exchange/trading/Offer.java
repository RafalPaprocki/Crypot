package com.crypot.exchange.trading;

import java.math.BigDecimal;

public class Offer {
    private String market;
    private BigDecimal amount;
    private  BigDecimal rate;
    private String offerType;
    private String mode;
    private boolean postOnly;

    public Offer(String market, BigDecimal amount, BigDecimal rate, String offerType, String mode, boolean postOnly) {
        this.market = market;
        this.amount = amount;
        this.rate = rate;
        this.offerType = offerType;
        this.mode = mode;
        this.postOnly = postOnly;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}

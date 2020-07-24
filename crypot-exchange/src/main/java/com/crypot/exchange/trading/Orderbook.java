package com.crypot.exchange.trading;

import java.math.BigDecimal;

public class Orderbook {
    String type;
    BigDecimal presentCryptoAmount;
    Integer sequentialNumber;

    public Orderbook(String type, BigDecimal presentCryptoAmount, Integer sequentialNumber) {
        this.type = type;
        this.presentCryptoAmount = presentCryptoAmount;
        this.sequentialNumber = sequentialNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPresentCryptoAmount() {
        return presentCryptoAmount;
    }

    public void setPresentCryptoAmount(BigDecimal presentCryptoAmount) {
        this.presentCryptoAmount = presentCryptoAmount;
    }

    public Integer getSequentialNumber() {
        return sequentialNumber;
    }

    public void setSequentialNumber(Integer sequentialNumber) {
        this.sequentialNumber = sequentialNumber;
    }
}

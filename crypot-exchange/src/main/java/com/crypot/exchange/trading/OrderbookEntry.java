package com.crypot.exchange.trading;

import java.math.BigDecimal;

public class OrderbookEntry {
    BigDecimal quantity;
    BigDecimal rate;
    Integer sequentialNumber;

    public OrderbookEntry(BigDecimal quantity, BigDecimal rate, Integer sequentialNumber) {
        this.quantity = quantity;
        this.rate = rate;
        this.sequentialNumber = sequentialNumber;
    }

    public Integer getSequentialNumber() {
        return sequentialNumber;
    }

    public void setSequentialNumber(Integer sequentialNumber) {
        this.sequentialNumber = sequentialNumber;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}

package com.crypot.exchange.trading;

import java.math.BigDecimal;

public class OrderbookEntry {
    BigDecimal quantity;
    BigDecimal rate;

    public OrderbookEntry(BigDecimal quantity, BigDecimal rate) {
        this.quantity = quantity;
        this.rate = rate;
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

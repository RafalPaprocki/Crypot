package com.crypot.exchange.trading;

import java.math.BigDecimal;

public class Ticker {
    private BigDecimal highestBid;
    private BigDecimal lowestAsk;
    private BigDecimal lastTransactionRate;

    public Ticker(BigDecimal highestBid, BigDecimal lowestAsk, BigDecimal lastTransactionRate) {
        this.highestBid = highestBid;
        this.lowestAsk = lowestAsk;
        this.lastTransactionRate = lastTransactionRate;
    }

    public BigDecimal getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(BigDecimal highestBid) {
        this.highestBid = highestBid;
    }

    public BigDecimal getLowestAsk() {
        return lowestAsk;
    }

    public void setLowestAsk(BigDecimal lowestAsk) {
        this.lowestAsk = lowestAsk;
    }

    public BigDecimal getLastTransactionRate() {
        return lastTransactionRate;
    }

    public void setLastTransactionRate(BigDecimal lastTransactionRate) {
        this.lastTransactionRate = lastTransactionRate;
    }
}

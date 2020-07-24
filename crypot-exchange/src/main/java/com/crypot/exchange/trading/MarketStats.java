package com.crypot.exchange.trading;

import java.math.BigDecimal;

public class MarketStats {
    String marketCode;
    BigDecimal highestExchange24H;
    BigDecimal lowestExchange24H;
    BigDecimal volumen24H;
    BigDecimal avgRate24H;

    public MarketStats(String marketCode, BigDecimal highestExchange24H, BigDecimal lowestExchange24H, BigDecimal volumen24H, BigDecimal avgRate24H) {
        this.marketCode = marketCode;
        this.highestExchange24H = highestExchange24H;
        this.lowestExchange24H = lowestExchange24H;
        this.volumen24H = volumen24H;
        this.avgRate24H = avgRate24H;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public BigDecimal getHighestExchange24H() {
        return highestExchange24H;
    }

    public void setHighestExchange24H(BigDecimal highestExchange24H) {
        this.highestExchange24H = highestExchange24H;
    }

    public BigDecimal getLowestExchange24H() {
        return lowestExchange24H;
    }

    public void setLowestExchange24H(BigDecimal lowestExchange24H) {
        this.lowestExchange24H = lowestExchange24H;
    }

    public BigDecimal getVolumen24H() {
        return volumen24H;
    }

    public void setVolumen24H(BigDecimal volumen24H) {
        this.volumen24H = volumen24H;
    }

    public BigDecimal getAvgRate24H() {
        return avgRate24H;
    }

    public void setAvgRate24H(BigDecimal avgRate24H) {
        this.avgRate24H = avgRate24H;
    }
}

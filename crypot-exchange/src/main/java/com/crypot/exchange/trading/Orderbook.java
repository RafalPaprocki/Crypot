package com.crypot.exchange.trading;

import java.util.List;

public class Orderbook {
    List<OrderbookEntry> asks;
    List<OrderbookEntry> bids;

    public Orderbook(List<OrderbookEntry> asks, List<OrderbookEntry> bids) {
        this.asks = asks;
        this.bids = bids;
    }

    public List<OrderbookEntry> getAsks() {
        return asks;
    }

    public void setAsks(List<OrderbookEntry> asks) {
        this.asks = asks;
    }

    public List<OrderbookEntry> getBids() {
        return bids;
    }

    public void setBids(List<OrderbookEntry> bids) {
        this.bids = bids;
    }
}

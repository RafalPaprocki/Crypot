package com.crypot.exchange.trading;

import java.util.List;

public interface TradingApi {
    OfferResponse newOffer(Offer offer, String marketCode);
    MarketStats get24HMarketStatistics(String marketCode);
    Ticker getLastPriceTicker(String marketCode);
    Orderbook getOrders(String marketCode, int limit);
    void changeUsedWallets(String marketCode, String firstUUID, String secondUUID);
}

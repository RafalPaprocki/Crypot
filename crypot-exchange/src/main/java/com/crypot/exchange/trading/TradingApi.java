package com.crypot.exchange.trading;

import java.util.List;

public interface TradingApi {
    OfferResponse newOffer(Offer offer);
    MarketStats get24HMarketStatistics(String marketCode);
    Ticker getLastPriceTicker(String marketCode);
    Orderbook getOrders(int limit);
    void changeUsedWallets(String firstUUID, String secondUUID);
}

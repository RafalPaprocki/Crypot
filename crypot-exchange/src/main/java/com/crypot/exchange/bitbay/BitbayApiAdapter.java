package com.crypot.exchange.bitbay;

import com.crypot.exchange.AbstractWebClient;
import com.crypot.exchange.trading.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitbayApiAdapter implements TradingApi {

    private String publicKey = "0c8b4e50-0b47-4954-aa6b-7500b8c17dd7";
    private String secretKey = "6b3f11ad-46c3-4e39-99a8-77de588c2693";
    private String body;
    private AbstractWebClient webClient;
    private ObjectMapper jsonObjectMapper;
    private final int defaultOrderbookLimit = 50;

    public BitbayApiAdapter(){
        this.publicKey = publicKey;
        this.secretKey = secretKey;

        webClient = new BitbayWebClient(secretKey, publicKey);
        jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    @Override
    public OfferResponse newOffer(Offer offer, String marketCode) {
        OfferBitbay newOffer = new OfferBitbay(offer.getAmount(), offer.getRate(), offer.getOfferType(), offer.getMode(), offer.isPostOnly());
        OfferResponseBitbay offerResponse = null;
        try {
            String newOfferJson = jsonObjectMapper.writeValueAsString(newOffer);
            String response = this.webClient.sendAuthPostRequest("trading/offer/" + marketCode, newOfferJson);
            offerResponse = this.jsonObjectMapper.readValue(response, OfferResponseBitbay.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new OfferResponse(offerResponse.status,
                offerResponse.offerId, offerResponse.completed);
    }

    @Override
    public MarketStats get24HMarketStatistics(String marketCode) {
        MarketStatsResponse marketStatsBitbay = null;
        try {
            String result = this.webClient.sendGetRequest("trading/stats/" + marketCode);
            marketStatsBitbay = this.jsonObjectMapper.readValue(result, MarketStatsResponse.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new MarketStats(marketStatsBitbay.stats.m, marketStatsBitbay.stats.h,
                marketStatsBitbay.stats.l, marketStatsBitbay.stats.v,
                marketStatsBitbay.stats.r24h);
    }

    @Override
    public Ticker getLastPriceTicker(String marketCode) {
        TickerBitbayResponse tickerBitbay = null;
        try {
            String result = this.webClient.sendGetRequest("trading/ticker/" + marketCode);
            tickerBitbay = this.jsonObjectMapper.readValue(result, TickerBitbayResponse.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Ticker(tickerBitbay.ticker.highestBid, tickerBitbay.ticker.lowestAsk, tickerBitbay.ticker.rate);
    }

    @Override
    public Orderbook getOrders(String marketCode, int limit) {
        OrderbookBitbayResponse orderbookResponse = null;
        try {
            int correctLimit = checkAndSetCorrectLimit(limit);
            String result = this.webClient.sendGetRequest("trading/orderbook-limited/" + marketCode + "/" + correctLimit);
            orderbookResponse = this.jsonObjectMapper.readValue(result, OrderbookBitbayResponse.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<OrderbookEntry> buy = new ArrayList<>();
        for(var buyOffer: orderbookResponse.buy){
            OrderbookEntry entry = new OrderbookEntry(buyOffer.quantity, buyOffer.postionRate);
            buy.add(entry);
        }

        List<OrderbookEntry> sell = new ArrayList<>();
        for(var sellOffer: orderbookResponse.sell){
            OrderbookEntry entry = new OrderbookEntry(sellOffer.quantity, sellOffer.postionRate);
            sell.add(entry);
        }
        return new Orderbook(sell, buy);
    }

    @Override
    public void changeUsedWallets(String marketCode, String firstUUID, String secondUUID) {
        var configWallets = new WalletsConfig(firstUUID, secondUUID);
        String wallets = "";
        try {
            wallets = jsonObjectMapper.writeValueAsString(configWallets);
            this.webClient.sendAuthPostRequest("trading/offer/" + marketCode, wallets);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.webClient.sendAuthPostRequest("trading/config/" + marketCode, wallets);

    }

    private int checkAndSetCorrectLimit(int limit){
        if(Arrays.asList(10,50,100).contains(limit)){
            return limit;
        } else {
            return defaultOrderbookLimit;
        }
    }

    private static class MarketStatsResponse {
        public String status;
        public MarketStatsBitbay stats;
        public List<String> errors;

        private static class MarketStatsBitbay {
            public String m;
            public BigDecimal h;
            public BigDecimal l;
            public BigDecimal v;
            public BigDecimal r24h;
        }
    }

    private static class OfferResponseBitbay {
        public String status;
        public Boolean completed;
        public String offerId;
    }

    private static class OfferBitbay {
        public BigDecimal amount;
        public BigDecimal rate;
        public String offerType;
        public String mode;
        public Boolean postOnly = null;

        public OfferBitbay(BigDecimal amount, BigDecimal rate, String offerType, String mode, Boolean postOnly) {
            this.amount = amount;
            this.rate = rate;
            this.offerType = offerType;
            this.mode = mode;
            this.postOnly = postOnly;
        }
    }

    private static class TickerBitbayResponse {
        public String status;
        public TickerBitbay ticker;
        public List<String> errors;

        private static class TickerBitbay{
            public BigDecimal highestBid;
            public BigDecimal lowestAsk;
            public BigDecimal rate;
        }
    }

    private static class OrderbookBitbayResponse {
        public String status;
        public List<OrderbookEntryBitbay> sell;
        public List<OrderbookEntryBitbay> buy;
        public List<String> errors;

        private static class OrderbookEntryBitbay{
            @JsonProperty("ra")
            public BigDecimal postionRate;
            @JsonProperty("ca")
            public BigDecimal quantity;
        }
    }

    private static class WalletsConfig{
        public String first;
        public String second;

        public WalletsConfig(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }
}

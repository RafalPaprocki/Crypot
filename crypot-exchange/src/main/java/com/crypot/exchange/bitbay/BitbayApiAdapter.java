package com.crypot.exchange.bitbay;

import com.crypot.exchange.Trade;
import com.crypot.exchange.trading.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class BitbayApiAdapter implements TradingApi {

    private String publicKey = "0c8b4e50-0b47-4954-aa6b-7500b8c17dd7";
    private String secretKey = "6b3f11ad-46c3-4e39-99a8-77de588c2693";
    private String body;
    private WebClient client;

    public BitbayApiAdapter(){
        this.publicKey = publicKey;
        this.secretKey = secretKey;

        this.client = WebClient.builder().filter(logRequest())
                .exchangeStrategies(this.enableLoggingDetails())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            clientRequest.attributes().keySet().stream().forEach(System.out::println); //body is MyRequest.class
            System.out.println(clientRequest.attribute("org.springframework.web.reactive.function.client.ClientRequest.LOG_ID"));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeStrategies enableLoggingDetails(){
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.withDefaults();
        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));
        return exchangeStrategies;
    }

    public String getStringTest() {
          BitbayWebClient b = new BitbayWebClient(secretKey, publicKey);
          String res = b.sendPostRequest("trading/history/transactions", "");

//        Long timeStamp = Instant.now().getEpochSecond();
//        String apiHash = generate(publicKey + timeStamp, secretKey);
//
//        String tickers = WebClient.create()
//                .get()
//                .uri("https://api.bitbay.net/rest/trading/history/transactions")
//                .header("API-Key", publicKey)
//                .header("API-Hash", apiHash)
//                .header("operation-id", UUID.randomUUID().toString())
//                .header("Request-Timestamp", timeStamp.toString())
//                .header("Content-Type", "application/json")
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//                .retrieve()
//                .bodyToFlux(Trade.class)
//                .collectList()
//                .block();

        return res;
    }

    public ResponseEntity<WalletResponse> getAllWallets(){
        Long timeStamp = Instant.now().getEpochSecond();
        String apiHash = generate(publicKey + timeStamp, secretKey);

        WalletResponse resp = this.client
                .get()
                .uri("https://api.bitbay.net/rest/balances/BITBAY/balance")
                .header("API-Key", publicKey)
                .header("API-Hash", apiHash)
                .header("operation-id", UUID.randomUUID().toString())
                .header("Request-Timestamp", timeStamp.toString())
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(WalletResponse.class)
                .block();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public String changeConfiguration(String marketCode) throws Exception{

        MarketChange m = new MarketChange();
        m.first = "a636533b-468d-45a7-b42e-2ae106460b71";
        m.second = "efbffba3-d698-404d-89e4-d9b8f249db8e";

        ObjectMapper Obj = new ObjectMapper();
        String strBody = Obj.writeValueAsString(m);

        Long timeStamp = Instant.now().getEpochSecond();
        String apiHash = generate(publicKey + timeStamp + strBody, secretKey);
        String resp = this.client
                .post()
                .uri("https://api.bitbay.net/rest/trading/config/BTC-PLN" )
                .body(Mono.just(m), MarketChange.class)
                .header("API-Key", publicKey)
                .header("API-Hash", apiHash)
                .header("operation-id", UUID.randomUUID().toString())
                .header("Request-Timestamp", timeStamp.toString())
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return resp;
    }

    public String generate(String data, String key) {

        String result = "";

        try{
            byte [] byteKey = key.getBytes("UTF-8");
            final String HMAC_SHA512 = "HmacSHA512";
            Mac sha512_HMAC = null;
            sha512_HMAC = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512_HMAC.init(keySpec);
            byte [] mac_data = sha512_HMAC.
                    doFinal(data.getBytes("UTF-8"));
            result = bytesToHex(mac_data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();

        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();

    }

    @Override
    public OfferResponse newOffer(Offer offer) {
        return null;
    }

    @Override
    public MarketStats get24HMarketStatistics(String marketCode) {
        return null;
    }

    @Override
    public Ticker getLastPriceTicker(String marketCode) {
        return null;
    }

    @Override
    public Orderbook getOrders(int limit) {
        return null;
    }

    @Override
    public void changeUsedWallets(String firstUUID, String secondUUID) {

    }

    public static class MarketChange{
        public String first;
        public String second;
    }

    public static class Wallet {
        public String id;
        public BigDecimal availableFunds;
        public BigDecimal totalFunds;
        public BigDecimal lockedFunds;
        public String currency;
        public String balanceEngine;
    }

    public static class WalletResponse {
        public String status;
        public List<Wallet> balances;
        public List<String> errors;
    }
}

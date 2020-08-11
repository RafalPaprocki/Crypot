package com.crypot.exchange.bitbay;

import com.crypot.exchange.AbstractWebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

public class BitbayWebClient extends AbstractWebClient {
    final String baseUrl = "https://api.bitbay.net/rest/";
    private WebClient client;

    BitbayWebClient(String secretKey, String publicKey){
        super(secretKey, publicKey);
        this.client = WebClient.builder()
                .exchangeStrategies(this.enableLoggingDetails())
                .build();
    };

    @Override
    public String sendPostRequest(String url, String body) {
        String resp = this.client
                .post()
                .uri(baseUrl + url)
                .body(Mono.just(body), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return resp;
    }

    public String sendAuthPostRequest(String url, String body) {
        String resp = this.client
                .post()
                .uri(baseUrl + url)
                .body(Mono.just(body), String.class)
                .headers(authHeaders(body))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return resp;
    }

    @Override
    public String sendGetRequest(String url) {
        String resp = this.client
                .get()
                .uri(baseUrl + url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return resp;
    }

    @Override
    public String sendAuthGetRequest(String url) {
        String resp = this.client
                .get()
                .uri(baseUrl + url)
                .headers(authHeaders(""))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return resp;
    }

    private Consumer<HttpHeaders> authHeaders(String body){
        Long timeStamp = Instant.now().getEpochSecond();
        return (h) -> {
            h.add("API-Key", this.getPublicKey());
            h.add("API-Hash", this.generateApiHash(timeStamp, body));
            h.add("operation-id", UUID.randomUUID().toString());
            h.add("Request-Timestamp", timeStamp.toString());
            h.add("Content-Type", "application/json");
        };
    }

    private String generateApiHash(Long timeStamp, String body) {
        String hash = "";
        String data = getPublicKey() + timeStamp + body;
        try{
            byte [] byteKey = this.getSecretKey().getBytes("UTF-8");
            final String HMAC_SHA512 = "HmacSHA512";
            Mac sha512_HMAC = null;
            sha512_HMAC = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512_HMAC.init(keySpec);
            byte [] mac_data = sha512_HMAC.
                    doFinal(data.getBytes("UTF-8"));
            hash = bytesToHex(mac_data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    private String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private ExchangeStrategies enableLoggingDetails(){
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.withDefaults();
        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));
        return exchangeStrategies;
    }
}

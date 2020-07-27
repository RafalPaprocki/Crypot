package com.crypot.exchange;

abstract public class AbstractWebClient {
    private String secretKey;
    private String publicKey;

    public AbstractWebClient(String secretKey, String publicKey){
        this.secretKey = secretKey;
        this.publicKey = publicKey;
    }

    protected String getSecretKey(){
        return this.secretKey;
    }
    protected String getPublicKey(){
        return this.publicKey;
    }

    public abstract String sendPostRequest(String url, String body);
    public abstract String sendGetRequest(String url);
}

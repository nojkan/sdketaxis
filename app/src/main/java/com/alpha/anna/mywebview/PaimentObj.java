package com.alpha.anna.mywebview;

import java.net.URLEncoder;

/**
 * Created by Anna on 20.03.2016.
 */
public class PaimentObj {
    private String merchant_key = "2fd5dbe6";
    private String currency = "OUV";
    private String order_id = "MY_ORDER_ID_40";
    private String return_url = "http://hawaii.orangeadd.com/etaxis/webpay/return";
    private String cancel_url = "http://hawaii.orangeadd.com/etaxis/webpay/cancel";
    private String notif_url = "http://hawaii.orangeadd.com/etaxis/webpay/notif";
    private String lang = "fr";
    private int amount = 1;

    public String getMerchant_key() {
        return merchant_key;
    }

    public void setMerchant_key(String merchant_key) {
        this.merchant_key = merchant_key;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrder_id() {

        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }

    public String getNotif_url() {
        return notif_url;
    }

    public void setNotif_url(String notif_url) {
        this.notif_url = notif_url;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void PaimentObj() {};

}

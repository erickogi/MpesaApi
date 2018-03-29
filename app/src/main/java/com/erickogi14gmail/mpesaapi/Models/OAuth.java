package com.erickogi14gmail.mpesaapi.Models;

import android.util.Base64;

/**
 *
 */
public class OAuth {
    private final String app_key;
    private final String app_secret;
    private int production;


    /**
     * @param app_key
     * @param app_secret
     */
    public OAuth(String app_key, String app_secret) {
        this.app_key = app_key;
        this.app_secret = app_secret;

    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public String getApp_key() {
        return app_key;
    }


    public String getApp_secret() {
        return app_secret;
    }


    /**
     * @return
     */
    public String getOauth() {
        String keys = app_key + ":" + app_secret;
        return Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP);
    }


}

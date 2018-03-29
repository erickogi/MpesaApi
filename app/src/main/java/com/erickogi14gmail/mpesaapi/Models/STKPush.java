package com.erickogi14gmail.mpesaapi.Models;

import android.util.Base64;

import com.erickogi14gmail.mpesaapi.NetworkUtills.ApiConstants;
import com.erickogi14gmail.mpesaapi.Utils.Utils;

/**
 *
 */
public class STKPush {
    private final String businessShortCode;
    private final String transactionType;
    private final String amount;
    private final String partyA;
    private final String partyB;
    private final String phoneNumber;
    private final String callBackURL;
    private final String accountReference;
    private final String transactionDesc;
    private String timestamp;
    private int Production;


    public STKPush(String businessShortCode, String transactionType, String amount, String partyA, String partyB, String phoneNumber, String callBackURL, String accountReference, String transactionDesc) {
        this.businessShortCode = businessShortCode;
        this.transactionType = transactionType;
        this.amount = amount;
        this.partyA = partyA;
        this.partyB = partyB;
        this.phoneNumber = phoneNumber;
        this.callBackURL = callBackURL;
        this.accountReference = accountReference;
        this.transactionDesc = transactionDesc;
    }

    public int getProduction() {
        return Production;
    }

    public void setProduction(int production) {
        Production = production;
    }

    public String getBusinessShortCode() {
        return businessShortCode;
    }


    public String getPassword() {
        return getPassword(businessShortCode, Utils.getTimestamp());
    }


    public String getTimestamp() {
        return Utils.getTimestamp();
    }


    public String getTransactionType() {
        return transactionType;
    }


    public String getAmount() {
        return amount;
    }


    public String getPartyA() {
        return partyA;
    }


    public String getPartyB() {
        return partyB;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getCallBackURL() {
        return callBackURL;
    }


    public String getAccountReference() {
        return accountReference;
    }


    public String getTransactionDesc() {
        return transactionDesc;
    }


    /**
     * @param businessShortCode
     * @param timestamp
     * @return
     */
    private String getPassword(String businessShortCode, String timestamp) {
        String str = businessShortCode + ApiConstants.safaricom_pass_key + timestamp;
        return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    }
}

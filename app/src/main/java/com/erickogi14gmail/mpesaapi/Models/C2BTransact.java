package com.erickogi14gmail.mpesaapi.Models;

public class C2BTransact {

    private String ShortCode;
    private String CommandID = "CustomerPayBillOnline";
    private String Amount;
    private String Msisdn;
    private String BillRefNumber;
    private int Production;


    public C2BTransact(String shortCode, String amount, String msisdn, String billRefNumber) {
        ShortCode = shortCode;
        Amount = amount;
        Msisdn = msisdn;
        BillRefNumber = billRefNumber;
    }

    public C2BTransact(String shortCode, String commandID, String amount, String msisdn, String billRefNumber) {
        ShortCode = shortCode;
        CommandID = commandID;
        Amount = amount;
        Msisdn = msisdn;
        BillRefNumber = billRefNumber;
    }

    public String getShortCode() {
        return ShortCode;
    }

    public void setShortCode(String shortCode) {
        ShortCode = shortCode;
    }

    public String getCommandID() {
        return CommandID;
    }


    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getMsisdn() {
        return Msisdn;
    }

    public void setMsisdn(String msisdn) {
        Msisdn = msisdn;
    }

    public String getBillRefNumber() {
        return BillRefNumber;
    }

    public void setBillRefNumber(String billRefNumber) {
        BillRefNumber = billRefNumber;
    }

    public int getProduction() {
        return Production;
    }

    public void setProduction(int production) {
        Production = production;
    }
}

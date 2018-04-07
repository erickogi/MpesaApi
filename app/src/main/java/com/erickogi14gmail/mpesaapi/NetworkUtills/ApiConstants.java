package com.erickogi14gmail.mpesaapi.NetworkUtills;

/**
 * Created by Eric on 3/30/2017.
 */

public class ApiConstants {


    public static final String BASE_URL = "https://sandbox.safaricom.co.ke/";
    public static final String PRODUCTION_BASE_URL = "https://api.safaricom.co.ke/";

    public static final String ACCESS_TOKEN_URL = "oauth/v1/generate?grant_type=client_credentials";
    public static final String PROCESS_REQUEST_URL = "mpesa/stkpush/v1/processrequest";


    public static final String C2B_SIMULATE = "mpesa/c2b/v1/simulate";

    public static final String DEFAULT_TRANSACTION_TYPE = "CustomerPayBillOnline";


    public static final String safaricom_pass_key = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String safaricom_party_b = "174379";
    public static final String safaricom_bussiness_short_code = "174379";


    public static final String safaricom_Auth_key = "hAVnRxa2UOjyAnydVJMG31A0OuDDCxm5";
    public static final String safaricom_Secret = "UcpmdCdI8bAakdgm";


    public static final String callback_url = "http://erickogi.co.ke/Mpesaphp/callback.php";


    public static final int PRODUCTION_RELEASE = 1;
    public static final int PRODUCTION_DEBUG = 2;

    public static final String BUYGOODS = "CustomerBuyGoodsOnline";
    public static final String PAYBILL = "CustomerPayBillOnline";


    public static final String Shortcode1 = "600535";
    public static final String InitiatorNameShortcode1 = "testapi";
    public static final String SecurityCredentialShortcode1 = "Safaricom535!";
    public static final String TestMSISDN = "254708374149";
    private static final String Shortcode2 = "600000";


}

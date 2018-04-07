package com.erickogi14gmail.mpesaapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.erickogi14gmail.mpesaapi.Models.C2BTransact;
import com.erickogi14gmail.mpesaapi.Models.OAuth;
import com.erickogi14gmail.mpesaapi.Models.STKPush;
import com.erickogi14gmail.mpesaapi.NetworkUtills.ApiConstants;
import com.erickogi14gmail.mpesaapi.NetworkUtills.Request;
import com.erickogi14gmail.mpesaapi.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static String amount, phone;
    private static String spinnertext;
    private static Context context;
    private static int spinnerPosition;
    private static ProgressDialog progressDialog;

    /**
     * @param push
     * @return
     */
    private static String generateJsonStringParams(STKPush push) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("BusinessShortCode", push.getBusinessShortCode());
            postData.put("Password", push.getPassword());
            postData.put("Timestamp", push.getTimestamp());
            postData.put("TransactionType", push.getTransactionType());
            postData.put("Amount", push.getAmount());
            postData.put("PartyA", push.getPartyA());
            postData.put("PartyB", push.getPartyB());
            postData.put("PhoneNumber", push.getPhoneNumber());
            postData.put("CallBackURL", push.getCallBackURL());
            postData.put("AccountReference", push.getAccountReference());
            postData.put("TransactionDesc", push.getTransactionDesc());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData.toString();

    }

    private static String generateC2BJsonStringParams(C2BTransact c2BTransact) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("ShortCode", c2BTransact.getShortCode());
            postData.put("CommandID", c2BTransact.getCommandID());
            postData.put("Amount", c2BTransact.getAmount());
            postData.put("Msisdn", c2BTransact.getMsisdn());
            postData.put("BillRefNumber", c2BTransact.getBillRefNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData.toString();

    }
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static void startTransaction(String result, Context context) {
        if (result == null) {
            Toast.makeText(context, "Error Getting Oauth Key", Toast.LENGTH_LONG).show();

            return;
        }


        if (spinnerPosition == 0) {
            STK(result);
        } else if (spinnerPosition == 1) {
            C2B(result);
        } else {
            Toast.makeText(context, "Awaiting implementation", Toast.LENGTH_LONG).show();

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }


    }

    private static void C2B(String result) {
        try {

            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.get("access_token") != null) {

                String token = jsonObject.get("access_token").toString();


                C2BTransact c2BTransact = new C2BTransact(
                        ApiConstants.Shortcode1,
                        ApiConstants.BUYGOODS,
                        amount,
                        ApiConstants.TestMSISDN, ""

                );

                //stkPush.setProduction(ApiConstants.PRODUCTION_RELEASE);


                String url = ApiConstants.BASE_URL + ApiConstants.C2B_SIMULATE;

                if (c2BTransact.getProduction() == ApiConstants.PRODUCTION_RELEASE) {
                    url = ApiConstants.PRODUCTION_BASE_URL + ApiConstants.PROCESS_REQUEST_URL;
                }


                new PayService().execute(url, generateC2BJsonStringParams(c2BTransact), token);

            }

            return;
        } catch (Exception ignored) {


        }


    }

    private static Context getContext() {
        return context;
    }

    private static void STK(String result) {
        try {

            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.get("access_token") != null) {

                String token = jsonObject.get("access_token").toString();


                STKPush stkPush = new
                        STKPush(
                        ApiConstants.safaricom_bussiness_short_code,
                        ApiConstants.DEFAULT_TRANSACTION_TYPE, amount,
                        Utils.sanitizePhoneNumber(phone),
                        ApiConstants.safaricom_party_b,
                        Utils.sanitizePhoneNumber(phone),
                        ApiConstants.callback_url,
                        Utils.sanitizePhoneNumber(phone),
                        "Pay");

                //stkPush.setProduction(ApiConstants.PRODUCTION_RELEASE);


                String url = ApiConstants.BASE_URL + ApiConstants.PROCESS_REQUEST_URL;

                if (stkPush.getProduction() == ApiConstants.PRODUCTION_RELEASE) {
                    url = ApiConstants.PRODUCTION_BASE_URL + ApiConstants.PROCESS_REQUEST_URL;
                }


                new PayService().execute(url, generateJsonStringParams(stkPush), token);

            }

            return;
        } catch (Exception ignored) {


        }
    }

    private int getSelection() {
        return 0;
    }

    /**
     * @param view
     */
    public void pay(View view) {

        progressDialog = new ProgressDialog(MainActivity.this);

        Spinner spinner = findViewById(R.id.spinner);
        spinnertext = spinner.getSelectedItem().toString();
        spinnerPosition = spinner.getSelectedItemPosition();

        progressDialog.setCancelable(false);
        progressDialog.setTitle(spinnertext);
        progressDialog.setMessage("Processing payment");
        progressDialog.show();

        EditText edtPhone = findViewById(R.id.edt_phone);
        EditText edtAmount = findViewById(R.id.edt_amount);


        if (!edtPhone.getText().toString().isEmpty()
                && !edtAmount.getText().toString().isEmpty()
                && Utils.isNetworkAvailable(MainActivity.this)
                && Utils.sanitizePhoneNumber(edtPhone.getText().toString()) != null) {


            OAuth oAuth = new OAuth(
                    ApiConstants.safaricom_Auth_key,
                    ApiConstants.safaricom_Secret);


            //oAuth.setProduction(ApiConstants.PRODUCTION_RELEASE);


            String url = ApiConstants.BASE_URL + ApiConstants.ACCESS_TOKEN_URL;

            if (oAuth.getProduction() == ApiConstants.PRODUCTION_RELEASE)
                url = ApiConstants.PRODUCTION_BASE_URL + ApiConstants.ACCESS_TOKEN_URL;

            phone = edtPhone.getText().toString();
            amount = edtAmount.getText().toString();


            context = MainActivity.this;
            new AuthService(MainActivity.this).execute(url, oAuth.getOauth());


        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(MainActivity.this, "Fill required fields || have internet on || Use a correct phone number", Toast.LENGTH_LONG).show();
        }

    }

    /**
     *
     */
    static class AuthService extends AsyncTask<String, Void, String> {

        final Context context;

        AuthService(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Basic " + strings[1]);
            return Request.get(strings[0], headers);
        }

        protected void onPostExecute(String result) {
            startTransaction(result, context);
        }
    }

    static class PayService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + strings[2]);
            return Request.post(strings[0], strings[1], headers);
        }

        protected void onPostExecute(String result) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (spinnerPosition != 0) {
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            }
        }
    }
}

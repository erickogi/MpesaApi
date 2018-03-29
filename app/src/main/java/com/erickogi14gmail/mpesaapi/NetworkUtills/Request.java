package com.erickogi14gmail.mpesaapi.NetworkUtills;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Eric on 3/30/2017.
 */

public class Request {


    /**
     * @param targetURL
     * @param params
     * @param headers
     * @return
     */
    public static String post(String targetURL, String params, HashMap<String, String> headers) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection to the api
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            //Request method is POST
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);


            //SET Required headers
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                connection.setRequestProperty(key, value);
            }

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();


            //Get Response
            InputStream is;
            if (connection.getResponseCode() / 100 == 2) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            //Read response
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    /**
     * @param path
     * @param headers
     * @return
     */
    public static String get(String path, HashMap<String, String> headers) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(path);

            //Create connection
            connection = (HttpURLConnection) url.openConnection();
            //Set headers and properties
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                connection.setRequestProperty(key, value);
            }
            InputStream is;
            if (connection.getResponseCode() / 100 == 2) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
                buffer.append('\r');
            }
            bufferedReader.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

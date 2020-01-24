package com.lida.APIclient;

import com.lida.traffic_planner.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class httpClient {



    public static String requestGet(String requestUrl) {
        BufferedReader read=null;
        String result = null;
        try {


            URL url = new URL(requestUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            //urlConn.setConnectTimeout(5 * 1000);
            //urlConn.setReadTimeout(5 * 1000);
            ///urlConn.setUseCaches(true);
            //urlConn.setRequestMethod("GET");
            //urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.addRequestProperty("Authorization", "Bearer " + MainActivity.token);

            urlConn.connect();

            if (urlConn.getResponseCode() == 200) {

                result = convertStreamToString(urlConn.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(read!=null){
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(result==null){
            System.out.println("No Result");
        }

        //System.out.println(result);

        return result;
    }

    public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


    public static void keyGet() {

        /* curl -k -d "grant_type=client_credentials" -H "Authorization: Basic dnlhTXJKeXJEZTZsc01
        mclc1NW83QXpxSDJVYTpSNnJCNVh1aGV0YUNTdmREYVJadWFIc2Y1T2Nh" https://api.vasttrafik.se:443/token
        */

        try {
            StringBuilder data = new StringBuilder();
            data.append("grant_type=client_credentials");
            byte[] byteArray = data.toString().getBytes("UTF-8");
            URL url = new URL("https://api.vasttrafik.se:443/token");
            //curl -k -d "grant_type=client_credentials" -H "Authorization: Basic dnlhTXJKeXJEZTZsc01mclc1NW83QXpxSDJVYTpSNnJCNVh1aGV0YUNTdmREYVJadWFIc2Y1T2Nh" https://api.vasttrafik.se:443/token
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(5000);
            con .setDoOutput(true);
            con.setRequestProperty("Authorization",
                    "Basic dnlhTXJKeXJEZTZsc01mclc1NW83QXpxSDJVYTpSNnJCNVh1aGV0YUNTdmREYVJadWFIc2Y1T2Nh");
            OutputStream postStream = con.getOutputStream();
            postStream.write(byteArray, 0, byteArray.length);
            postStream.close();
//      curl -k -d "grant_type=client_credentials" -H "Authorization: Basic WFFWWFh5dElKeHBvcGxBd3JieGFNTEZzUDQ4YTppWWZpakJTbEJJUkpGQ2Z2NndpR2VzNWdpYU1h" https://192.168.15.82:8243/token
            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            BufferedReader in = new BufferedReader(reader);
            String json = in.readLine();

            MainActivity.token = json.split("\"")[13];
            //System.out.println("Token get: " + MainActivity.token);


        }catch (Exception e){
            e.printStackTrace();
        }


    }


}

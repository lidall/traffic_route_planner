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

        //Log.d("Result",result);

        return result;
    }

    public static String convertStreamToString(InputStream is) {

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
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(5000);
            con .setDoOutput(true);
            con.setRequestProperty("Authorization",
                    "Basic dnlhTXJKeXJEZTZsc01mclc1NW83QXpxSDJVYTpSNnJCNVh1aGV0YUNTdmREYVJadWFIc2Y1T2Nh");
            OutputStream postStream = con.getOutputStream();
            postStream.write(byteArray, 0, byteArray.length);
            postStream.close();
            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            BufferedReader in = new BufferedReader(reader);
            String json = in.readLine();

            MainActivity.token = json.split("\"")[13];
            //Log.d("Token", "Token get: " + MainActivity.token);


        }catch (Exception e){
            e.printStackTrace();
        }


    }


}

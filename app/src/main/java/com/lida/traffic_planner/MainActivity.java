package com.lida.traffic_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lida.APIclient.httpClient;
import com.lida.xmlPaser.processXML;


public class MainActivity extends AppCompatActivity {
    public static String journeyPlan;
    public static String originlon;
    public static String originlat;
    public static String token;

    public static String dstlon;
    public static String dstlat;

    private EditText originInput;
    private EditText dstInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new
        StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Intent intent = getIntent();

        Button clickButton = (Button) findViewById(R.id.searchButton);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            try{
                    httpClient.keyGet();

                    originInput = (EditText)findViewById(R.id.originText);
                    String originAddress=originInput.getText().toString().replace(" ", "%20");
                    dstInput = (EditText)findViewById(R.id.dstText);
                    String dstAddress=dstInput.getText().toString().replace(" ", "%20");

                    String originResp = httpClient.requestGet("https://api.vasttrafik.se/bin/rest.exe/v2/location.name?input=" + originAddress);
                    String originName = originResp.split("\n")[2].split("\"")[1];
                    originlon = originResp.split("\n")[2].split("\"")[3];
                    originlat = originResp.split("\n")[2].split("\"")[5];

                    String dstResp = httpClient.requestGet("https://api.vasttrafik.se/bin/rest.exe/v2/location.name?input=" + dstAddress);
                    String dstName = dstResp.split("\n")[2].split("\"")[1];
                    dstlon = dstResp.split("\n")[2].split("\"")[3];
                    dstlat = dstResp.split("\n")[2].split("\"")[5];

                    String journeyAPI = "https://api.vasttrafik.se/bin/rest.exe/v2/trip?originCoordLat=" + originlat + "&originCoordLong=" + originlon +
                            "&originCoordName=" + originName + "&destCoordLat=" + dstlat + "&destCoordLong=" + dstlon + "&destCoordName=" + dstName;
                    String resp = httpClient.requestGet(journeyAPI);

                    processXML pXML = new processXML();
                    pXML.xmlParserProcess(resp);
                    String text = "<br />";
                    for (int j = 0; j < pXML.Leg_list.size(); j++) {

                        if (pXML.Leg_list.get(j).contains("Walk")) {
                            text = text + pXML.Leg_list.get(j) +
                                    " (" + pXML.Time_list.get(j) + " min)</b></font><br />";
                        }else{
                            text = text + pXML.Leg_list.get(j).split("<br />")[0] +
                                    " (" + pXML.Time_list.get(j) + " min)" + "</b></font><br />" +
                                    pXML.Leg_list.get(j).split("<br />")[1] + "<br />";
                        }

                        text = text + pXML.Origin_list.get(j) + "<br />";
                        text = text + pXML.Dst_list.get(j) + "<br /><br />";
                    }
                    journeyPlan = text;

                    Intent intent =new Intent(MainActivity.this,routeDisplay.class);
                    startActivity(intent);

            }catch (Exception e){
                e.printStackTrace();
            }




            }
        });



    }



}

package com.lida.traffic_planner;

import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.lida.APIclient.httpClient;
import static org.junit.Assert.assertTrue;



@RunWith(MockitoJUnitRunner.class)
public class LocalUnitTest {



    @Test
    public void checkAPI() throws Exception{
        String tokenResult = httpClient.keyGet();
        assertTrue(!tokenResult.isEmpty());
    }

    @Before
    public void setup() throws Exception{
        MainActivity.token = httpClient.keyGet();
    }

    @Test
    public void checkContent() {
        String StringResult = httpClient.requestGet("https://api.vasttrafik.se/bin/rest.exe/v2/location.name?input=Central");
        assertTrue(!StringResult.isEmpty());
    }


}
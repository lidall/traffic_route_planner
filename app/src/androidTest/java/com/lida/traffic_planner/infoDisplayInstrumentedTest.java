package com.lida.traffic_planner;

import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.lida.APIclient.httpClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class infoDisplayInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() throws Exception{
        // Specify a valid string.
        MainActivity.token = httpClient.keyGet();
        onView(withId(R.id.originText))
                .perform(typeText("Central"), closeSoftKeyboard());
        onView(withId(R.id.dstText))
                .perform(typeText("Lindholmen"), closeSoftKeyboard());
        onView(withId(R.id.searchButton)).perform(click());

    }


    @Rule
    public ActivityTestRule<routeDisplay> route  = new  ActivityTestRule<>(routeDisplay.class);

    @Test
    public void ensureListViewIsPresent() throws Exception {
        routeDisplay routeActivity = route.getActivity();
        TextView trafficInfo = routeActivity.findViewById(R.id.routeView);
        assertThat(trafficInfo,notNullValue());
        assertThat(trafficInfo, instanceOf(TextView.class));

        MapView map = routeActivity.findViewById(R.id.mapView);
        assertThat(map,notNullValue());
        assertThat(map, instanceOf(MapView.class));


    }
}

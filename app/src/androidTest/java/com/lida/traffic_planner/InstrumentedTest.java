package com.lida.traffic_planner;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.lida.traffic_planner", appContext.getPackageName());
    }



    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureMainIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        EditText dstById = activity.findViewById(R.id.dstText);
        assertThat(dstById,notNullValue());
        assertThat(dstById, instanceOf(EditText.class));

        EditText originById = activity.findViewById(R.id.originText);
        assertThat(originById,notNullValue());
        assertThat(originById, instanceOf(EditText.class));

        Button searchBtn= activity.findViewById(R.id.searchButton);
        assertThat(searchBtn,notNullValue());
        assertThat(searchBtn, instanceOf(Button.class));


    }



}

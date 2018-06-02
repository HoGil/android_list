/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
       /* ArrayList<String> earthquakes = new ArrayList<>();
        earthquakes.add("San Francisco");
        earthquakes.add("London");
        earthquakes.add("Tokyo");
        earthquakes.add("Mexico City");
        earthquakes.add("Moscow");
        earthquakes.add("Rio de Janeiro");
        earthquakes.add("Paris");*/

        // create a fake list of earthquake objects
        Earthquake sanfran = new Earthquake("7.2", "San Fransisco", "Feb 2, 2016");
        Earthquake london = new Earthquake("6.1", "London", "Feb 2, 2016");
        Earthquake tokyo = new Earthquake("3.9", "Tokyo", "Feb 2, 2016");
        Earthquake mexcity = new Earthquake("5.4", "Mexico City", "Feb 2, 2016");
        Earthquake moscow = new Earthquake("2.8", "Moscow", "Feb 2, 2016");
        Earthquake riodeja = new Earthquake("4.9", "Rio De Janeiro", "Feb 2, 2016");
        Earthquake paris = new Earthquake("1.6", "Paris", "Feb 2, 2016");
        Earthquake geneva = new Earthquake("6.6", "Geneva", "Feb 2, 2016");

        // put those objects in an arraylist
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        earthquakes.add(sanfran);
        earthquakes.add(london);
        earthquakes.add(tokyo);
        earthquakes.add(mexcity);
        earthquakes.add(moscow);
        earthquakes.add(riodeja);
        earthquakes.add(paris);
        earthquakes.add(geneva);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.listview_eq);

        // Create a new {@link ArrayAdapter} of earthquakes
        EqArrayAdapter adapter = new EqArrayAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}

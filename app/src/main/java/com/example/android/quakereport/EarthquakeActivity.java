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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // start asynctask thread
        EarthquakeTask task = new EarthquakeTask();
        task.execute();

    }

    private void updateUi(Earthquake earthquake) {
        // display the earthquake magnitutde
        TextView magTextView = (TextView)findViewById(R.id.magnitude_view);
        magTextView.setText(earthquake.getMagnitute());

        // display the location
        TextView locTextView = (TextView)findViewById(R.id.location_view);
        locTextView.setText(earthquake.getCity());

        // display the time or date
        TextView timeView = (TextView)findViewById(R.id.date_view);
        timeView.setText(earthquake.getEventDate());
    }



    // create the task
    private class EarthquakeTask extends AsyncTask<URL, Void, Earthquake> {

        @Override
        protected Earthquake doInBackground(URL... urls) {
            // create URL object
            URL url = createUrl(USGS_REQUEST_URL);

            // perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO
            }

            // extract relevant fields from JSON response and create an earthquake object
            Earthquake eq = extractFeatureFromJson(jsonResponse);

            return eq;
        }


        //update the screen with the given earthquake (ie the result of the async)
        @Override
        protected void onPostExecute(Earthquake earthquake) {
            if (earthquake == null) {
                return;
            }

            updateUi(earthquake);
        }

        // custom method : returns a new URL object from the given string URL
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e("EQ: URL exception", "URL Exc", e);
                return null;
            }

            return url;
        }

        // the actual HTTP request method
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try {

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } catch (IOException e) {

            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }


        // convert the inputstream into a String which contains the whole JSON
        // response from the server.
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }


        // return an eq object by parsing out information
        // about the first earthquake from the input earthquakeJSON string
        private Earthquake extractFeatureFromJson(String earthquakeJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
                JSONArray featureArray = baseJsonResponse.getJSONArray("features");

                // if there are results in the features array
                if (featureArray.length() > 0) {
                    // extract out the first feature (eg an eq)
                    JSONObject firstFeature = featureArray.getJSONObject(0);
                    JSONObject properties = firstFeature.getJSONObject("properties");

                    // extract out the title, time and location
                    String mag = properties.getString("mag");
                    String location = properties.getString("place");
                    String date = properties.getString("time");

                    return new Earthquake(mag, location, date);
                }

            } catch (JSONException e) {
                // TODO
            }
            return null;
        }

    }





}




























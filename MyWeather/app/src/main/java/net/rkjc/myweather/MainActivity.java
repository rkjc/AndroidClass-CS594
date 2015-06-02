package net.rkjc.myweather;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public static final String TAG = " MainActivity ";
    String weatherJSON = "";
    String cityName = "";
    String CountryName = "";
    String dayTemp[];
    ArrayList<String> dayData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showWeatherFragment();

    }

    public void setWetherJSON(String weatherJSON) {
        this.weatherJSON = weatherJSON;
        parseWeatherJSON(weatherJSON);
    }

    public String getCity(){return cityName;}
    public String getCountry(){return CountryName;}
    public String[] getDayTemp(){ return dayTemp;}

    public void parseWeatherJSON(String weatherJSON){
        Log.i(TAG, " parseWeatherJSON weatherJSON=" + weatherJSON);

        JSONObject obj = null;
        try {
            obj = new JSONObject(weatherJSON);

            cityName = obj.getJSONObject("city").getString("name");
            Log.i(Constants.TAG, "parseWeatherJSON cityName=" + cityName);

            CountryName = obj.getJSONObject("city").getString("country");
            Log.i(Constants.TAG, "parseWeatherJSON CountryName=" + CountryName);

            JSONArray arr = obj.getJSONArray("list");
            String dayTemp0[] = new String[arr.length()];
            for (int i = 0; i < arr.length(); i++)
            {
                dayTemp0[i] = arr.getJSONObject(i).getJSONObject("temp").getString("day");
            }
            dayTemp = dayTemp0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void showWeatherFragment(){
        Log.i(Constants.TAG, "showWeatherFragment");
        downloadWeatherData();
        WeatherFragment weatherFrag = new WeatherFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, weatherFrag);
        ft.addToBackStack("fragment a");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void showLocationFragment() {
        Log.i(Constants.TAG, "showLocationFragment");
        LocationsFragment locFrag = new LocationsFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, locFrag);
        ft.addToBackStack("fragment b");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        //    public void showList() {
        //        FragmentManager fm = getSupportFragmentManager();
        //        FragmentTransaction ft = fm.beginTransaction();
        //        ft.add(R.id.mainContainer, new WeatherFragment());
        //        ft.commit();
        //    }
    }

    private class CallAPI extends AsyncTask<String, String, String> {

        //api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json
        String dataString = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
        String weatherData = "";
        InputStream in = null;

        @Override
        protected String doInBackground(String... params) {

            String urlString=params[0]; // URL to call

            InputStream is = null;



            // HTTP Get
            try {

                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //is = new BufferedInputStream(urlConnection.getInputStream());
                is = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String responseString;
                StringBuilder sb = new StringBuilder();

                while ((responseString = reader.readLine()) != null) {
                    sb = sb.append(responseString);
                }

                weatherData = sb.toString();


            } catch (Exception e ) {

                System.out.println(e.getMessage());

                return e.getMessage();

            }

            //Log.i(TAG, "CallAPI doInBackground weatherData=" + weatherData);
            return weatherData;
        }

        protected void onPostExecute(String result) {
            setWetherJSON(weatherData);
        }

    } // end CallAPI


    public void downloadWeatherData(){
        //api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json
        String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";

        Log.i(TAG, "downloadWeatherData dataString=" + urlString);

        new CallAPI().execute(urlString);
    }


    //##################### Options Menu #############################
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    //###############################################################


    @Override
    public void onStart() {
        Log.i(TAG, "Fragment onStart called");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "Fragment onResume called");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "Fragment onPause called");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "Fragment onStop called");
        super.onStop();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "Fragment onDestroy called");
        super.onDestroy();
    }


}

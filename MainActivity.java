package de.guidoschmidtonline.remctrltestapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.io.BufferedReader;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity
{

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final Button buttonLeft = findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                String urlstr = "http://192.168.178.65:8000/test?3,SERVO_MIN";
                mTextMessage.setText("Go Left:" + urlstr);


                new FetchWeatherData().execute();


/*
                URL url = null;
                try
                {
                    url = new URL(urlstr);
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try
                {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                   // InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    // readStream(in);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Accept", "text/html");
                    //urlConnection.setDoOutput(true);
                    //urlConnection.setChunkedStreamingMode(0);
                    urlConnection.setReadTimeout(10000); // millis
                    urlConnection.setConnectTimeout(15000); // millis
                    //urlConnection.setDoOutput(true);
                    urlConnection.getRequestMethod();

                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();

                    //urlConnection.getResponseCode();

                    if (urlConnection.getResponseCode() !=200)
                    {
                        //ok
                    }
                    else
                    {
                        //error
                    }



                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally
                {
                    urlConnection.disconnect();
                } */
            }
        });



    }// on create


    private class FetchWeatherData extends AsyncTask<Void, Void, String> {

       // @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://192.168.178.65:8000/test?3,SERVO_MIN");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }
/*
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvWeatherJson.setText(s);
            Log.i("json", s);
        } */
    }




}

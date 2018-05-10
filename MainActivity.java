package de.guidoschmidtonline.remctrltestapp;

import android.hardware.Sensor;
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

import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.content.Context;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import java.io.BufferedReader;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity
{

    private TextView mTextMessage;
    //private SensorManager mSensorManager;
    //private Sensor mSensor;
//    private TriggerEventListener mTriggerEventListener;
    private float lastX = 0;



    private static final String TAG =  MainActivity.class.getSimpleName();
    private SensorManager manager;
    private Sensor sensor;
    private Sensor orientation_sensor;
    private SensorEventListener listener;
    //private SensorManager.DynamicSensorCallback callback;
    //private HashMap<String, Boolean> hm;




    //
    // main Navogation
    //
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

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
    }; // Bottom Navigation


    //
    // Trigger Event Listener
    //

    /*
    private TriggerEventListener mTriggerEventListener = new TriggerEventListener()
    {
    @Override
    public void onTrigger(TriggerEvent event)
    {
        // Do work
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            mTextMessage.setText("Orientation xxxxxxxxxxxxx");

            float x = event.values[SensorManager.AXIS_X];

            if (Math.abs(x - lastX) > 1.0)
            {
                //if (mOnOrientationListener != null)
                //
                // {
                //    mOnOrientationListener.onOrientationChanged(x);
                // }

                //String urlstr = "http://192.168.178.65:8000/test?3,SERVO_MIN";
                mTextMessage.setText("Orientation xxxxxxxxxxxxx" + x);
                //new FetchWeatherData().execute();

            }

            lastX = x;

        }

    }
};// Trigger event listener */




    //
    // On Create
    //
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        //mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //mSensorManager.requestTriggerSensor(mTriggerEventListener, mSensor);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hash map
        //hm = new HashMap<>();

        // Text Feld
        //
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // listener

        listener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent event)
            {


                if (Math.abs(event.values[0] - lastX) > 0.5)
                {

                    lastX = event.values[0];

                    mTextMessage.setText("changed trigger: " +  event.sensor.getName() + " : " + event.values[0] +
                            " : " + event.values[1] +
                            " : " + event.values[2]
                    );

                    if (event.values[0] > 7) {
                        String urlstr = "http://192.168.178.65:8000/test?3,SERVO_MIN";
                        mTextMessage.setText("Go Left:" + urlstr);
                        new RemCtrlHTTPRequest().execute(urlstr);

                    } else if (event.values[0] < -7) {
                        String urlstr = "http://192.168.178.65:8000/test?3,SERVO_MAX";
                        mTextMessage.setText("Go Left:" + urlstr);
                        new RemCtrlHTTPRequest().execute(urlstr);
                    }
                    else if (Math.abs(event.values[0]) < 1) {
                        String urlstr = "http://192.168.178.65:8000/test?3,SERVO_NEUTRAL";
                        mTextMessage.setText("Go Left:" + urlstr);
                        new RemCtrlHTTPRequest().execute(urlstr);
                    }
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {

            }
        };

        mTextMessage.append("");
        // Liste der vorhandenen Sensoren ausgeben
        //manager = getSystemService(SensorManager.class);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (manager == null) {
            finish();
        }
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors)
        {
            mTextMessage.append(s.getName() + ": " + s.getVendor()  + ": " +  s.getVersion()  + "\n");

            //if (s.getName().contains("Orientation"))
            if (s.getName().contains("Acceleration"))
            {
                manager.registerListener(listener, s, SensorManager.SENSOR_DELAY_FASTEST,10);
            }
        }

        // screen orientation sensor = 1
        //
        //orientation_sensor = sensors.get(1);

        //manager.registerListener(listener, orientation_sensor, SensorManager.SENSOR_DELAY_GAME);


        //
        // Button Left
        final Button buttonLeft = findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                String urlstr = "http://192.168.178.65:8000/test?3,SERVO_MIN";
                mTextMessage.setText("Go Left:" + urlstr);
                new RemCtrlHTTPRequest().execute(urlstr);


            }
        });

        //
        // Button Right
        final Button buttonRight = findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                String urlstr = "http://192.168.178.65:8000/test?3,SERVO_MAX";
                mTextMessage.setText("Go Right:" + urlstr);
                new RemCtrlHTTPRequest().execute(urlstr);



            }
        });



    }// on create

/*
    @Override
    protected void onResume() {
        super.onResume();
        mTextMessage.setText("");
        // Liste der vorhandenen Sensoren ausgeben
        //manager = getSystemService(SensorManager.class);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (manager == null) {
            finish();
        }
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors)
        {
            mTextMessage.append(s.getName() + ": " + s.getVendor()  + ": " +  s.getVersion()  + ": " + Boolean.toString(s.isDynamicSensor()) + "\n");
        }
        // Helligkeitssensor ermitteln
        /*
        sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensor != null) {
            listener = new SensorEventListener() {

                @Override
                public void onAccuracyChanged(Sensor sensor,
                                              int accuracy) {
                    Log.d(TAG, "onAccuracyChanged(): " + accuracy);
                }

                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.values.length > 0) {
                        float light = event.values[0];
                        String text = Float.toString(light);
                        if ((SensorManager.LIGHT_SUNLIGHT <= light)
                                && (light <=
                                SensorManager.LIGHT_SUNLIGHT_MAX)) {
                            text = "sunny";
                        }
                        // jeden Wert nur einmal ausgeben
                        if (!hm.containsKey(text)) {
                            hm.put(text, Boolean.TRUE);
                            text += "\n";
                            mTextMessage.append(text);
                        }
                    }
                }
            };
            // Listener registrieren
            manager.registerListener(listener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            mTextMessage.append("No Sensor");
        } */
        /*
        // Callback für dynamische Sensoren
        callback = null;
        if (manager.isDynamicSensorDiscoverySupported()) {
            callback = new SensorManager.DynamicSensorCallback() {

                @Override
                public void onDynamicSensorConnected(Sensor sensor) {
                    mTextMessage.append("Connected "+ sensor.getName());
                }

                @Override
                public void onDynamicSensorDisconnected(Sensor sensor) {
                    mTextMessage.append("disconnected " + sensor.getName());
                }
            };
            manager.registerDynamicSensorCallback(callback,
                    new Handler());
        }*/
    }

    /*
    @Override
    protected void onPause()
    {
        super.onPause();
        if (sensor != null) {
            manager.unregisterListener(listener);
        }
      //  if (callback != null) {
      //      manager.unregisterDynamicSensorCallback(callback);
      //  }
    }
*/


//}// end of class

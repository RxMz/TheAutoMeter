package com.todaysfuture.autometer;

/**
 * Created by rishabh on 6/4/16.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class withRTT extends Activity {

    public TextView meterKilometer, meterFare;
    public LocationManager locationManager;
    public Location lastLocation = null;
    public Float distance = (float) 0.0, finalKilometers = (float) 0, finalFare = (float) 0;
    public Criteria criteria;
    public Context context;
    public boolean meterRunning = false;


    public withRTT() {
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //context = (Context) getApplicationContext();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withrtt);
        locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
        meterKilometer = (TextView) findViewById(R.id.meterKilometer);
        meterFare = (TextView) findViewById(R.id.meterFare);

        meterKilometer.setText("Distance \t : " + (String.format("%.2f", finalKilometers)));
        meterFare.setText("Fare \t \t \t : " + String.format("%.2f", finalFare));
        boolean locationEnabled = locationManager.isProviderEnabled(locationManager.getBestProvider(criteria, false));
        if (!locationEnabled) {
            Toast.makeText(getApplicationContext(),
                    "Please enable GPS to use Real Time Tracking", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        //trackLocation();
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.track, menu);
            return true;
        }
    */
    public void trackLocation(boolean startTracking) {
        Log.d("Rishabh", "tracklocation started");

        LocationListener locLis = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.d("Rishabh", "override onlocationchanged");
                if (lastLocation == null) {
                    lastLocation = location;
                }
                distance += lastLocation.distanceTo(location);
                lastLocation = location;
                updateMeter(distance);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("Rishabh", "provider status changed");

            }

            @Override
            public void onProviderEnabled(String s) {
                //lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
                Log.d("Rishabh", "provider enabled");
                if (ActivityCompat.checkSelfPermission(withRTT.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(withRTT.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, false), 600, 50, this);


            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(getApplicationContext(),
                        "DO NOT DISABLE GPS!!\nEnable GPS immediately to resume tracking", Toast.LENGTH_LONG).show();

            }
        };
        if (startTracking) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, false), 600, 50, locLis);
        } else {
            try {
                locationManager.removeUpdates(locLis);
            } catch (Exception e ) {
                //Nothing to do here
            }
        }



    }

    public void updateMeter (float mDist) {
        int number = getIntent().getIntExtra("number2", 0);//0 is the default value
        if (number == 0)
            Toast.makeText(getApplicationContext(), "Something has gone wrong.", Toast.LENGTH_LONG);

        if (number == 1) {      //This is for Delhi
            finalKilometers = mDist / 1000;
            if (finalKilometers > 2) {
                finalFare = (((finalKilometers - 2) * 8) + 25);
            } else {
                finalFare = (float) 25.00;
            }
            Log.d("Rishabh", finalKilometers + "  " + finalFare);
            meterKilometer.setText("Distance \t : " + (String.format("%.2f", finalKilometers)));
            meterFare.setText("Fare \t \t \t : " + String.format("%.2f", finalFare));
        }
        if(number==2){
            //Some other city
            //make other ifs also
        }
    }

    public void closeMeter (View v) {
        meterRunning = false;
        trackLocation(false);
        finish();
    }
    public void startMeter (View v) {
        finalFare = (float) 25.00;
        meterFare.setText("Fare \t \t \t : " + String.format("%.2f", finalFare));
        meterRunning = true;
        trackLocation(true);
        Toast.makeText(getApplicationContext(),
                "Location Tracking started! Meter will be updated every second!", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onBackPressed () {
        if (meterRunning) {
            Toast.makeText(getApplicationContext(),
                    "Close the Meter using CLOSE button. To use other apps, press Home and launch apps from the launcher.", Toast.LENGTH_LONG).show();
        } else {
            finish();
        }
    }

}
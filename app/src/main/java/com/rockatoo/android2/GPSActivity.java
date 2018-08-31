package com.rockatoo.android2;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GPSActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    protected GoogleApiClient mGoogleApiClient;
    private static final String TAG = MainActivity.class.getCanonicalName();

    // Location Info
    protected Location mLastLocation;

    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        mLatitudeTextView = (TextView)findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView)findViewById(R.id.longitude_text);

        buildGoogleApiclient();
    }

    protected synchronized void buildGoogleApiclient(){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle){
        // Get Last Location and Longitude
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null){
            // Get Latitude and
            double la = mLastLocation.getLatitude();
            double lo = mLastLocation.getLongitude();

            // Set Information on UI
            mLatitudeTextView.setText(String.valueOf(la));
            mLongitudeTextView.setText(String.valueOf(lo));
        }else{
            // Can't find Location
            String locationErrorMsg = "Can't find Location Information.";
            Toast.makeText(this, locationErrorMsg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i){
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.i(TAG, "Connection failed - Error Code: " + connectionResult.getErrorCode());
    }

}

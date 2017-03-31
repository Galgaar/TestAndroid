package com.example.rdoison.myapplication;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

public class ProgramActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    final static long INTERVAL = 1000;
    final static int MS_IN_S = 1000;
    final static int S_IN_M = 60;
    private static final String TAG = "ProgramActivity";

    String name;
    TextView countdown;

    GoogleApiClient googleApiClient;
    TextView distance;
    TextView curLocation;
    float totalDistance = 0;
    Location lastLocation;

    Map<String, RunSession> program = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        Intent intent = getIntent();
        name = intent.getStringExtra("tag");
        countdown = (TextView) findViewById(R.id.countdown_session);
        distance = (TextView) findViewById(R.id.distance);
        curLocation = (TextView) findViewById(R.id.location_session);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(name);
        initProgram();
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
    }

    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    protected void createLocationRequest() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            requestLocUpdates();
        }
    }

    private void requestLocUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,
                    this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocUpdates();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initProgram() {
        RunSession runSession = new RunSession();
        RunElement runElement = new RunElement();
        runElement.setType(RunElement.ElementType.WARMUP);
        runElement.setLength(10);
        RunElement runElement1 = new RunElement(RunElement.ElementType.RUN, 5);
        List<RunElement> runElementList = new ArrayList<RunElement>();
        runElementList.add(runElement);
        runElementList.add(runElement1);
        runSession.setName("Week 1");
        runSession.setSession(runElementList);
        program.put("W1", runSession);
    }

    public void startSession(View view) {
        RunSession curSession = program.get(name);
        List<RunElement> session = curSession.getSession();
        ListIterator<RunElement> iter = session.listIterator();
        createLocationRequest();
        startElement(iter);
    }

    private void startElement(final ListIterator<RunElement> iter) {
        if (iter.hasNext()) {
            final RunElement element = iter.next();
            CountDownTimer countDownTimer = new CountDownTimer(element.getLength() * MS_IN_S, INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / MS_IN_S;
                    long minutes = seconds / S_IN_M;
                    seconds = seconds % S_IN_M;
                    countdown.setText(String.format(Locale.getDefault(), "%3$s : %1$02d:%2$02d", minutes, seconds, RunElement.elementString.get(element.getType())));
                }

                @Override
                public void onFinish() {
                    startElement(iter);
                }
            };
            countDownTimer.start();
        } else {
            countdown.setText("Félicitations");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (lastLocation != null) {
            float dist = location.distanceTo(lastLocation);
            totalDistance += dist;
        }
        lastLocation = location;
        distance.setText(String.format(Locale.getDefault(), "%.2fkm", totalDistance));
        curLocation.setText(String.format("%d, %d", location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

package com.example.bridgereportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements LocationListener{


    // declaring variables for  edittext, button, spinner and dbhandler
    private EditText bridgeNameEdt, bridgeDateEdt;
    private Button addReportBtn, viewReportBtn;
    private DBHandler dbHandler;
    private Spinner bridgeStateSpinner;
    LocationManager locationManager;


    // declaring variables that are entered and retrieved by the user
    String latitude, longitude, bridgeLocation, bridgeDate, bridgeState,bridgeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Runtime permissions once app is launcher
        // permissions are checked in the PackageManager
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }


        // declaring variables to their related elements in the layout
        bridgeNameEdt = findViewById(R.id.idEdtBridgeName);
        bridgeDateEdt = findViewById(R.id.idEdtBridgeDate);
        bridgeStateSpinner = findViewById(R.id.idSpinnerState);
        addReportBtn = findViewById(R.id.idBtnAddReport);
        viewReportBtn = findViewById(R.id.idBtnViewReport);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.state_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bridgeStateSpinner.setAdapter(adapter);

        // instantiating a DBHandler and passing the context of the activity
        dbHandler = new DBHandler(MainActivity.this);




        // on click listener for the text box that will open a date picker dialog
        // this acts as validation so user does not make errors
        bridgeDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // creating a calendar instance
                final Calendar c = Calendar.getInstance();

                // placing calender values into integer variables
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // creating a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // passing context, this will open in the current activity
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on date set, the values are returned and placed into edittext.
                                bridgeDateEdt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }


        });

        // directs the user to an activity to view our bridge reports
        viewReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewReports.class);
                startActivity(i);
            }


        });


        // calls the get location method

        addReportBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // get location method adds new report when a new location has been requested
                getLocation();

            }
        });


    }

    void getLocation() {
        // request location update from LocationListener
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) { // catching security exceptions as permissions may not have been granted
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        // return lat and long and parse into strings
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());


        //  placing user input into String variables
        bridgeName = bridgeNameEdt.getText().toString();
        bridgeLocation = "Latitude:" + "\n" + latitude +  "\n" + "Longitude:"+  "\n" +longitude;
        bridgeDate = bridgeDateEdt.getText().toString();
        bridgeState =  bridgeStateSpinner.getSelectedItem().toString();

        // validating for empty fields and notifying user
        if (bridgeName.isEmpty() && bridgeDate.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter missing report data", Toast.LENGTH_SHORT).show();
            return;
        }


        // calling addNewReport method and pass values in lines above as parameters
        // details are added to SQLite db

        dbHandler.addNewReport(bridgeName,bridgeLocation,bridgeDate,bridgeState);

        // after a report has been made, a Toast message is displayed to notify user they have performed a task
        // text fields are emptied to allow for new user input
        Toast.makeText(MainActivity.this, "New bridge report has been added.", Toast.LENGTH_SHORT).show();
        bridgeNameEdt.setText("");
        bridgeDateEdt.setText("");
        bridgeStateSpinner.setSelection(0);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please enable location services", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}


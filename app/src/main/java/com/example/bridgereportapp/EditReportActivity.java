package com.example.bridgereportapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;

import java.util.Calendar;

public class EditReportActivity extends AppCompatActivity {
    // variables for our edit text, button, strings and dbhandler class.
    private EditText bridgeNameEdt, bridgeLocationEdt, bridgeDateEdt, bridgeStateEdt;
    private Button updateCourseBtn, deleteReportBtn;
    private DBHandler dbHandler;
    String bridgeName, bridgeLocation, bridgeDate, bridgeState;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reports);

        bridgeNameEdt = findViewById(R.id.bridgeNameEdt);
        bridgeLocationEdt = findViewById(R.id.bridgeLocationEdt);
        bridgeDateEdt = findViewById(R.id.bridgeDateEdt);
        bridgeStateEdt = findViewById(R.id.bridgeStateEdt);
        updateCourseBtn = findViewById(R.id.editReportBtn);
        deleteReportBtn = findViewById(R.id.deleteReportBtn);

        // alert dialog for delete actions
        builder = new AlertDialog.Builder(this);

        // make bridge name and location not editable

        bridgeNameEdt.setKeyListener(null);
        bridgeLocationEdt.setKeyListener(null);


        dbHandler = new DBHandler(EditReportActivity.this);

        // passing values from adapter class
        bridgeName = getIntent().getStringExtra("name");
        bridgeLocation = getIntent().getStringExtra("location");
        bridgeDate = getIntent().getStringExtra("date");
        bridgeState = getIntent().getStringExtra("state");

        // initializing textboxes in update activity with values from database
        bridgeNameEdt.setText(bridgeName);
        bridgeLocationEdt.setText(bridgeLocation);
        bridgeStateEdt.setText(bridgeState);
        bridgeDateEdt.setText(bridgeDate);

        // date picker dialog to update date
        bridgeDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();


                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                bridgeDateEdt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }


        });
        // adding on click listener to our update course button.
        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calling edit report method and passing in parameters of entered details by the user
                dbHandler.editReport(bridgeName, bridgeNameEdt.getText().toString(), bridgeLocationEdt.getText().toString(), bridgeDateEdt.getText().toString(), bridgeStateEdt.getText().toString());

                // displaying a toast message that our course has been updated.
                Toast.makeText(EditReportActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();

                // starting the viewports activity to refresh the reports .
                Intent i = new Intent(EditReportActivity.this, ViewReports.class);
                startActivity(i);
            }
        });
        // calling delete method if the user selects Yes in the alert dialog
        deleteReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Do you want to delete this report?")
                                .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int id) {
                                                finish();
                                                dbHandler.deleteReport(bridgeName);
                                                Toast.makeText(EditReportActivity.this, "Bridge report has been deleted", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(EditReportActivity.this, MainActivity.class);
                                                startActivity(i);
                                            }
                                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = builder.create();

                alert.setTitle("Delete this report");
                alert.show();



            }
        });
    }
}
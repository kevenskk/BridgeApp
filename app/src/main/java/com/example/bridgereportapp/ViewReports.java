package com.example.bridgereportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// activity used to display the reports in a recycler view
public class ViewReports extends AppCompatActivity {

    private ArrayList<ReportModal> reportModalArrayList;
    private DBHandler dbHandler;
    private ReportRVAdapter reportRVAdapter;
    private RecyclerView reportRV;
    private FloatingActionButton floatingActionButton;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        // creating action bar
        ActionBar actionBar =  getSupportActionBar();

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // floating action bar to add new reports

        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewReports.this, MainActivity.class);
                startActivity(i);
            }
        });

        reportModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewReports.this);

        // retrieving the array from DBHandler class
        reportModalArrayList = dbHandler.readReports();

        reportRVAdapter = new ReportRVAdapter(reportModalArrayList, ViewReports.this);
        reportRV = findViewById(R.id.idRVReports);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewReports.this, RecyclerView.VERTICAL, false);
        reportRV.setLayoutManager(linearLayoutManager);

        //  adapter to recycler view.
        reportRV.setAdapter(reportRVAdapter);

        builder = new AlertDialog.Builder(this);
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // method to control the delete all button in the action bar
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.delete:


                builder.setMessage("Do you want to delete all reports?")
                                .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int id) {
                                                finish();
                                                dbHandler.deleteAll();
                                                Toast.makeText(ViewReports.this, "All bridge reports have been deleted", Toast.LENGTH_SHORT).show();

                                                // activity is restarted to refresh the cardview holding the reports that have been deleted
                                                Intent i = new Intent(ViewReports.this, ViewReports.class);
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
                alert.setTitle("Delete All Reports");
                alert.show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

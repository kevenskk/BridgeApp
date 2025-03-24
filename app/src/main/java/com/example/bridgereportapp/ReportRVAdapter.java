package com.example.bridgereportapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// the recycler view adapter creates ViewHolder objects
public class ReportRVAdapter extends RecyclerView.Adapter<ReportRVAdapter.ViewHolder> {

    private ArrayList<ReportModal> reportModalArrayList;
    private Context context;

    // passing data from array list to the adapter
    public ReportRVAdapter(ArrayList<ReportModal> reportModalArrayList, Context context) {
        this.reportModalArrayList = reportModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating layout created for the recycyler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportModal modal = reportModalArrayList.get(position);

        holder.bridgeNameTV.setText(modal.getBridgeName());
        holder.bridgeLocationTV.setText(modal.getBridgeLocation());
        holder.bridgeDateTV.setText(modal.getBridgeDate());
        holder.bridgeStateTV.setText(modal.getBridgeState());

        // on click listener for recycler view items
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calling edit report activity
                 Intent i = new Intent(context, EditReportActivity.class);

                //  passing all our values as context to the activity being started
                i.putExtra("name", modal.getBridgeName());
                i.putExtra("location", modal.getBridgeLocation());
                i.putExtra("date", modal.getBridgeDate());
                i.putExtra("state", modal.getBridgeState());

                context.startActivity(i);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, EditReportActivity.class);

                i.putExtra("name", modal.getBridgeName());
                i.putExtra("location", modal.getBridgeLocation());
                i.putExtra("date", modal.getBridgeDate());
                i.putExtra("state", modal.getBridgeState());

                context.startActivity(i);
            }
        });
    }



     // return the amount of reports present in the array/database
    @Override
    public int getItemCount() {
        return reportModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView bridgeNameTV, bridgeLocationTV, bridgeDateTV, bridgeStateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing text views with the opened reports details
            bridgeNameTV = itemView.findViewById(R.id.idTVBridgeName);
            bridgeLocationTV = itemView.findViewById(R.id.idTVBridgeLocation);
            bridgeDateTV = itemView.findViewById(R.id.idTVBridgeDate);
            bridgeStateTV = itemView.findViewById(R.id.idTVBridgeState);
        }
    }
}

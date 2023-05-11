package com.example.barbershop;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapterAdminDaysOff extends RecyclerView.Adapter<CustomAdapterAdminDaysOff.MyViewHolder> {

    public void removeDayOff(String date, String path){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path).child(date);
        ref.removeValue();
    }

    private String path;
    private ArrayList<DayOff> dataSet;

    public CustomAdapterAdminDaysOff(ArrayList<DayOff> dataSet, String path) {
        this.dataSet = dataSet;
        this.path = path;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewDate;
        TextView textViewDay;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardViewDaysOffCardsLayout);
            textViewDay = (TextView) itemView.findViewById(R.id.textViewCardsLayoutDaysOffDay);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewCardsLayoutDaysOffDate);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext() ).inflate(R.layout.admin_days_off_cards_layout, parent ,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        CardView cardView = viewHolder.cardView;
        TextView textViewDate = viewHolder.textViewDate;
        TextView textViewDay = viewHolder.textViewDay;

        textViewDate.setText(dataSet.get(position).date);
        textViewDay.setText(dataSet.get(position).dayName);

        String date = textViewDate.getText().toString();
        String path2 = this.path;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Removing the day off");
                builder.setMessage("Are you sure you want to remove it?");
                builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeDayOff(date,path2);
                        Toast.makeText(view.getContext(),"Your day off has been removed.", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}

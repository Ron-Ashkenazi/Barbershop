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

public class CustomAdapterUserMyAppointments extends RecyclerView.Adapter<CustomAdapterUserMyAppointments.MyViewHolder> {

    public void removeAppointment(String path){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("appointments").child(path);
        ref.removeValue();
    }

    private String path;
    private ArrayList<Appointment> dataSet;

    public CustomAdapterUserMyAppointments(ArrayList<Appointment> dataSet, String path) {
        this.dataSet = dataSet;
        this.path = path;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewDate;
        TextView textViewDay;
        TextView textViewBarberName;
        TextView textViewTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardViewUserMyAppointments);
            textViewDay = (TextView) itemView.findViewById(R.id.textViewUserCardsLayoutMyAppointmentsDay);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewUserCardsLayoutMyAppointmentsDate);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewUserCardsLayoutMyAppointmentsTime);
            textViewBarberName = (TextView) itemView.findViewById(R.id.textViewUserCardsLayoutMyAppointmentsBarberName);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext() ).inflate(R.layout.user_my_appointments_cards_layout, parent ,false);
        CustomAdapterUserMyAppointments.MyViewHolder myViewHolder = new CustomAdapterUserMyAppointments.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position){
        CardView cardView = viewHolder.cardView;
        TextView textViewDate = viewHolder.textViewDate;
        TextView textViewDay = viewHolder.textViewDay;
        TextView textViewBarberName = viewHolder.textViewBarberName;
        TextView textViewTime = viewHolder.textViewTime;

        textViewDate.setText(dataSet.get(position).date);
        textViewDay.setText(dataSet.get(position).dayName);
        textViewBarberName.setText(dataSet.get(position).userName);
        textViewTime.setText(dataSet.get(position).time);

        String appointmentID;
        appointmentID = textViewDate.getText().toString() + " -- " +
                        textViewTime.getText().toString() + " -- " +
                        textViewBarberName.getText().toString();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Removing the appointment");
                builder.setMessage("Are you sure you want to remove it?");
                builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAppointment(appointmentID);
                        Toast.makeText(view.getContext(),"Your appointment has been removed.", Toast.LENGTH_LONG).show();
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

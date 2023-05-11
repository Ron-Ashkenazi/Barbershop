package com.example.barbershop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class adminAddDayOff extends Fragment {

    public void removeAppointmentsOnDayOff(int year, int month, int dayOfMonth,
                                           String adminEmailID){
        month = month + 1;
        String date = dayOfMonth + "-" + month + "-" + year;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("appointments");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    if (childSnapshot.child("date").getValue().toString().equals(date) &&
                        childSnapshot.child("barberEmailID").getValue().toString().equals(adminEmailID)){
                        childSnapshot.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void writeDayOff (String dayName ,int year, int month, int dayOfMonth, String adminEmailID){
        month = month + 1;
        String date = dayOfMonth + "-" + month + "-" + year;
        DayOff dayoff = new DayOff(dayName, dayOfMonth, month, year, date);
        String path = "barbers/" + adminEmailID + "/daysOff/";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path).child(dayoff.date);
        myRef.setValue(dayoff);
    }

    public adminAddDayOff() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_day_off, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarViewAdminAddDayOff);

        assert getArguments() != null;
        String arg = getArguments().getString("emailAdminAddDayOff");
        String finalArg = arg;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

                if (dayName.equals("Saturday")){
                    Log.d("saturday","Entered the if statement");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("You can't make a day off on Saturday");
                    builder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Day off at "+ dayName+ " " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    builder.setMessage("Are you sure you want to make it a day off?");
                    builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            writeDayOff(dayName, year, month, dayOfMonth, finalArg);
                            removeAppointmentsOnDayOff(year, month, dayOfMonth, finalArg);
                            Toast.makeText(getActivity(),"Your day off has been saved", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            }
        });

        return view;
    }
}
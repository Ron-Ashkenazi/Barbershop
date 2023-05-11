package com.example.barbershop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        Button buttonChangeWorkingHours = view.findViewById(R.id.buttonAdminChangeWorkingHours);
        Button buttonChanges = view.findViewById(R.id.buttonAdminAddDayOff);
        Button buttonDaysOff = view.findViewById(R.id.buttonAdminMyDaysOff);
        Button buttonWorkingHours = view.findViewById(R.id.buttonAdminMyWorkingHours);
        Button buttonMyAppointments = view.findViewById(R.id.buttonAdminMyAppointments);
        TextView textViewWelcome = view.findViewById(R.id.textViewAdminWelcome);

        assert getArguments() != null;
        String arg = getArguments().getString("adminEmail");
        arg = arg.replace(".","");
        String finalArg = arg;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("barbers").child(finalArg);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textViewWelcome.setText(String.valueOf(dataSnapshot.child("name").getValue()) );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonChangeWorkingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("emailAdminWorkHoursString",finalArg);
                Navigation.findNavController(view).navigate(R.id.action_adminFragment_to_adminWorkHoursFragment, bundle);
            }
        });

        buttonChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("emailAdminAddDayOff",finalArg);
                Navigation.findNavController(view).navigate(R.id.action_adminFragment_to_adminAddDayOff, bundle);
            }
        });

        buttonDaysOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("emailAdminDaysOff",finalArg);
                Navigation.findNavController(view).navigate(R.id.action_adminFragment_to_recycleViewFragment, bundle);
            }
        });

        buttonWorkingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("emailAdminMyWorkHoursString",finalArg);
                Navigation.findNavController(view).navigate(R.id.action_adminFragment_to_adminWorkHoursFragment2, bundle);
            }
        });

        buttonMyAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("emailAdminMyAppointments",finalArg);
                Navigation.findNavController(view).navigate(R.id.action_adminFragment_to_adminMyAppointmentsRecycleViewFragment, bundle);
            }
        });

        return view;
    }
}
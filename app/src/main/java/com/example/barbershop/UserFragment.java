package com.example.barbershop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

public class UserFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Button makeAppointment = view.findViewById(R.id.buttonMakeAppointment);
        Button myAppointments = view.findViewById(R.id.buttonUserMyAppointments);
        TextView textViewWelcome = view.findViewById(R.id.textViewUserWelcome);

        assert getArguments() != null;
        String arg = getArguments().getString("userEmail");
        arg = arg.replace(".","");
        String userEmail = arg;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(userEmail);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textViewWelcome.setText(String.valueOf(dataSnapshot.child("name").getValue()) );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userEmail",userEmail);
                Navigation.findNavController(view).navigate(R.id.action_userFragment_to_pickBarberFragment, bundle);
            }
        });

        myAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userEmailMyAppointments",userEmail);
                Navigation.findNavController(view).navigate(R.id.action_userFragment_to_userMyAppointmentsRecycleViewFragment, bundle);
            }
        });
        return view;
    }
}
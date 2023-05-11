package com.example.barbershop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserMyAppointmentsRecycleViewFragment extends Fragment {

    private ArrayList<Appointment> dataSet;
    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private CustomAdapterUserMyAppointments adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        String arg = getArguments().getString("userEmailMyAppointments");
        String finalArg = arg;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("appointments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (childSnapshot.child("userEmailID").getValue().toString().equals(finalArg)){
                        UserMyAppointmentsRecycleViewFragment.this.dataSet.add(new Appointment(
                                childSnapshot.child("barberName").getValue().toString(),
                                childSnapshot.child("dayName").getValue().toString(),
                                childSnapshot.child("date").getValue().toString(),
                                childSnapshot.child("time").getValue().toString()
                        ));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_my_appointments_recycle_view, container,
                false);

        recycleView = view.findViewById(R.id.userMyAppointmentsRecycleView);

        layoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());

        dataSet = new ArrayList<Appointment>();

        assert getArguments() != null;
        String arg = getArguments().getString("userEmailMyAppointments");
        String finalArg = arg;

        adapter = new CustomAdapterUserMyAppointments(dataSet,finalArg);
        recycleView.setAdapter(adapter);

        return view;
    }
}
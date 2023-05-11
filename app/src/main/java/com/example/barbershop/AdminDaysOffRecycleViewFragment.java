package com.example.barbershop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDaysOffRecycleViewFragment extends Fragment {

    private ArrayList<DayOff> dataSet;
    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private CustomAdapterAdminDaysOff adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        String arg = getArguments().getString("emailAdminDaysOff");
        String finalArg = arg;

        String path = "barbers/" + finalArg + "/daysOff";

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    AdminDaysOffRecycleViewFragment.this.dataSet.add(new DayOff(
                            childSnapshot.child("dayName").getValue().toString(),
                            childSnapshot.child("date").getValue().toString()
                    ));
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
        View view = inflater.inflate(R.layout.fragment_admin_days_off_recycle_view, container, false);

        recycleView = view.findViewById(R.id.daysOffRecycleView);

        layoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());

        dataSet = new ArrayList<DayOff>();

        assert getArguments() != null;
        String arg = getArguments().getString("emailAdminDaysOff");
        String finalArg = arg;

        String path = "barbers/" + finalArg + "/daysOff";

        adapter = new CustomAdapterAdminDaysOff(dataSet,path);
        recycleView.setAdapter(adapter);
        return view;
    }
}
package com.example.barbershop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class adminChangeWorkHoursFragment extends Fragment {

    public void writeChangesToDB(String day, String starHour, String finishHour, String adminEmailID){
        String pathStartHour = "barbers/" + adminEmailID + "/days/" + day + "/" + day + "StartHour";
        String pathFinishHour = "barbers/" + adminEmailID + "/days/" + day + "/" + day + "FinishHour";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(pathStartHour);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.setValue(starHour);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference myRef2 = database.getReference(pathFinishHour);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef2.setValue(finishHour);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public Boolean validWorkingHours (String starHour, String finishHour){
        int numStart = -1, numFinish = -1 ;
        String [] hours = MyData.workingHours;
        for (int i = 0; i < hours.length; i++){
            if (starHour == hours[i]){
                numStart = i;
            }
            if (finishHour == hours[i]){
                numFinish = i;
            }
            if(numStart != -1 && numFinish != -1){
                break;
            }
        }

        if( numFinish - numStart >= 8){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_change_work_hours, container, false);

        assert getArguments() != null;
        String arg = getArguments().getString("emailAdminWorkHoursString");
        String finalArg = arg;

        Spinner spinnerDay = view.findViewById(R.id.spinnerDay);
        Spinner spinnerStartH = view.findViewById(R.id.spinnerStartHour);
        Spinner spinnerFinishH = view.findViewById(R.id.spinnerFinishHour);
        TextView textStartH = view.findViewById(R.id.textViewSelectStartHour);
        TextView textFinishH = view.findViewById(R.id.textViewSelectFinishHour);
        Button buttonSaveChanges = view.findViewById(R.id.buttonSaveChanges);

        String [] days = {"None", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String [] hours = MyData.workingHours;
        String[] fridayHours ={"9:00", "9:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00"};

        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, days);
        spinnerDay.setAdapter(adapterDays);

        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                if (item.toString() != "None"){
                    spinnerStartH.setVisibility(View.VISIBLE);
                    spinnerFinishH.setVisibility(View.VISIBLE);
                    textStartH.setVisibility(View.VISIBLE);
                    textFinishH.setVisibility(View.VISIBLE);
                    buttonSaveChanges.setEnabled(true);

                    if (item.toString() == "Friday"){
                        ArrayAdapter<String> adapterHours = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_list_item_1, fridayHours);
                        spinnerStartH.setAdapter(adapterHours);
                        spinnerFinishH.setAdapter(adapterHours);
                    }
                    else{
                        ArrayAdapter<String> adapterHours = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_list_item_1, hours);
                        spinnerStartH.setAdapter(adapterHours);
                        spinnerFinishH.setAdapter(adapterHours);
                    }
                }
                else{
                    spinnerStartH.setVisibility(View.INVISIBLE);
                    spinnerFinishH.setVisibility(View.INVISIBLE);
                    textStartH.setVisibility(View.INVISIBLE);
                    textFinishH.setVisibility(View.INVISIBLE);
                    buttonSaveChanges.setEnabled(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = spinnerDay.getSelectedItem().toString();
                String startHour = spinnerStartH.getSelectedItem().toString();
                String finishHour = spinnerFinishH.getSelectedItem().toString();
                Boolean check;
                check = validWorkingHours(startHour, finishHour);

                if(check){
                    writeChangesToDB(day, startHour, finishHour, finalArg);
                    Toast.makeText(getActivity(),"Your changes have been saved", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(),"Invalid hours", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
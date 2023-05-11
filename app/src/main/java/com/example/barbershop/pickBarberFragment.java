package com.example.barbershop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class pickBarberFragment extends Fragment {

    public pickBarberFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_barber, container, false);
        Button buttonRon = view.findViewById(R.id.buttonBarberRon);
        Button buttonGal = view.findViewById(R.id.buttonBarberGal);

        assert getArguments() != null;
        String arg = getArguments().getString("userEmail");
        String userEmail = arg;


        buttonRon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userEmail",userEmail);
                bundle.putString("barberEmail","ron@gmailcom");
                Navigation.findNavController(view).navigate(R.id.action_pickBarberFragment_to_userMakeAppointmentFragment,
                        bundle);
            }
        });

        buttonGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userEmail",userEmail);
                bundle.putString("barberEmail","gal@gmailcom");
                Navigation.findNavController(view).navigate(R.id.action_pickBarberFragment_to_userMakeAppointmentFragment,
                        bundle);
            }
        });

        return view;
    }
}
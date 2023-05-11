package com.example.barbershop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;

    public Boolean validateInfo(String email, String pass){
        if ( email == null || pass == null || email.isEmpty() || pass.isEmpty() ){ return true; }
        else { return false; }
    }

    public Boolean validateAdmin(String email, String pass){
        if ( (email.equals("ron@gmail.com") || email.equals("gal@gmail.com"))
        && pass.equals("123456") ){ return true; }
        else { return false; }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.editTextHomeFragEmail);
        pass = view.findViewById(R.id.editTextHomeFragPassword);
        Button login = view.findViewById(R.id.buttonHomeFragLogin);
        Button register = view.findViewById(R.id.buttonHomeFragRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registerFragment);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString().trim();
                String passStr = pass.getText().toString().trim();
                Boolean check = validateInfo(emailStr,passStr);
                Boolean adminCheck = validateAdmin(emailStr,passStr);

                if (check) {
                    Toast.makeText(getActivity(),"Please fill all the inputs", Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(emailStr, passStr)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (adminCheck){
                                            Bundle bundle = new Bundle();
                                            bundle.putString("adminEmail",emailStr);
                                            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_adminFragment, bundle);
                                        }
                                        else{
                                            Bundle bundle = new Bundle();
                                            bundle.putString("userEmail",emailStr);
                                            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_userFragment, bundle);
                                        }
                                    } else {
                                        Toast.makeText(getActivity(),"Login fail", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }
}
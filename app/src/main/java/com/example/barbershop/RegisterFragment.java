package com.example.barbershop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;
    EditText id;
    EditText name;

    public void write(Person p){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(p.emailid);
        myRef.setValue(p);
    }

    public Boolean validateInfo(String email, String pass, String id, String name){
        if(email == null || pass == null || id == null || name == null ||
                email.isEmpty() || pass.isEmpty() || id.isEmpty() || name.isEmpty()){ return true; }
        else{ return false; }
    }

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.editTextRegisterFragEmail);
        pass = view.findViewById(R.id.editTextRegisterFragPassword);
        id = view.findViewById(R.id.editTextRegisterFragID);
        name = view.findViewById(R.id.editTextRegisterFragName);
        Button register = view.findViewById(R.id.buttonRegisterFragRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString().trim();
                String emailidStr = email.getText().toString().trim();
                emailidStr = emailidStr.replace(".","");
                String passStr = pass.getText().toString().trim();
                String idStr = id.getText().toString().trim();
                String nameStr = name.getText().toString().trim();
                Boolean check = validateInfo(emailStr,passStr,idStr,nameStr);

                if (check)
                {
                    Toast.makeText(getActivity(),"Please fill all the inputs", Toast.LENGTH_LONG).show();
                }
                else{
                    String finalEmailidStr = emailidStr;
                    mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(),"You have registered successfully!", Toast.LENGTH_LONG).show();
                                        Person p = new Person(nameStr,idStr,emailStr,passStr, finalEmailidStr);
                                        write(p);
                                    }
                                    else {
                                        Toast.makeText(getActivity(),"Sorry something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }
}
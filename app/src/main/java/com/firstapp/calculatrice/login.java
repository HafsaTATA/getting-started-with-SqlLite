package com.firstapp.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    Button login;
    String log,password;
    TextInputEditText logInput,passInput;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DB_Helper db=new DB_Helper(this);
        //i'll add all administators:
        //db.AddUser("hafsa.tata@usmba.ac.ma","hafsa!!2002","TATA","Hafsa",1);
        //db.AddUser("ilyas.nhasse@usmba.ac.ma","ilyas!!2001","NHASSE","Ilyas",1);

        login=findViewById(R.id.button);
        logInput=findViewById(R.id.log);
        passInput=findViewById(R.id.pass);
        signUp=findViewById(R.id.register);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //those must be declared here and nit outside because it shoudl retrive teh value input on the click and not before otherwise it will always be found empty
                log=(logInput).getText().toString();
                password=(passInput).getText().toString();
                ArrayList<Object> output=new ArrayList<>();
                output=db.getPasswordByLogin(log,login.this);

                if (!log.isEmpty()) {
                    if(!password.isEmpty()){
                        if(password.equals(output.get(0))){
                            if(output.get(1).equals("2")) {
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                            } else if (output.get(1).equals("1")) {
                                Intent intent = new Intent(login.this, Admin.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(login.this, "Access denied", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(login.this, "Must enter a password", Toast.LENGTH_SHORT).show();
                } else {
                    // Password is empty, show an error or handle the case accordingly
                    Toast.makeText(login.this, "Must enter an email", Toast.LENGTH_SHORT).show();
                }

            }
        });

    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(login.this,Register.class);
            startActivity(intent);
        }
    });


    }





}
package com.firstapp.calculatrice;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    TextInputEditText login,name,fname,password;
    TextInputLayout loginInputLayout ;

    Button register;
    DB_Helper db=new DB_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        login=findViewById(R.id.logRegister);
        name=findViewById(R.id.nom);
        fname=findViewById(R.id.prenom);
        password=findViewById(R.id.passRegister);
        register = findViewById(R.id.button2);
        loginInputLayout=findViewById(R.id.idLayout1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=login.getText().toString();
                String mdp=password.getText().toString();
                String nom=name.getText().toString();
                String prenom=fname.getText().toString();
                if(!email.isEmpty() && loginInputLayout.getError()==null && !mdp.isEmpty() && !nom.isEmpty() && !prenom.isEmpty()){
                    db.AddUser(email,mdp,nom,prenom,2);
                    Toast.makeText(Register.this, "Welcome "+ nom +" "+ prenom, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


       login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean isFocused) {
               if(!isFocused){
                   checkEmailExists();
               }
           }
       });



    }

    private void checkEmailExists() {
        String email = login.getText().toString();
        ArrayList output = db.getPasswordByLogin(email, Register.this);
        loginInputLayout=findViewById(R.id.idLayout1);

        if (output.get(0) != null) {
            loginInputLayout.setError("This user already exists.");
        } else {
            loginInputLayout.setError(null);
        }
    }
}
package com.firstapp.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
 TextInputLayout nomLayout,prenomLayout,mdpLayout;
 Button add,delete;
 private Animation rotateOpen, rotateClose,toBottom,fromBottom ;
 FloatingActionButton floatingRemove,floatingAdd,X;
 Boolean clicked=false;
 DB_Helper db=new DB_Helper(this);
 TextInputLayout loginInputLayout;
 TextInputEditText login,nom,prenom,mdp;
 TextView msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        nomLayout=findViewById(R.id.nameLayoutAdmin);
        prenomLayout=findViewById(R.id.prenomLayoutAdmin);
        add=findViewById(R.id.AddButton);
        delete=findViewById(R.id.Delete);
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close);
        toBottom= AnimationUtils.loadAnimation(this, R.anim.to_bottom);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        floatingRemove=findViewById(R.id.floatingActionButton3);
        floatingAdd=findViewById(R.id.floatingActionButton2);
        X=findViewById(R.id.floatingActionButton);
        msg=findViewById(R.id.msgText);
        mdpLayout=findViewById(R.id.passwordLayoutAdmin);

        //elemts:
        loginInputLayout=findViewById(R.id.logLayoutAdmin);
        login=findViewById(R.id.logAdmin);
        nom=findViewById(R.id.nomAdmin);
        prenom=findViewById(R.id.prenomAdmin);
        mdp=findViewById(R.id.passAdmin);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=login.getText().toString();
                String password=mdp.getText().toString();
                String fname=nom.getText().toString();
                String name=prenom.getText().toString();
                ArrayList output = db.getPasswordByLogin(email, Admin.this);
                if(!email.isEmpty() && output.get(0)==null &&  !password.isEmpty() && !fname.isEmpty() && !name.isEmpty()){
                    db.AddUser(email,password,fname,name,2);
                    Toast.makeText(Admin.this,fname +" "+ name +" added successfully.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Admin.this,"Missing Information or User already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserFound()==true){
                    showConfirmationDialog();
                }else
                    Toast.makeText(Admin.this, "User not found in DATABASE.", Toast.LENGTH_SHORT).show();
            }
        });


        floatingRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomLayout.setVisibility(View.GONE);
                prenomLayout.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                delete.setVisibility(View.VISIBLE);
                msg.setVisibility(View.VISIBLE);
                mdpLayout.setVisibility(View.GONE);
            }
        });

        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete.setVisibility(View.GONE);
                nomLayout.setVisibility(View.VISIBLE);
                prenomLayout.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                msg.setVisibility(View.GONE);
                mdpLayout.setVisibility(View.VISIBLE);

            }
        });

        X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(clicked);
                setAnimation(clicked);
                clicked = !clicked;

            }
        });
    }
    private void setVisibility(boolean clicked) {
        if (!clicked) {
            floatingAdd.setVisibility(View.VISIBLE);
            floatingRemove.setVisibility(View.VISIBLE);
        } else {
            floatingAdd.setVisibility(View.GONE);
            floatingRemove.setVisibility(View.GONE);
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            floatingAdd.startAnimation(fromBottom);
            floatingRemove.startAnimation(fromBottom);
            X.startAnimation(rotateOpen);

        } else {
            floatingAdd.startAnimation(toBottom);
            floatingRemove.startAnimation(toBottom);
            X.startAnimation(rotateClose);
        }
    }

    private Boolean checkEmailExists() {
        String email = login.getText().toString();
        ArrayList output = db.getPasswordByLogin(email, Admin.this);
        loginInputLayout=findViewById(R.id.idLayout1);

        if (output.get(0) != null) {
            loginInputLayout.setError("User already exists");
            return true;
        } else {
            loginInputLayout.setError(null);
           return false;
        }
    }
    private Boolean UserFound() {
        String email = login.getText().toString();
        ArrayList output = db.getPasswordByLogin(email, Admin.this);

        if (output.get(0) != null) {
            return true;
        } else {
            return false;
        }
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this user?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            String email=login.getText().toString();
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Perform the delete operation here
                db.deleteUser(email);
                Toast.makeText(Admin.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog if "No" is clicked
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
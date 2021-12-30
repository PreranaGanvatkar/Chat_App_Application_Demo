package com.prerana.chatappapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail,edtPassword,edtUsername;
    private Button btnSignin;
    private TextView txtLogin;

    private boolean isSigningUp=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignin = findViewById(R.id.btnSignin);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEmail.getText().toString().isEmpty()||edtPassword.getText().toString().isEmpty()){
                    if (isSigningUp && edtUsername.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (isSigningUp){
                    handleSignUp();
                }
                else{
                    handleLogin();
                }
            }
        });

        txtLogin = findViewById(R.id.txtLogin);

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,FriendsActivity.class));
            finish();
        }

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSigningUp){
                    isSigningUp = false;
                    edtUsername.setVisibility(View.GONE);
                    btnSignin.setText("Log in");
                    txtLogin.setText("Don't have an account ? Sign up");

                }
                else {
                    isSigningUp=true;
                    edtUsername.setVisibility(View.VISIBLE);
                    btnSignin.setText("Sign in");
                    txtLogin.setText("Already have an account? Log in");
                }
            }
        });

    }
    private void handleSignUp(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(edtUsername.getText().toString(),edtEmail.getText().toString(),""));
                    startActivity(new Intent(MainActivity.this,FriendsActivity.class));
                    Toast.makeText(MainActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,FriendsActivity.class));
                    Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    }

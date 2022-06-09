package com.example.stor_it;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signup extends AppCompatActivity {

    TextView txtlogin;
    TextInputEditText Username, Password, confirmPassword;
    Button btn_signup;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Users myUsers = new Users();

        txtlogin = (TextView) findViewById(R.id.textViewLogin);
        btn_signup = (Button) findViewById(R.id.btn_signupthree);

        Username = (TextInputEditText) findViewById(R.id.textfeildUsername);
        Password = (TextInputEditText) findViewById(R.id.textfeildPassword);
        confirmPassword = (TextInputEditText) findViewById(R.id.textfeildConfirmPassword);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performAuth();
            }
        });

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(Signup.this,Login.class);
                startActivity(log);
            }
        });
    }

    private void performAuth() {
        String email = Username.getText().toString();
        String password = Password.getText().toString();
        String conPassword = confirmPassword.getText().toString();

        String encryptedPassword =  encryptText(password);
        String encryptedconPassword = encryptText(conPassword);


        if(encryptedPassword.equals("") || encryptedconPassword.equals("") || email.equals("")){
            Toast.makeText(Signup.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
        else if(encryptedPassword.equals(encryptedconPassword)){

            progressDialog.setMessage("Please wait while signing up...");
            progressDialog.setTitle("Sign Up");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, encryptedPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToLogin();
                        Toast.makeText(Signup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Signup.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
        else{
            Toast.makeText(Signup.this, "Passwords Do Not Match!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendUserToLogin() {
        Intent login = new Intent(Signup.this, Login.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
    }

    //Advanced feature encryption
    private String encryptText(String toString) {
        String MD5 = "MD5";

        try {
            //This creates MD5 hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(toString.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();

            //This creates hex String
            for(byte aMsgDigest : messageDigest){
                String h = Integer.toHexString(0xFF & aMsgDigest);

                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
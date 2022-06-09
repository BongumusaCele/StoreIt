package com.example.stor_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    Button button_signup, button_login;
    TextInputEditText username, password;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Users myUser = new Users();

        button_signup = (Button) findViewById(R.id.btn_signuptwo);
        button_login = (Button) findViewById(R.id.btn_logintwo);
        username = (TextInputEditText) findViewById(R.id.textfeildUsernamelog);
        password = (TextInputEditText) findViewById(R.id.textfeildPasswordlog);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performLogin();

            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(Login.this, Signup.class);
                startActivity(signup);
            }
        });
    }

    private void performLogin() {
        String Password = password.getText().toString();
        String Email = username.getText().toString();

        String encryptedPassword =  encryptText(Password);

        if(encryptedPassword.equals("") || Email.equals("")){
            Toast.makeText(Login.this, "Please enter all fields, Correctly!", Toast.LENGTH_SHORT).show();
        }

                /*else if(myUser.getLoginPassword() != myUser.getSignupPassword()){
                    Toast.makeText(Login.this, "Please enter correct password!", Toast.LENGTH_LONG).show();
                }*/

        else{
            progressDialog.setMessage("Please wait while loging in...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(Email, encryptedPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToMenu();
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToMenu() {
        Intent menuAct = new Intent(Login.this, Menu.class);
        menuAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuAct);
    }

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
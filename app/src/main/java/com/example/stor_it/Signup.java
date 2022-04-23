package com.example.stor_it;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Signup extends AppCompatActivity {

    TextView txtlogin;
    TextInputEditText Username, Password, confirmPassword;
    Button btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Users myUsers = new Users();

        txtlogin = (TextView) findViewById(R.id.textViewLogin);
        btn_signup = (Button) findViewById(R.id.btn_signupthree);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = (TextInputEditText) findViewById(R.id.textfeildUsername);
                Password = (TextInputEditText) findViewById(R.id.textfeildPassword);
                confirmPassword = (TextInputEditText) findViewById(R.id.textfeildConfirmPassword);

                myUsers.setSignupUsername(Username.getText().toString());
                myUsers.setSignupPassword(Password.getText().toString());
                myUsers.setConfirmPassword(confirmPassword.getText().toString());

                if(myUsers.getSignupPassword().equals("") || myUsers.getConfirmPassword().equals("") || myUsers.getSignupUsername().equals("")){
                    Toast.makeText(Signup.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if(myUsers.getSignupPassword().equals(myUsers.getConfirmPassword())){
                    Toast.makeText(Signup.this, "You have been registered!", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(Signup.this, Login.class);
                    startActivity(login);

                }
                else{
                    Toast.makeText(Signup.this, "Passwords Do Not Match!", Toast.LENGTH_SHORT).show();
                }
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
}
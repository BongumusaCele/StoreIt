package com.example.stor_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    Button button_signup, button_login;
    TextInputEditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Users myUser = new Users();

        button_signup = (Button) findViewById(R.id.btn_signuptwo);
        button_login = (Button) findViewById(R.id.btn_logintwo);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = (TextInputEditText) findViewById(R.id.textfeildUsernamelog);
                password = (TextInputEditText) findViewById(R.id.textfeildPasswordlog);

                myUser.setLoginPassword(password.getText().toString());
                myUser.setLoginUsername(username.getText().toString());

                if(myUser.getLoginUsername().equals("") || myUser.getLoginPassword().equals("")){
                    Toast.makeText(Login.this, "Please enter all fields, Correctly!", Toast.LENGTH_SHORT).show();
                }

                /*else if(myUser.getLoginPassword() != myUser.getSignupPassword()){
                    Toast.makeText(Login.this, "Please enter correct password!", Toast.LENGTH_LONG).show();
                }*/

                else{
                    Toast.makeText(Login.this, "Login Success!", Toast.LENGTH_LONG).show();
                    Intent menuAct = new Intent(Login.this, Menu.class);
                    startActivity(menuAct);
                }
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
}
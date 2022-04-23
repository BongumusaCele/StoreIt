package com.example.stor_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddCollection extends AppCompatActivity {

    Button createbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        createbtn = findViewById(R.id.btn_create);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addcollection = new Intent(AddCollection.this, Menu.class);
                startActivity(addcollection);
            }
        });
    }
}
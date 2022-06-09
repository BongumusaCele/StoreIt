package com.example.stor_it;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Additem extends AppCompatActivity {

    ImageView addPic;
    Button addPhoto, addItemBTN;
    EditText edtitemName, edtitemCategory, edtitemDescription, edtitemDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        addPhoto = findViewById(R.id.btnAddPhoto);
        addPic = findViewById(R.id.ImageViewAddPhoto);
        //edtitemName = findViewById(R.id.textfeildItemName);
        //edtitemCategory = findViewById(R.id.textfieldItemcategory);
        //edtitemDescription = findViewById(R.id.textfieldItemDescription);
        //edtitemDate = findViewById(R.id.textfieldItemDate);
        addItemBTN = findViewById(R.id.btn_addItem);

        String name = getIntent().getStringExtra("Name");
        String goal = getIntent().getStringExtra("Goal");

        addItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*String itemName = "", itemCategory = "", itemDescription ="", itemDate ="";
                int img;

                if(!edtitemName.getText().toString().equals("")){
                    itemName = edtitemName.getText().toString();
                }else{
                    Toast.makeText(Additem.this,"Please Enter Category Name!", Toast.LENGTH_SHORT);
                }

                if(!edtitemCategory.getText().toString().equals("")){
                    itemCategory = edtitemCategory.getText().toString();
                }else{
                    Toast.makeText(Additem.this,"Please Enter Category Name!", Toast.LENGTH_SHORT);
                }

                if(!edtitemDescription.getText().toString().equals("")){
                    itemDescription = edtitemDescription.getText().toString();
                }else{
                    Toast.makeText(Additem.this,"Please Enter Category Name!", Toast.LENGTH_SHORT);
                }

                if(!edtitemDate.getText().toString().equals("")){
                    itemDate = edtitemDate.getText().toString();
                }else{
                    Toast.makeText(Additem.this,"Please Enter Category Name!", Toast.LENGTH_SHORT);
                }




                Intent i = new Intent(Additem.this, CategoryClicked.class);
                i.putExtra("Name", name);
                i.putExtra("Goal",goal);
                i.putExtra("ItemName", itemName);
                i.putExtra("ItemCategory",itemCategory);
                i.putExtra("ItemDescription", itemDescription);
                i.putExtra("ItemDate",itemDate);
                i.putExtra("img", (Parcelable) addPic);
                startActivity(i);*/

                Toast.makeText(Additem.this, "Image added!", Toast.LENGTH_SHORT);
            }
        });

        if(ContextCompat.checkSelfPermission(Additem.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Additem.this, new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");

            addPic.setImageBitmap(captureImage);
        }
    }
}
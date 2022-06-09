package com.example.stor_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoryClicked extends AppCompatActivity {

    FloatingActionButton floataddBtn;
    RecyclerView recyclerView;
    ArrayList<ItemModel> arrItem = new ArrayList<>();
    RecyclerItemAdapter adapter;
    Button addPic;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAddPic:
                Intent addpic = new Intent(CategoryClicked.this, Additem.class);
                startActivity(addpic);
                return true;
            case R.id.itemAddFile:
                Intent addfile = new Intent(CategoryClicked.this, AddFile.class);
                startActivity(addfile);
                return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_clicked);

        //extracting variables
        String name = getIntent().getStringExtra("Name");
        String goal = getIntent().getStringExtra("Goal");
        String itemName = getIntent().getStringExtra("ItemName");
        String ItemCategory = getIntent().getStringExtra("ItemCategory");
        String ItemDescription = getIntent().getStringExtra("ItemDescription");
        String ItemDate = getIntent().getStringExtra("ItemDate");

        //storing textviews into variables
        TextView txtCategoryName = findViewById(R.id.txtCategoryName);
        TextView txtCategoryGoal = findViewById(R.id.txtCategoryGoal);


        //assigning values to textviews
        txtCategoryName.setText("Category Name: " + name);
        txtCategoryGoal.setText("Goal: " + goal);

        floataddBtn = (FloatingActionButton)  findViewById(R.id.floatingActionButtonAddItems);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        addPic = findViewById(R.id.btnAddTakePhoto);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        arrItem.add(new ItemModel(R.drawable.ic_settings,"Mybook", "Books", "My favourite Book","22 May 2012"));
        arrItem.add(new ItemModel(R.drawable.ic_settings,"Mybook2", "Books", "My not favourite Book","6 July 2012"));
        arrItem.add(new ItemModel(R.drawable.ic_settings,"Mybook3", "Books", "My least favourite Book","9 February 2012"));

        adapter = new RecyclerItemAdapter(this, arrItem);

        recyclerView.setAdapter(adapter);

        adapter.notifyItemChanged(arrItem.size()-1);

        recyclerView.scrollToPosition(arrItem.size()-1);

        floataddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(CategoryClicked.this);
                dialog.setContentView(R.layout.add_item_layout);

                EditText edtItemName = dialog.findViewById(R.id.edtItemName);
                EditText edtItemCategory = dialog.findViewById(R.id.edtItemCategory);
                EditText edtItemDescription = dialog.findViewById(R.id.edtItemDescription);
                EditText edtitemdate = dialog.findViewById(R.id.edtItemDate);
                Button btnAddPhoto = dialog.findViewById(R.id.btnAddTakePhoto);
                Button btnAddTheItem = dialog.findViewById(R.id.btnAddTheItem);

                btnAddTheItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String itemName = "", itemCategory ="", itemDescription="", itemDate="";

                        if(!edtItemName.getText().toString().equals("")){
                            itemName = edtItemName.getText().toString();
                        }else{
                            Toast.makeText(CategoryClicked.this,"Please Enter Item Name!", Toast.LENGTH_SHORT);
                        }

                        if(!edtItemCategory.getText().toString().equals("")){
                            itemCategory = edtItemCategory.getText().toString();
                        }else{
                            Toast.makeText(CategoryClicked.this,"Please Enter Item Category!", Toast.LENGTH_SHORT);
                        }

                        if(!edtItemDescription.getText().toString().equals("")){
                            itemDescription = edtItemDescription.getText().toString();
                        }else{
                            Toast.makeText(CategoryClicked.this,"Please Enter Item Description", Toast.LENGTH_SHORT);
                        }

                        if(!edtitemdate.getText().toString().equals("")){
                            itemDate = edtitemdate.getText().toString();
                        }else{
                            Toast.makeText(CategoryClicked.this,"Please Enter Date of Aquisation!", Toast.LENGTH_SHORT);
                        }

                        //Enter data into the Firebase RealTime database
                        ReadwriteItemData writeCategoryData = new ReadwriteItemData(itemName, itemCategory, itemDescription, itemDate);

                        //Extracting User reference from Database for "Registered Users"
                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Category Items");

                        referenceProfile.child(mUser.getUid()).setValue(writeCategoryData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(CategoryClicked.this,"Data Stored Succesfully!", Toast.LENGTH_SHORT);
                                }else{
                                    Toast.makeText(CategoryClicked.this,"Failed!", Toast.LENGTH_SHORT);
                                }

                            }
                        });

                        arrItem.add(new ItemModel(R.drawable.ic_settings, itemName, itemCategory, itemDescription, itemDate));

                        adapter.notifyItemChanged(arrItem.size()-1);

                        recyclerView.scrollToPosition(arrItem.size()-1);

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_photo, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView1 = (android.widget.SearchView) searchItem.getActionView();

        searchView1.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }
}
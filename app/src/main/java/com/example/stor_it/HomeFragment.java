package com.example.stor_it;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements RecyclerViewInterface{

    FloatingActionButton floataddBtn;
    RecyclerView recyclerView;
    ArrayList<CategoryModel> arrCategory = new ArrayList<>();
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        floataddBtn = (FloatingActionButton) view.findViewById(R.id.floatingActionaddBTN);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        /*arrCategory.add(new CategoryModel(R.drawable.ic_baseline_category,"Cars","5"));
        arrCategory.add(new CategoryModel(R.drawable.ic_baseline_category,"Books","6"));
        arrCategory.add(new CategoryModel(R.drawable.ic_baseline_category,"Jordan 1s","3"));
        arrCategory.add(new CategoryModel(R.drawable.ic_baseline_category,"Trees","2"));
        arrCategory.add(new CategoryModel(R.drawable.ic_baseline_category,"Phones","1"));*/

        RecyclerCategoryAdapter adapter = new RecyclerCategoryAdapter(getActivity(), arrCategory, this);

        recyclerView.setAdapter(adapter);

        floataddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.add_update_lay);

                EditText edtName = dialog.findViewById(R.id.edtName);
                EditText edtGoalNumber = dialog.findViewById(R.id.edtSetGoal);
                Button btnAction = dialog.findViewById(R.id.btnAction);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = "", number = "";

                        if(!edtName.getText().toString().equals("")){
                            name = edtName.getText().toString();
                        }else{
                            Toast.makeText(getActivity(),"Please Enter Category Name!", Toast.LENGTH_SHORT);
                        }

                        if(!edtGoalNumber.getText().toString().equals("")){
                            number = edtGoalNumber.getText().toString();
                        }else{
                            Toast.makeText(getActivity(),"Please Enter Goal Number!", Toast.LENGTH_SHORT);
                        }


                        //Enter data into the Firebase RealTime database
                        ReadwriteCategoryData writeCategoryData = new ReadwriteCategoryData(name, number);

                        //Extracting User reference from Database for "Registered Users"
                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Category");

                        referenceProfile.child(mUser.getUid()).setValue(writeCategoryData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Data Stored Succesfully!", Toast.LENGTH_SHORT);
                                }else{
                                    Toast.makeText(getActivity(),"Failed!", Toast.LENGTH_SHORT);
                                }

                            }
                        });


                        arrCategory.add(new CategoryModel(R.drawable.ic_baseline_category,name, number));

                        adapter.notifyItemChanged(arrCategory.size()-1);

                        recyclerView.scrollToPosition(arrCategory.size()-1);

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;

    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getActivity(), CategoryClicked.class);

        intent.putExtra("Name", arrCategory.get(position).name);
        intent.putExtra("Goal", arrCategory.get(position).number);
        intent.putExtra("View", RecyclerItemAdapter.class);

        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        RecyclerCategoryAdapter adapter = new RecyclerCategoryAdapter(getActivity(), arrCategory, this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
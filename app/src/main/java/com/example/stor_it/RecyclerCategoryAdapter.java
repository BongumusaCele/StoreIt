package com.example.stor_it;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder> implements Filterable {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<CategoryModel> arrCategory;
    ArrayList<CategoryModel> arrCategoryFull;

    RecyclerCategoryAdapter(Context context, ArrayList<CategoryModel> arrCategory, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.arrCategory = arrCategory;
        arrCategoryFull = new ArrayList<>(arrCategory);
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CategoryModel model = (CategoryModel) arrCategory.get(position);
        holder.imageCategory.setImageResource(model.img);
        holder.txtName.setText(model.name);
        holder.txtNameGoal.setText("Category Goal: " + model.number);

        holder.menuPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.item_popup_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.itemEdit:
                                Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.add_update_lay);

                                EditText edtName = dialog.findViewById(R.id.edtName);
                                EditText edtGoalNumber = dialog.findViewById(R.id.edtSetGoal);
                                Button btnAction = dialog.findViewById(R.id.btnAction);
                                TextView txtTitle = dialog.findViewById(R.id.txtTitle);
                                TextView txtNameGoal = dialog.findViewById(R.id.txtNameGoal);

                                txtTitle.setText("Update Category");
                                btnAction.setText("Update");

                                edtName.setText((arrCategory.get(position)).name);
                                edtGoalNumber.setText((arrCategory.get(position).number));

                                btnAction.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String name = "", number = "";

                                        if(!edtName.getText().toString().equals("")){
                                            name = edtName.getText().toString();
                                        }else{
                                            Toast.makeText(context,"Please Enter Category Name!", Toast.LENGTH_SHORT);
                                        }

                                        if(!edtGoalNumber.getText().toString().equals("")){
                                            number = edtGoalNumber.getText().toString();
                                        }else{
                                            Toast.makeText(context,"Please Enter Goal Number!", Toast.LENGTH_SHORT);
                                        }

                                        arrCategory.set(position, new CategoryModel(arrCategory.get(position).img, name,number ));
                                        notifyItemChanged(position);

                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                                Toast.makeText(context, "Edit Clicked", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.itemDelete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                        .setTitle("Delete Category")
                                        .setMessage("Are you sure you want to delete?")
                                        .setIcon(R.drawable.ic_baseline_delete_24)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                arrCategory.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });

                                builder.show();
                                Toast.makeText(context, "Delete Clicked", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return true;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtNameGoal;
        ImageView imageCategory, menuPopup;
        LinearLayout categoryRow;

        public ViewHolder(View itemView){
            super(itemView);

            txtNameGoal = itemView.findViewById(R.id.txtNameGoal);
            menuPopup = itemView.findViewById(R.id.optionsMenu);
            txtName = itemView.findViewById(R.id.txtName);
            imageCategory = itemView.findViewById(R.id.imageCategory);
            categoryRow = itemView.findViewById(R.id.categoryRow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CategoryModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(arrCategoryFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(CategoryModel item : arrCategoryFull){
                    if(item.name.toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrCategory.clear();
            arrCategory.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };
}

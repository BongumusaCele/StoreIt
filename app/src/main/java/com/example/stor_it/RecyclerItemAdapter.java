package com.example.stor_it;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ItemModel> arrItem;
    ArrayList<ItemModel> arrItemFull;

    RecyclerItemAdapter(Context context, ArrayList<ItemModel> arrItem){
        this.context = context;
        this.arrItem = arrItem;
        arrItemFull = new ArrayList<>(arrItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemModel model = (ItemModel) arrItem.get(position);
        holder.ItemImage.setImageResource(model.img);
        holder.txtItemName.setText(model.itemName);
        holder.txtItemCategory.setText(model.itemCategory);
        holder.txtItemDescription.setText(model.itemDescription);
        holder.txtItemDate.setText(model.itemDate);

        holder.optionsMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.mymenu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.itemEdit:
                                Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.add_item_layout);

                                EditText edtItemName = dialog.findViewById(R.id.edtItemName);
                                EditText edtItemCategory = dialog.findViewById(R.id.edtItemCategory);
                                EditText edtItemDescription = dialog.findViewById(R.id.edtItemDescription);
                                EditText edtitemdate = dialog.findViewById(R.id.edtItemDate);
                                Button btnAddPhoto = dialog.findViewById(R.id.btnAddTakePhoto);
                                Button btnAddTheItem = dialog.findViewById(R.id.btnAddTheItem);

                                TextView txtTitle = dialog.findViewById(R.id.txtAddItem);
                                TextView txtNameGoal = dialog.findViewById(R.id.txtNameGoal);

                                txtTitle.setText("Update Item");
                                btnAddTheItem.setText("Update");

                                edtItemName.setText((arrItem.get(position)).itemName);
                                edtItemCategory.setText((arrItem.get(position).itemCategory));
                                edtItemDescription.setText((arrItem.get(position)).itemDescription);
                                edtitemdate.setText((arrItem.get(position).itemDate));

                                btnAddTheItem.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String itemName = "", itemCategory ="", itemDescription="", itemDate="";

                                        if(!edtItemName.getText().toString().equals("")){
                                            itemName = edtItemName.getText().toString();
                                        }else{
                                            Toast.makeText(context,"Please Enter Item Name!", Toast.LENGTH_SHORT);
                                        }

                                        if(!edtItemCategory.getText().toString().equals("")){
                                            itemCategory = edtItemCategory.getText().toString();
                                        }else{
                                            Toast.makeText(context,"Please Enter Item Category!", Toast.LENGTH_SHORT);
                                        }

                                        if(!edtItemDescription.getText().toString().equals("")){
                                            itemDescription = edtItemDescription.getText().toString();
                                        }else{
                                            Toast.makeText(context,"Please Enter Item Description", Toast.LENGTH_SHORT);
                                        }

                                        if(!edtitemdate.getText().toString().equals("")){
                                            itemDate = edtitemdate.getText().toString();
                                        }else{
                                            Toast.makeText(context,"Please Enter Date of Aquisation!", Toast.LENGTH_SHORT);
                                        }


                                        arrItem.set(position, new ItemModel(arrItem.get(position).img, itemName,itemCategory, itemDescription, itemDate ));
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
                                                arrItem.remove(position);
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

                            case R.id.itemAddPic:

                                Toast.makeText(context, "Picture Added", Toast.LENGTH_SHORT).show();
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
        return arrItem.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ItemModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(arrItemFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(ItemModel item : arrItemFull){
                    if(item.itemName.toLowerCase().contains(filterPattern)){
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
            arrItem.clear();
            arrItem.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtItemName, txtItemCategory, txtItemDescription, txtItemDate;
        ImageView ItemImage, optionsMenuItem;

        public ViewHolder(View itemView){
            super(itemView);

            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemCategory = itemView.findViewById(R.id.txtItemCategory);
            txtItemDescription = itemView.findViewById(R.id.txtItemDescription);
            txtItemDate = itemView.findViewById(R.id.txtItemDate);
            ItemImage = itemView.findViewById(R.id.ItemImage);
            optionsMenuItem = itemView.findViewById(R.id.optionsMenuItem);
        }

    }
}

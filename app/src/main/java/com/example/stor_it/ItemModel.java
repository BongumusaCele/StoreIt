package com.example.stor_it;

public class ItemModel {
    int img;
    String itemName, itemCategory, itemDescription, itemDate;

    public ItemModel(int img, String itemName, String itemCategory, String itemDescription, String itemDate){
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.itemDate = itemDate;
        this.img = img;
    }

    public ItemModel(String itemName, String itemCategory, String itemDescription, String itemDate){
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.itemDate = itemDate;
    }

}

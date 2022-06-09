package com.example.stor_it;

public class CategoryModel {

    int img;
    String name, number;

    public CategoryModel(int img, String name, String number){
        this.name = name;
        this.number = number;
        this.img = img;
    }

    public CategoryModel(String name, String number){
        this.name = name;
        this.number = number;
    }
}

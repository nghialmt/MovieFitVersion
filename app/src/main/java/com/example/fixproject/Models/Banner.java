package com.example.fixproject.Models;

public class Banner {
    private String Name;
    private String Image;

    public Banner() {
    }

    public Banner(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

package com.example.myapplicationcocktail;

import android.media.Image;

public class Cocktail {

private String nom;
private String image;
private int id;




    public Cocktail(String nom , String image, int id){
        this.id = id;
        this.image = image;
        this.nom = nom;


    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }
}

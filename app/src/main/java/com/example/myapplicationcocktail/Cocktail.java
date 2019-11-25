package com.example.myapplicationcocktail;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;

public class Cocktail {

private String nom;
private String image;
private int id;

private String category;
private String alcoholic;
private String instruction;
private ArrayList<String> ingedients;


    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public ArrayList<String> getIngedients() {
        return ingedients;
    }

    public void setIngedients(ArrayList<String> ingedients) {
        this.ingedients = ingedients;
    }

//class maStruct1

    public Cocktail(String nom , String image, int id){
        this.id = id;
        this.image = image;
        this.nom = nom;


    }

    public void setImage(String image) {
        this.image = image;
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

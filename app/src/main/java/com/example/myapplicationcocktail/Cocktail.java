package com.example.myapplicationcocktail;

import android.graphics.Bitmap;
import android.media.Image;

public class Cocktail {

private String nom;
private String image;
private int id;
private String description;
private String category;
private String alcoholic;
private String instruction;
private String[] ingedients;
private String[] dose;

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

    public String[] getIngedients() {
        return ingedients;
    }

    public void setIngedients(String[] ingedients) {
        this.ingedients = ingedients;
    }

    public String[] getDose() {
        return dose;
    }

    public void setDose(String[] dose) {
        this.dose = dose;
    }
//class maStruct1

    public Cocktail(String nom , String image, int id){
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.description= null;

    }

    public void setImage(String image) {
        this.image = image;
    }

    public Cocktail(String nom , int id){
        this.id = id;
        this.image = null;
        this.nom = nom;
        this.description= null;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

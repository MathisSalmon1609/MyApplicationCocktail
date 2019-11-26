package com.example.myapplicationcocktail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ListeCocktails {


    ArrayList<Cocktail> listeCocktails = new ArrayList<Cocktail>();
    Context appContext;



    public ListeCocktails(Context ctx) {

        appContext = ctx.getApplicationContext();

    }

    public void ajouteCocktail(String nom, String image , int id)
    {
        listeCocktails.add(new Cocktail(nom, image , id));
    }

    public Cocktail get(int i) {
        return (Cocktail) this.listeCocktails.get(i);

    }

    public boolean deleteCocktail(int i) {
        this.listeCocktails.remove(i);
        return true;

    }

    public boolean deleteCocktailByID(int i) {
        for(int j =0 ; j < listeCocktails.size() ; j++ ){
            if (listeCocktails.get(j).getId() == i){
                this.listeCocktails.remove(listeCocktails.get(j));
            }
        }
        return true;

    }

    public void update2(List<Cocktail> liste){
        listeCocktails.clear();
        for (int i = 0 ; i < liste.size() ; i++ ){
            listeCocktails.add(i,liste.get(i));
        }
    }


    public ArrayList<Cocktail> getListeCocktails() {
        return this.listeCocktails;
    }

    public void setListeCocktails(ArrayList<Cocktail> listeCocktails) {
        this.listeCocktails = listeCocktails;
    }

    @Override
    public String toString() {
        return "ListeCocktails{" +
                "listeCocktails=" + listeCocktails +
                ", appContext=" + appContext +
                '}';
    }





    public int size() {
        return this.listeCocktails.size();

    }
}

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

    public void update2(List<Cocktail> liste){
        listeCocktails.clear();
        for (int i = 0 ; i < liste.size() ; i++ ){
            listeCocktails.add(i,liste.get(i));
        }
    }


    public int size() {
        return this.listeCocktails.size();

    }
}

package com.example.myapplicationcocktail;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.CheckBox;

public class Favoris extends AppCompatActivity implements FavFragment.OnListFragmentInteractionListener {

    private DrawerLayout drawer;
    Context ctx;
    BDDOpenHelper dbHelper;
    private ListeCocktails listeFavoris;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        this.ctx = this.getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("My Favorites");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_fav);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment FavFragment = (FavFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.mainFrag_fav);


        listeFavoris = new ListeCocktails(ctx);
        // On s'assure de bien récupérer le contexte de l'app, même si on a récupéré un contexte d'ativity en paramètre
//        ajouteNote("Note 1","Contenu de la note 1");
//        ajouteNote("Autre note","Et un autre contenu");
//        ajouteNote("Petite troisième","On va faire un contenu un peu plus long, pour voir comment ça passe sur tous les affichages. Hopla ! Et même encore un peu plus long histoire de dire. Après tout, normalement on a tout un écran pour l'afficher, donc on est bien large...");
    }





    public void ajouteCocktail(String nom,String image, int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues toAdd = new ContentValues();
        toAdd.put(BDDOpenHelper.COLUMN_NOM,nom);
        toAdd.put(BDDOpenHelper.COLUMN_IMAGE,image);
        toAdd.put(BDDOpenHelper.COLUMN_ID,id);
        db.insert(BDDOpenHelper.TABLE_COCKTAIL,null,toAdd);
        db.close();
        listeFavoris.ajouteCocktail(nom,image,id);
    }

    public int deleteCocktail(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(BDDOpenHelper.TABLE_COCKTAIL,BDDOpenHelper.COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
        db.close();
        for (int i=0;i<listeFavoris.size();++i) {
            Cocktail n=listeFavoris.get(i);
            if (id == n.getId()) {
                listeFavoris.deleteCocktail(i);
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onListFragmentInteraction(Cocktail item) {
        Log.d("testions" , "ok");
    }
}


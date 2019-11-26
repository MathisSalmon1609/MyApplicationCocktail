package com.example.myapplicationcocktail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Favoris extends AppCompatActivity implements FavFragment.OnListFragmentInteractionListener , NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Context ctx;
    BDDOpenHelper dbHelper;
    private ListeCocktails listeFavoris;
    FavFragment favFragment;

    @Override
    protected void onResume() {
        super.onResume();
        favFragment.updateFav();
        drawer.closeDrawer(GravityCompat.START);
    }


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

        NavigationView navigationView = findViewById(R.id.nav_view3);
        navigationView.setNavigationItemSelectedListener(this);

       favFragment = (FavFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.mainFrag_fav);


        listeFavoris = new ListeCocktails(ctx);
         }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("Touch" , String.valueOf(item.getItemId()));
        switch (item.getItemId()){
            case R.id.nav_mes_cocktails:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_accueil:
                Intent intent2 = new Intent(this.getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }




    @Override
    public void onListFragmentInteraction(Cocktail item) {
        Log.d("testions" , "ok");
        Toast.makeText(this, item.getNom() + " - " + item.getId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getApplicationContext(), ViewDetails.class);
        intent.putExtra("id" , item.getId());
        intent.putExtra("name" , item.getNom());
        intent.putExtra("image" , item.getImage());
        startActivity(intent);

    }
}


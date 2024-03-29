package com.example.myapplicationcocktail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class ViewDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    TextView name;
    ImageView imageDetail;

    int idCocktail;
    DetailFrag detailFragment;
    private Cocktail cocktail;
    ListView listView;
    TextView  category;
    TextView alcoholic;
    TextView instruction;
    CheckBox checkBox;
    Context ctx;
    BDDOpenHelper dbHelper;

    @Override
    protected void onResume() {
        super.onResume();
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailFragment = (DetailFrag) getSupportFragmentManager().findFragmentById(R.id.detailFrag);

        setContentView(R.layout.activity_view_details);
        this.name = findViewById(R.id.name_detail);
        this.imageDetail = findViewById(R.id.image_detail);
        this.listView = findViewById(R.id.listIngredients);
        this.category = findViewById(R.id.category_detail);
        this.alcoholic = findViewById(R.id.alcoholic_detail);
        this.instruction = findViewById(R.id.instruction_detail);
        this.checkBox = findViewById(R.id.checkBox);

        ctx = this.getApplicationContext();



        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout2);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        String getName = (String) bd.get("name");
        Log.d("DEBUG" , getName);
        String getImage = (String) bd.get("image");
        int getId = (int) bd.get("id");
        this.cocktail = new Cocktail(getName,getImage,getId);

        name.setText(getName);
        idCocktail = getId;


        dbHelper = new BDDOpenHelper(ctx,"cocktail",null,1);
        /*SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cr = db.query(BDDOpenHelper.TABLE_COCKTAIL,new String[]{BDDOpenHelper.COLUMN_ID},BDDOpenHelper.COLUMN_ID+"=?",new String[] {String.valueOf(idCocktail)},null,null,null);

        if(cr != null) checkBox.setChecked(true);*/

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cr = db.query(BDDOpenHelper.TABLE_COCKTAIL, new String[]{BDDOpenHelper.COLUMN_ID}, BDDOpenHelper.COLUMN_ID+ " =?", new String[] {String.valueOf(idCocktail)}, null, null, null, "1");

        try {
            if (cr.moveToNext() == true) {
                checkBox.setChecked(true);
            }
        }
        finally {
            cr.close();
        }
        db.close();


        Picasso.with(this)
                .load(getImage)
                .into(this.imageDetail);

        URL url = createURLCocktailDetail(idCocktail);

        try {
            new GetCocktailDetail().execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String nameToast = cocktail.getNom();


                if (isChecked == true){             //TO DO ADD IN DATABASE
                    ajouteCocktail(cocktail.getNom(),cocktail.getImage(),cocktail.getId());
                    Toast.makeText(ctx , nameToast + " add to Favorites" , Toast.LENGTH_LONG).show();

                }else{
                    deleteCocktail(cocktail.getId());
                    Toast.makeText(ctx, nameToast + " removed from Favorites" , Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_mes_cocktails:
                Intent intent = new Intent(this.getApplicationContext(),Favoris.class);
                startActivity(intent);
                break;

            case R.id.nav_accueil:
                Intent intent2 = new Intent(this.getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                break;
        }
        return false;
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

    }

    public int deleteCocktail(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(BDDOpenHelper.TABLE_COCKTAIL,BDDOpenHelper.COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
        db.close();
        return -1;
    }



    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public URL createURLCocktailDetail(int id){

        String baseUrl = getString(R.string.web_service_url_detail);
        //Log.d("debuglol" , ingredient);
        try {
            // creation URL pour les cocktails recherchée
            String urlString = baseUrl + "i=" + id;
            // + "&APPID=" + apiKey;
            ;
            return new URL(urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null; // URL mal formée
    }





    public void update (Cocktail item){
        category.setText(item.getCategory());
        alcoholic.setText(item.getAlcoholic());
        instruction.setText(item.getInstruction());
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }



    private class GetCocktailDetail extends AsyncTask<URL,Void, JSONObject> {


        @Override
        protected JSONObject doInBackground(URL... params) {
            InputStream is = null;
            try {
                is = params[0].openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(JSONObject cocktail) {

            getCocktailFromJSON(cocktail);
            Log.d("PLOP9", "print");

        }


        // creation d'objets Cocktail à partir du JSONObject

        private void getCocktailFromJSON(JSONObject forecast) {
            // Log.d("PLOP", forecast.toString());


            try {

                JSONArray arr = forecast.getJSONArray("drinks");


            ArrayList<String> ingredients = new ArrayList<>();  //creattion d une liste avec les ingredient et leur dose
            //String[] ingredients = new String[15];
            //String[] dose = new String[15];
            String request;
            String test;
            boolean continu = true ;
            int index = 0;

            while(continu){
               request = "strIngredient" + (index+1);
               test =  arr.getJSONObject(0).getString(request);
               Log.d("testons" , request + " : " + test);
               if(test.equals("null")){
                   continu = false;
               }else {
                   ingredients.add(test);
                   index++;
               }
            }
            int index2=0;

            while(index2!=(index)){
                request = "strMeasure" + (index2+1);
                test =  arr.getJSONObject(0).getString(request);
                Log.d("testons" , request + " : " + test );
                if(test.equals("null")){
                    index2++;
                }else {
                    ingredients.set(index2 , ingredients.get(index2) + " : " + test);
                    index2++;
                }
            }

            cocktail.setIngedients(ingredients);
            cocktail.setInstruction(arr.getJSONObject(0).getString("strInstructions"));
            cocktail.setAlcoholic(arr.getJSONObject(0).getString("strAlcoholic") );
            cocktail.setCategory(arr.getJSONObject(0).getString("strCategory"));

            index = 0;



                // on met a jour la liste a l'ecran via l Adaptter "arrayAdapter".


                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.listingredients,ingredients);
                listView.setAdapter(arrayAdapter);
                update(cocktail);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        // creation de l'URL du web service  openweathermap.org
        // à partir de la requête de l'utilisateur


    }

}

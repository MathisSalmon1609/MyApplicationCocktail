package com.example.myapplicationcocktail;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AccueilFragment.OnListFragmentInteractionListener {

    private DrawerLayout drawer;

    AccueilFragment accueilFragment;
    RechercheFragment rechercheFragment;
    List<Cocktail> listeCocktails;
    EditText ingredient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        accueilFragment = (AccueilFragment) getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        rechercheFragment = (RechercheFragment)getSupportFragmentManager().findFragmentById(R.id.mainRechercheFrag);
        ingredient = findViewById(R.id.ingredient);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();





    }

    public void Rechercher(View view ){
        String ingredient = this.ingredient.getText().toString();
        URL url = createURLCocktail(ingredient);
        Log.d("debug" , url.toString());
        new GetCocktailInfo().execute(url);
        //printListeConsole();
        accueilFragment.update(listeCocktails);

    }

public void printListeConsole(){
        for (Cocktail c : listeCocktails){
            Log.d("DEBUG" , c.getNom() );
        }




}


    public URL createURLCocktail(String ingredient){
       // String apiKey = getString(R.string.api_key);
        String baseUrl = getString(R.string.web_service_url);
        Log.d("debuglol" , ingredient);
        try {
            // creation URL pour les cocktails recherchée
            String urlString = baseUrl + "i=" + ingredient;
                   // + "&APPID=" + apiKey;
           ;
            return new URL(urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null; // URL mal formée
    }









    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public void onListFragmentInteraction(Cocktail item) {
        //TO DO
    }


    private class GetCocktailInfo extends AsyncTask<URL,Void,JSONObject> {


        @Override
        protected JSONObject doInBackground(URL... params) {
            Log.d("PLOP", "lol");
            HttpURLConnection connection = null;

            try{
                connection = (HttpURLConnection) params[0].openConnection();
                int response = connection.getResponseCode();

                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();

                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {

                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    return new JSONObject(builder.toString());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                connection.disconnect(); // fermeture HttpURLConnection
            }

            return null;
        }

        // traitement de la réponse JSON
        // et mise à jour de la ListView



        @Override
        protected void onPostExecute(JSONObject cocktail) {

            // repeupler la weatherList
            getCocktailFromJSON(cocktail);
        }
    }




    // creation d'objets Cocktail à partir du JSONObject

    private void getCocktailFromJSON(JSONObject forecast) {
        Log.d("PLOP",forecast.toString());

        try {




            JSONArray arr = forecast.getJSONArray("drinks");
            List<Cocktail> list = new ArrayList<Cocktail>();

            for(int i = 0; i < arr.length(); i++){
                list.add(new Cocktail(arr.getJSONObject(i).getString("strDrink") , arr.getJSONObject(i).getString("strDrinkThumb") , arr.getJSONObject(i).getInt("idDrink") ));
            }

            this.listeCocktails = list;

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // creation de l'URL du web service  openweathermap.org
    // à partir de la requête de l'utilisateur







    }



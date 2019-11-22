package com.example.myapplicationcocktail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AccueilFragment.OnListFragmentInteractionListener {

    private DrawerLayout drawer;

    AccueilFragment accueilFragment;
    RechercheFragment rechercheFragment;
    List<Cocktail> listeCocktails;
    EditText ingredient;
    String continu="1";



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

        listeCocktails = new ArrayList<Cocktail>();



    }

    public void Rechercher(View view ){
        String ingredient = this.ingredient.getText().toString();
        URL url = createURLCocktail(ingredient);
        Log.d("debug" , url.toString());
        try {
            new GetCocktailInfo().execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        printListeConsole();
        //accueilFragment.update(listeCocktails);

    }

public void printListeConsole(){
        if (!listeCocktails.isEmpty()) {
            for (Cocktail c : listeCocktails) {
                Log.d("DEBUG", c.getNom() + " " + c.getImage());
            }
        }
        else
            Log.d("print","listevide");

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
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    private class GetCocktailInfo extends AsyncTask<URL,Void,JSONObject> {


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
            Log.d("PLOP9", "lolmdr");
            // repeupler la weatherList
            getCocktailFromJSON(cocktail);
            printListeConsole();
            Log.d("PLOP9", "print");
            accueilFragment.update3(listeCocktails);
        }


        // creation d'objets Cocktail à partir du JSONObject

        private void getCocktailFromJSON(JSONObject forecast) {
           // Log.d("PLOP", forecast.toString());


            try {

                JSONArray arr = forecast.getJSONArray("drinks");
                List<Cocktail> list = new ArrayList<Cocktail>();

                for (int i = 0; i < arr.length(); i++) {
                    list.add(new Cocktail(arr.getJSONObject(i).getString("strDrink"), arr.getJSONObject(i).getString("strDrinkThumb"), arr.getJSONObject(i).getInt("idDrink")));
                }

                listeCocktails = list;
                //printListeConsole();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // creation de l'URL du web service  openweathermap.org
        // à partir de la requête de l'utilisateur


    }

    }



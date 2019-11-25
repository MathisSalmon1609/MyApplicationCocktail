package com.example.myapplicationcocktail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;


public class ViewDetails extends AppCompatActivity {


    TextView name;
    ImageView imageDetail;

    int idCocktail;
    DetailFrag detailFragment;
    Cocktail cocktail;
    ListView listView;
    TextView  category;
    TextView alcoholic;
    TextView instruction;


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

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        String getName = (String) bd.get("name");
        String getImage = (String) bd.get("image");
        int getId = (int) bd.get("id");
        this.cocktail = new Cocktail(getImage,getImage,getId);

        name.setText(getName);
        idCocktail = getId;

        Picasso.with(this)
                .load(getImage)
                .into(this.imageDetail);

        URL url = createURLCocktailDetail(idCocktail);

        try {
            new GetCocktailDetail().execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

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


            ArrayList<String> ingredients = new ArrayList<>();
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



           Log.d("array" , ingredients.toString());

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,ingredients);
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

package com.example.myapplicationcocktail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewDetails extends AppCompatActivity {


    TextView name;
    ImageView imageDetail;
    TextView container;
    int idCocktail;
    DetailFrag detailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailFragment = (DetailFrag) getSupportFragmentManager().findFragmentById(R.id.detailFrag);

        setContentView(R.layout.activity_view_details);
        this.name = findViewById(R.id.name_detail);
        this.container = findViewById(R.id.container_detail);
        this.imageDetail = findViewById(R.id.image_detail);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        String getName = (String) bd.get("name");
        String getImage = (String) bd.get("image");
        int getId = (int) bd.get("id");

        name.setText(getName);
        idCocktail = getId;

        Picasso.with(this)
                .load(getImage)
                .into(this.imageDetail);

    }







    public void update (Cocktail item){
        name.setText(item.getNom());
        Picasso.with(this).load(item.getImage()).into(imageDetail);
        container.setText(item.getDescription());
    }
}

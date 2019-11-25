package com.example.myapplicationcocktail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.os.FileUtils.copy;

public class MyCocktailRecyclerViewAdapter extends RecyclerView.Adapter<MyCocktailRecyclerViewAdapter.ViewHolder > {
    private final ListeCocktails listeCocktails;
    private final AccueilFragment.OnListFragmentInteractionListener mListener;
    private Context ctx;


    public MyCocktailRecyclerViewAdapter(Context context , ListeCocktails listeCocktails, AccueilFragment.OnListFragmentInteractionListener listener) {
        this.ctx = context;
        this.listeCocktails = listeCocktails;
        this.mListener = listener;

    }


    @NonNull
    @Override
    public MyCocktailRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cocktail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCocktailRecyclerViewAdapter.ViewHolder holder, int position) {



       Picasso.with(ctx)
                .load(listeCocktails.get(position).getImage())//listeCocktails.get(position).getImage())
                .into(holder.mImage);

        holder.mItem = listeCocktails.get(position);
        holder.mNom.setText(listeCocktails.get(position).getNom());
        //holder.mImage.setText(listeCocktails.get(position).getImage());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }


        @Override
        public int getItemCount () {
            return listeCocktails.size();
        }




        public void update7(List<Cocktail> liste) {
            this.listeCocktails.update2(liste);

        }








        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public TextView mNom;
            public ImageView mImage;
            public Cocktail mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mNom =  view.findViewById(R.id.nom);
                mImage = (ImageView) view.findViewById(R.id.image);
            }



            @Override
            public String toString() {
                return super.toString() + " '" + mNom.getText() + "'";
            }
        }
    }


package com.example.myapplicationcocktail;

import android.media.Image;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyCocktailRecyclerViewAdapter extends RecyclerView.Adapter<MyCocktailRecyclerViewAdapter.ViewHolder > {
    private final ListeCocktails listeCocktails;
    private final AccueilFragment.OnListFragmentInteractionListener mListener;


    public MyCocktailRecyclerViewAdapter(ListeCocktails listeCocktails, AccueilFragment.OnListFragmentInteractionListener listener) {
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
        holder.mItem = listeCocktails.get(position);
        holder.mNom.setText(listeCocktails.get(position).getNom());
        holder.mImage.setText(listeCocktails.get(position).getImage());
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
            public final View mView;
            public final TextView mNom;
            public final TextView mImage;

            public Cocktail mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                Log.d("hehe"  ,  view.toString());
                mNom =  view.findViewById(R.id.nom);
                mImage =  view.findViewById(R.id.image);
                Log.d("hehe"  , mNom.getText().toString());
            }



            @Override
            public String toString() {
                return super.toString() + " '" + mNom.getText() + "'";
            }
        }
    }


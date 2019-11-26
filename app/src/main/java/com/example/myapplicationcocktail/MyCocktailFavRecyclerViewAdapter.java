package com.example.myapplicationcocktail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCocktailFavRecyclerViewAdapter extends RecyclerView.Adapter<MyCocktailFavRecyclerViewAdapter.ViewHolder > {
    private  ListeCocktails listeCocktails;
    private final FavFragment.OnListFragmentInteractionListener mListener;
    private Context ctx;
    BDDOpenHelper dbHelper;



    public MyCocktailFavRecyclerViewAdapter(Context context , ListeCocktails listeCocktails, FavFragment.OnListFragmentInteractionListener listener) {
        this.ctx = context;
        this.listeCocktails = listeCocktails;
        this.mListener = listener;
        this.dbHelper = new BDDOpenHelper(ctx,"cocktail",null,1);

    }


    @NonNull
    @Override
    public MyCocktailFavRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listeitem_favoris, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCocktailFavRecyclerViewAdapter.ViewHolder holder, int position) {



       Picasso.with(ctx)
                .load(listeCocktails.get(position).getImage())//listeCocktails.get(position).getImage())
                .into(holder.mImage);

        holder.mItem = listeCocktails.get(position);
        holder.mNom.setText(listeCocktails.get(position).getNom());
        //holder.mImage.setText(listeCocktails.get(position).getImage());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String nameToast = holder.mNom.getText().toString();

                listeCocktails.deleteCocktailByID(holder.mItem.getId());
                Toast.makeText(ctx, nameToast + " removed from Favorites" , Toast.LENGTH_SHORT).show();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete(BDDOpenHelper.TABLE_COCKTAIL,BDDOpenHelper.COLUMN_ID+" = ?", new String[]{String.valueOf(holder.mItem.getId())});
                notifyDataSetChanged();
            }
        });

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




        public void update7(ListeCocktails liste) {
            Log.d("UPDATE1" , liste.toString());
            Log.d("UPDATE2" , liste.getListeCocktails().toString());
            this.listeCocktails.setListeCocktails(liste.listeCocktails);
            Log.d("UPDATE3" , liste.getListeCocktails().toString());
            Log.d("UPDATE4" , listeCocktails.toString());
        }








        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public TextView mNom;
            public ImageView mImage;
            public Cocktail mItem;
            public ImageButton imageButton;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mNom =  view.findViewById(R.id.nom_fav);
                mImage = (ImageView) view.findViewById(R.id.image_fav);
                imageButton = (ImageButton) view.findViewById(R.id.checkBoxFav);
            }



            @Override
            public String toString() {
                return super.toString() + " '" + mNom.getText() + "'";
            }
        }
    }


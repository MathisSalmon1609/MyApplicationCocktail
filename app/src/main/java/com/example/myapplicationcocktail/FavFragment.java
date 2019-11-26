

package com.example.myapplicationcocktail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FavFragment extends Fragment {


    private FavFragment.OnListFragmentInteractionListener mListener;
    private MyCocktailFavRecyclerViewAdapter mAdapter;
    private ListeCocktails mListeFav;
    private BDDOpenHelper dbHelper;




    public FavFragment(){


    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        mListeFav = new ListeCocktails(getContext());
        mAdapter = new MyCocktailFavRecyclerViewAdapter(this.getContext(), mListeFav, mListener);
        updateFav();
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoris, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            // if (mListeCocktails == null) mListeCocktails = new ListeCocktails(context);
            // if (mAdapter == null) mAdapter = new MyCocktailRecyclerViewAdapter(mListeCocktails,mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FavFragment.OnListFragmentInteractionListener) {
            mListener = (FavFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void updateFav(){
        mListeFav.listeCocktails.clear();
        dbHelper = new BDDOpenHelper(this.getContext(),"cocktail",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(BDDOpenHelper.TABLE_COCKTAIL, new String[]{BDDOpenHelper.COLUMN_ID ,BDDOpenHelper.COLUMN_NOM, BDDOpenHelper.COLUMN_IMAGE}, null, null, null, null, null);

        try {
            while (cursor.moveToNext()) {
                mListeFav.ajouteCocktail(cursor.getString(1),cursor.getString(2),cursor.getInt(0));
            }
        }
        finally {
            cursor.close();
        }

        db.close();
        mAdapter.update7(mListeFav);
        mAdapter.notifyDataSetChanged();
    }




    public interface OnListFragmentInteractionListener{
        void onListFragmentInteraction (Cocktail item);
    }

}

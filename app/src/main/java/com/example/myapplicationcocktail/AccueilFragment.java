package com.example.myapplicationcocktail;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccueilFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private MyCocktailRecyclerViewAdapter mAdapter;
    private ListeCocktails mListeCocktails;




    public AccueilFragment(){


    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (mListeCocktails == null) mListeCocktails = new ListeCocktails(context);
            if (mAdapter == null) mAdapter = new MyCocktailRecyclerViewAdapter(mListeCocktails,mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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


    public void update(List<Cocktail> liste){
        Log.d("up"  , liste.toString());
        this.mListeCocktails.update(liste);
        mAdapter.notifyDataSetChanged();
    }


    public interface OnListFragmentInteractionListener{
        void onListFragmentInteraction (Cocktail item);
    }

}

package com.example.myapplicationcocktail;

import android.database.sqlite.SQLiteOpenHelper;
        import android.content.Context;
        import android.database.DatabaseErrorHandler;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Build;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.annotation.RequiresApi;

public class BDDOpenHelper extends SQLiteOpenHelper {

    // Il est plus propre de mettre les noms des tables et des colonnes dans des attributs statiques de cette classe. Ç a écvite de faire des fautes de frappe...
    public static final String TABLE_COCKTAIL ="Favoris";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NOM="nom";
    public static final String COLUMN_IMAGE="image";

    public BDDOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BDDOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public BDDOpenHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // On passe dans cette méthode lorsque la base est créée. le paramètre db est donc vide, il faut dcréer les table (la table, dans notre cas)
        String requete =
                "create table "+ TABLE_COCKTAIL +" ( "+
                        COLUMN_ID + " integer primary key , " +
                        COLUMN_NOM + " text not null ," +
                        COLUMN_IMAGE + " text not null " +
                        ") ;";

        db.execSQL(requete);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // POur le moment, on n'a qu'une version de la BDD, donc on ne fait rien dans cette méthode.
        // Si on vévelooppe une V2, et qu'on a a modifier la table, alors il conviendra d'apporter ces changements
        // si et seulement si oldversion=1 et nexVersion=2.
    }
}

package com.example.applicationprojetsergiojerem.exo.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.applicationprojetsergiojerem.exo.database.dao.ExcursionDAO;
import com.example.applicationprojetsergiojerem.exo.database.dao.GuideDAO;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

import java.util.concurrent.Executors;


public abstract class  AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static final String DATABASE_NAME = "ExcursionsDB";

    public abstract GuideDAO guideDAO();
    public abstract ExcursionDAO excursionDAO();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private static volatile AppDatabase instance;

    /**
     * Méthode de base des singleton
     * @param context
     * @return
     */
    public static AppDatabase getInstance(final Context context){
        if (instance == null){
            synchronized (AppDatabase.class){
                if (instance == null){
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return instance;
    }


    /**
     * Crée la base de données avec Google Room
     * @param context
     * @return
     */
    private static AppDatabase buildDatabase(final Context context){
        Log.i(TAG, "Database will be initialized");
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db){
                super.onCreate(db);

                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase database = AppDatabase.getInstance(context);
                    initializeDemoData(database);

                    database.setDatabaseCreated();
                });
            }
        }).build();
    }

    /**
     * Insére des données demo dans la base de données qu'on vient de créer
     * @param database
     */
    public static void initializeDemoData(final AppDatabase database){
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                DatabaseInitializer.populateDatabase(database);
            });
        });
    }

    /**
     * On indique juste que la db est créé avec des données
     * @param context
     */
    private void updateDatabaseCreated(final Context context){
        if (context.getDatabasePath(DATABASE_NAME).exists()){
            Log.i(TAG, "Database initialized");
            setDatabaseCreated();
        }
    }

    /**
     * Change une variable en true pour indiquer que la db est créée
     */
    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    /**
     * Retourne la valeur de la variable qui checke si la db est créée
     * @return true si la db est créée, false sinon
     */
    private LiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    }
}

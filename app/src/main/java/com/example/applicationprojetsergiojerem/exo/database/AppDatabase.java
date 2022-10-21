package com.example.applicationprojetsergiojerem.exo.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

import java.util.concurrent.Executors;

@Database(entities = {Excursion.class, Guide.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static final String DATABASE_NAME = "ExcursionsDB";

    public abstract Guide guide();
    public abstract Excursion excursion();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private static AppDatabase instance;

    private AppDatabase(){

    }

    public AppDatabase getInstance(final Context context){
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


    public static void initializeDemoData(final AppDatabase database){
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                Log.i(TAG, "Wipe database");
                database.guide().deleteAll();
                database.excursion().deleteAll();

                DatabaseInitializer.populateDatabase(database);
            });
        });
    }


    private void updateDatabaseCreated(final Context context){
        if (context.getDatabasePath(DATABASE_NAME).exists()){
            Log.i(TAG, "Database inizialized");
            setDatabaseCreated();
        }
    }


    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private LiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    }
}

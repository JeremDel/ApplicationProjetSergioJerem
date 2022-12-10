package com.example.applicationprojetsergiojerem.exo.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ExcursionLiveData extends LiveData<Excursion> {
    private static final String TAG = "ExcursionLiveData";

    private final DatabaseReference dbReference;
    private final int guide;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ExcursionLiveData(DatabaseReference dbReference){
        this.dbReference = dbReference;
        guide = Integer.parseInt(dbReference.getParent().getParent().getKey());
    }

    @Override
    protected void onActive(){
        Log.d(TAG, "onActive");
        dbReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive(){
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Excursion entity = snapshot.getValue(Excursion.class);
            entity.setId(Integer.parseInt(snapshot.getKey()));
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Can't listen to query " + dbReference, error.toException());
        }
    }
}

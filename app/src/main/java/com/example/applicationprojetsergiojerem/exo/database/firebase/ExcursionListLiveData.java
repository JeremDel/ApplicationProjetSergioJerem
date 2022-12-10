package com.example.applicationprojetsergiojerem.exo.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExcursionListLiveData extends LiveData<List<Excursion>> {
    private final String TAG = "ExcursionListLiveData";
    private final DatabaseReference dbReference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ExcursionListLiveData(DatabaseReference dbReference){
        this.dbReference = dbReference;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        dbReference.addValueEventListener(listener);
    }
    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(toExcursions(snapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Can't listen to query " + dbReference, error.toException());
        }
    }

    private List<Excursion> toExcursions(DataSnapshot snapshot){
        List<Excursion> result = new ArrayList<>();

        for(DataSnapshot ds : snapshot.getChildren()){
            if (ds.getKey() != null){
                Excursion excursion = ds.getValue(Excursion.class);
                result.add(excursion);
            }
        }

        return result;
    }
}

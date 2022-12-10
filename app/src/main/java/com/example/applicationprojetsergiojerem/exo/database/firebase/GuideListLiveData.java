package com.example.applicationprojetsergiojerem.exo.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GuideListLiveData extends LiveData<List<Guide>> {
    private final String TAG = "GuideListLiveData";
    private final DatabaseReference dbReference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public GuideListLiveData(DatabaseReference dbReference){
        this.dbReference = dbReference;
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

    private class MyValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(toGuides(snapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Cannot listen to query " + dbReference, error.toException());
        }
    }

    private List<Guide> toGuides(DataSnapshot snapshot){
        List<Guide> result = new ArrayList<>();

        for (DataSnapshot ds : snapshot.getChildren()){
            if (ds.getKey() != null){
                Guide guide = ds.getValue(Guide.class);
                result.add(guide);
            }
        }

        return result;
    }
}

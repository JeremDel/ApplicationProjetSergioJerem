package com.example.applicationprojetsergiojerem.exo.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class GuideLiveData extends LiveData<Guide> {
    private static final String TAG = "GuideLiveData";

    private final DatabaseReference dbReference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public GuideLiveData(DatabaseReference dbReference){
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

    private class MyValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Guide guide = snapshot.getValue(Guide.class);
            guide.setId(Integer.parseInt(snapshot.getKey()));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Can't listen to query " + dbReference, error.toException());
        }
    }
}

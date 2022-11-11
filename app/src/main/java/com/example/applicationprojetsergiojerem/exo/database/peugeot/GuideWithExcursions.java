package com.example.applicationprojetsergiojerem.exo.database.peugeot;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

public class GuideWithExcursions {
    @Embedded
    public Guide guide;

    @Relation(parentColumn = "id", entityColumn = "guide", entity = Excursion.class)
    public LiveData<Excursion> excursionList;
}

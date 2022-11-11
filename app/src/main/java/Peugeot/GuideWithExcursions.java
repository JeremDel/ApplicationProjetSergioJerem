package Peugeot;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Entities.Excursion;
import Entities.Guide;

public class GuideWithExcursions {
    @Embedded
    public Guide guide;

    @Relation(parentColumn = "id", entityColumn = "guide", entity = Excursion.class)
    public List<Excursion> excursionList;
}

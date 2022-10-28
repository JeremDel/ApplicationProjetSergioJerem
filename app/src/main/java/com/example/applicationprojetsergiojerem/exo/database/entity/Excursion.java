package com.example.applicationprojetsergiojerem.exo.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Guide.class,
                parentColumns = "id",
                childColumns = "guide",
                onDelete = ForeignKey.CASCADE
        )
})
public class Excursion {
    @PrimaryKey
    public int id;
    public int price;

    public float distance;

    public String name;
    public String locations;
    public String difficulty;
    public String picPath;

    @NonNull
    public int guide;

    public Excursion(int price, float distance, String name, String locations, String difficulty, String picPath, int guide){
        this.price = price;
        this.distance = distance;
        this.name = name;
        this.locations = locations;
        this.difficulty = difficulty;
        this.picPath = picPath;
        this.guide = guide;
    }

    // -- Getters --
    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public float getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    public String getLocations() {
        return locations;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getPicPath() {
        return picPath;
    }

    public int getGuide() {
        return guide;
    }
}

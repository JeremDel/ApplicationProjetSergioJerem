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
    private int id;
    private int price;

    private float distance;

    private String name;
    private String locations;
    private String difficulty;
    private String picPath;

    @NonNull
    private int guide;

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

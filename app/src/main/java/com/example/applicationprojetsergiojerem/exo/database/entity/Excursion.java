package com.example.applicationprojetsergiojerem.exo.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Excursion {
    public String id;
    public int price;

    public float distance;

    public String name;
    public String locations;
    public String difficulty;
    public String picPath;

    @NonNull
    public String guide;


    public Excursion(int price, float distance, String name, String locations, String difficulty, String picPath, String guide){
        this.price = price;
        this.distance = distance;
        this.name = name;
        this.locations = locations;
        this.difficulty = difficulty;
        this.picPath = picPath;
        this.guide = guide;
    }

    // -- Getters --
    @Exclude
    public String getId() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getPicPath() {
        return picPath;
    }

    public String getGuide() {
        return guide;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("price", price);
        result.put("distance", distance);
        result.put("name", name);
        result.put("locations", locations);
        result.put("difficulty", difficulty);
        result.put("picPath", picPath);

        return result;
    }

}

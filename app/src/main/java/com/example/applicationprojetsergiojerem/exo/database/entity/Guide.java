package com.example.applicationprojetsergiojerem.exo.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Guide {
    public int id;
    public int phoneNumber;

    public String birthdate;

    public String name;
    public String lastName;
    public String description;
    public String address;
    public String email;
    public String picPath;
    public int guide;

    public Guide(int phoneNumber, String birthdate, String name, String lastName, String description,
                 String address, String email, String picPath, int guide){
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.name = name;
        this.lastName = lastName;
        this.description = description;
        this.address = address;
        this.email = email;
        this.picPath = picPath;
        this.guide = guide;
    }

    // -- Getters --
    @Exclude
    public int getId() {
        return id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicPath() {
        return picPath;
    }

    @Exclude
    public int getGuide() {
        return guide;
    }

    public void setGuide(int guide) {
        this.guide = guide;
    }

    @Override
    public String toString(){
        return name + " " + lastName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("phoneNumber", phoneNumber);
        result.put("birthdate", birthdate);
        result.put("name", name);
        result.put("lastName", lastName);
        result.put("description", description);
        result.put("address", address);
        result.put("email", email);
        result.put("picPath", picPath);

        return result;
    }


}

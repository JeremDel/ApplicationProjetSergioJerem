package com.example.applicationprojetsergiojerem.exo.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity //manque tablename + primaryKey
public class Guide {
    @PrimaryKey
    public int id;
    public int phoneNumber;

    public String birthdate;

    public String name;
    public String lastName;
    public String description;
    public String address;
    public String email;
    public String picPath;

    //Guide vide ?

    //obliger certaines valeures à être non null ?
    public Guide(int phoneNumber, String birthdate, String name, String lastName, String description,
                 String address, String email, String picPath){
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.name = name;
        this.lastName = lastName;
        this.description = description;
        this.address = address;
        this.email = email;
        this.picPath = picPath;
    }

    //pas de setter ?

    // -- Getters --
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

    //pas de equals ?
}

package com.example.applicationprojetsergiojerem.exo.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
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

    public String getPicPath() {
        return picPath;
    }
}

package com.example.applicationprojetsergiojerem.exo.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Guide {
    public String id;
    public int phoneNumber;

    public String birthdate;

    public String name;
    public String lastName;
    public String description;
    public String address;
    public String email;
    public String picPath;

    public Guide(){}
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
    @Exclude
    public String getId() {
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

    public void setId(String id){
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

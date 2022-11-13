package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Guide {
    @PrimaryKey
    private int id;
    private int phoneNumber;

    private Date birthdate;

    private String name;
    private String lastName;
    private String description;
    private String address;
    private String email;
    private int telephone;

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    private String picPath;

    public Guide(int phoneNumber, Date birthdate, String name, String lastName, String description,
                 String address, String email, int telephone, String picPath){
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.name = name;
        this.lastName = lastName;
        this.description = description;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.picPath = picPath;
    }

    // -- Getters --
    public int getId() {
        return id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public Date getBirthdate() {
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

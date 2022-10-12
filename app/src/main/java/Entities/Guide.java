package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Guide {
    @PrimaryKey
    public int id;
    public int phoneNumber;

    public Date birthdate;

    public String name;
    public String lastName;
    public String description;
    public String address;
    public String email;
}

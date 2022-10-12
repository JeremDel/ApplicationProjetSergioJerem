package Entities;

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

    @NonNull
    public int guide;
}

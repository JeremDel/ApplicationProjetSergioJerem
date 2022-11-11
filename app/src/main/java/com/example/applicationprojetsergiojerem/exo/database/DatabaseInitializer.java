package com.example.applicationprojetsergiojerem.exo.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

import java.util.Date;

public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitilizer";

    public static void populateDatabase(final AppDatabase database){
        Log.i(TAG, "Insert demo data");
        PopulateDbAsync task = new PopulateDbAsync(database);
        task.execute();
    }

    private static void addGuide(final AppDatabase database, final String name, final String lastName, final String description, final String address, final String email,
                                 final String picPath, final int phoneNumber, final String birthDate, final String userName, final String password){
        Guide guide = new Guide(phoneNumber, birthDate, name, lastName, description, address, email, picPath, userName, password);
        database.guideDAO().insert(guide);
    }

    private static void addExcursion(final AppDatabase database, final int price, final float distance, final String name, final String locations, final String difficulty,
                                     final String picPath, final int guideId){
        Excursion excursion = new Excursion(price, distance, name, locations, difficulty, picPath, guideId);
        database.excursionDAO().insert(excursion);
    }


    private static void populateWithTestData(AppDatabase database){
        database.guideDAO().deleteAll();

        addGuide(database, "Jean", "Bonvin", "I have always been very passionate about the mountains. During my various excursions. I learned to know the nature and its inhabitants. The marmots consider me now as one of theirs and do not hesitate to guide me on unknown paths by most of the Valaisans." +
                        "With me, you will be able to say that you will have lived absolutely unique experiences.", "Route des joyeux lurons 23",
                "3pecial@gmail.com", "walserguide.jpg", 761857496, "11.12.1982", "petitePerruche", "Argh88!");

        addGuide(database, "Michael", "Ritz", "I have lived in these mountains since I was a child. My friends call me the flying chamois because I am fast and slender. I would be happy to show you my favorite raclette spots. " +
                        "Hang on, because with me, there will be sport.)", "Avenue des bouquiniers 18",
                "tiptop@outlook.com", "ritz.jpg", 768125594, "23.2.1970", "ObiWan", "HelloThere");

        addGuide(database, "George", "Derivaz", "Want to think about something other than your IT projects that make you sick? Then I'm here to help you! I've been exploring the Alps for 20 years and nothing has any secrets for me anymore. " +
                        "I am able to locate a Dahu at more than 12 km. I am the master of the excursion.", "Route des crapaux 14",
                "WeissWein@gmail.com", "derivaz.jpg", 784825169, "24.12.1965", "gentilBonhomme", "Dahmer69");


        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        addExcursion(database, 330, 110, "Tour des Muverans", "Bex to Ovronnaz", "Medium", "@+id/imageMuveran", 1);

        addExcursion(database, 615, 205, "Tour Walser", "Conches", "Hard", "@+id/imageWalser", 2);

        addExcursion(database, 425, 81, "Les 7 lacs", "Bellwald", "Medium", "@drawable/lacs", 3);


    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase database;

        PopulateDbAsync(AppDatabase database){
            this.database = database;
        }

        @Override
        protected Void doInBackground(final Void... params){
            populateWithTestData(database);
            return null;
        }
    }
}

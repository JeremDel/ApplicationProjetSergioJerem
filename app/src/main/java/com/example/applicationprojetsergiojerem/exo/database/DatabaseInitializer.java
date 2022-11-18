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
                                 final String picPath, final int phoneNumber, final String birthDate){
        Guide guide = new Guide(phoneNumber, birthDate, name, lastName, description, address, email, picPath);
        database.guideDAO().insert(guide);
    }

    private static void addExcursion(final AppDatabase database, final int price, final float distance, final String name, final String locations, final String difficulty,
                                     final String picPath, final int guideId){
        Excursion excursion = new Excursion(price, distance, name, locations, difficulty, picPath, guideId);
        database.excursionDAO().insert(excursion);
    }


    private static void populateWithTestData(AppDatabase database){
        database.excursionDAO().deleteAll();
        database.guideDAO().deleteAll();


        addGuide(database, "Storm", "Front", "Il faut respecter l'opinion des autres ;)", "Route des Delphins 420",
                "eineBierBitte@gmail.com", "https://mlpnk72yciwc.i.optimole.com/cqhiHLc.IIZS~2ef73/w:350/h:350/q:75/rt:fill/g:ce/https://bleedingcool.com/wp-content/uploads/2020/09/theboysstormfront-1-350x350.jpg", 784825169, "24.12.1905");

        addGuide(database, "Jean", "Néo-Skours", "Ma maman dit toujours que je suis très spécial :)", "Route de l'Avenir 123",
                "3pecial@gmail.com", "https://i.ytimg.com/vi/v1mxMtr8Mws/mqdefault.jpg", 761857496, "11.12.2003");

        addGuide(database, "Barry", "Not-Allen-Si-Jamais", "Ma maman dit toujours que je suis un bon garçon :))", "Avenue des doggos 455",
                "woofPasUnChienWoof@outlook.com", "https://www.dogzone.com/images/breeds/st-bernard.jpg", 768125594, "23.2.1970");

        addGuide(database, "Soldier", "Boy", "Si tu veux être heureux, ne te marie jamais à une belle femme", "Avenue de Lennil Boss 79",
                "iEnjoyEatingCars@yahoo.com", "https://mlpnk72yciwc.i.optimole.com/cqhiHLc.IIZS~2ef73/w:350/h:350/q:75/rt:fill/g:ce/https://bleedingcool.com/wp-content/uploads/2022/06/Soldier-Boy-_-Guest-Appearance-on-Solid-Gold-1-33-screenshot-350x350.jpg", 792871056, "20.04.1875");


        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addExcursion(database, 45, 13.5f, "Tour du Mort D'or", "De Bex à Lavey (?)", "Difficile", "mortDor.png", 1);

        addExcursion(database, 169, 50.2f, "Marathon à la Norwegian Regaetton", "Du Mont Blanc aux Alpes", "Difficile", "rega.png", 2);

        addExcursion(database, 25, 5.9f, "Tour des monarques", "De Sierre à... Sierre aussi", "Facile", "filetmignons.png", 4);

        addExcursion(database, 0, 350.0f, "Promenade Germanique", "Tournée par Zurich centre ville. En raquettes, oui.", "Facile", "doucheRelaxante.png", 3);

        addExcursion(database, 70, 20.0f, "Marche du Saint-Bernard", "Du Petit Saint-Bernard au Grand Saint-Bernard", "Moyenne", "bethoven.png", 2);

        addExcursion(database, 35, 136.25f, "Marche Boréale ou jsp", "Du Simplon au Singleton", "Difficile", "badPun.png", 1);

        addExcursion(database, 10, 10.7f, "Tour des déceptions", "", "Moyenne", "mirror.png", 4);

        addExcursion(database, 60, 35.0f, "Marche des loups", "Du Mont Blanc au Mont Noir ou jsp", "Moyenne", "garouCLoup.png", 3);
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

package com.example.applicationprojetsergiojerem.exo.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

import java.util.Date;

public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitilizer";

    /**
     * Log + appel de la méthode qui insère des données démo dans la db
     * @param database
     */
    public static void populateDatabase(final AppDatabase database){
        Log.i(TAG, "Insert demo data");
        PopulateDbAsync task = new PopulateDbAsync(database);
        task.execute();
    }

    /**
     * Ajoute un guide dans la db
     * @param database Database où insérer le guide
     * @param name Nom du guide
     * @param lastName Nom de famille du guide
     * @param description Description du guide
     * @param address Adresse du guide
     * @param email Email du guide
     * @param picPath URL de l'image choisie pour le guide
     * @param phoneNumber Numéro de natel du guide
     * @param birthDate Date de naissance du guide
     */
    private static void addGuide(final AppDatabase database, final String name, final String lastName, final String description, final String address, final String email,
                                 final String picPath, final int phoneNumber, final String birthDate){
        Guide guide = new Guide(phoneNumber, birthDate, name, lastName, description, address, email, picPath);
        database.guideDAO().insert(guide);
    }

    /**
     * Ajoute une excursion dans la db
     * @param database Database où insérer le guide
     * @param price Prix de l'excursion
     * @param distance Distance de l'excursion EN KM
     * @param name Nom de l'excursion
     * @param locations Emplacements de l'excursion (FROM ... TO ...)
     * @param difficulty Difficulté de l'excursion (pas du tout subjectif...)
     * @param picPath URL de l'image choisie pour l'excursion
     * @param guideId Id du guide responsable de l'excursion
     */
    private static void addExcursion(final AppDatabase database, final int price, final float distance, final String name, final String locations, final String difficulty,
                                     final String picPath, final int guideId){
        Excursion excursion = new Excursion(price, distance, name, locations, difficulty, picPath, guideId);
        database.excursionDAO().insert(excursion);
    }


    /**
     * Efface tout le contenu de la db, puis ajoute des données demo
     * @param database Database à gérer
     */
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

        addExcursion(database, 45, 13.5f, "Tour du Mort D'or", "De Bex à Lavey (?)", "Difficile", "https://www.mexicoescultura.com/galerias/actividades/principal/elretornoderey_900.jpg", 1);

        addExcursion(database, 169, 50.2f, "Marathon à la Norwegian Regaetton", "Du Mont Blanc aux Alpes", "Difficile", "https://images.fineartamerica.com/images-medium-large-5/on-the-innominata-ridge-mont-blanc-jonathan-griffith.jpg", 2);

        addExcursion(database, 25, 5.9f, "Tour des monarques", "De Sierre à... Sierre aussi", "Facile", "https://myalpx.com/user/pages/blog/secrets-de-sommelier-8-vins-suisses-dexception-a-moins-de-50-chf/alpx-valais-secrets-de-sommelier-8-vins-suisses-dexception-a-moins-de-50-chf.jpg", 4);

        addExcursion(database, 0, 350.0f, "Promenade Germanique", "Tournée par Zurich centre ville. En raquettes, oui.", "Facile", "https://cheapfirstclass.com/wp-content/uploads/2014/06/CheapbusinessclassflightstoZurich.jpg", 3);

        addExcursion(database, 70, 20.0f, "Marche du Saint-Bernard", "Du Petit Saint-Bernard au Grand Saint-Bernard", "Moyenne", "https://www.nolimitsexperience.ch/images/activites/Hospice_St-Bernard.jpg", 2);

        addExcursion(database, 35, 136.25f, "Marche Boréale ou jsp", "Du Simplon au Singleton", "Difficile", "https://dziedava.lv/daba/rucavas_pag/ziemelblazma1_251011.jpg", 1);

        addExcursion(database, 10, 10.7f, "Tour des déceptions", "", "Moyenne", "https://rabbitsblack.com/wp-content/uploads/2012/02/logo-900x350.jpg", 4);

        addExcursion(database, 60, 35.0f, "Marche des loups", "Du Mont Blanc au Mont Noir ou jsp", "Moyenne", "https://www.dangerousroads.org/images/stories/__Roads00000bt/Col%20du%20Pillon0.jpg", 3);
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase database;

        /**
         * Constructeur
         * @param database Database à gérer
         */
        PopulateDbAsync(AppDatabase database){
            this.database = database;
        }

        /**
         * Appelle les méthodes pour insérer les données démo
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(final Void... params){
            populateWithTestData(database);
            return null;
        }
    }
}

package com.example.applicationprojetsergiojerem.exo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.applicationprojetsergiojerem.R;

import android.widget.Toolbar;

import com.example.applicationprojetsergiojerem.exo.ui.excursion.ExpeditionActivity;
import com.example.applicationprojetsergiojerem.exo.ui.guides.GuideList;
import com.example.applicationprojetsergiojerem.exo.ui.settings.Aboutus;
import com.example.applicationprojetsergiojerem.exo.ui.settings.settings;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {


    private Button bton_expedition, btnGuides;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    /**
     * Déploiement du menu latéral en cas de sélection.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Création de la page principale de l'application.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);

        }else{
            setTheme(R.style.AppTheme);
        }


        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);



        //bouton pour passer de la fenêtre de titre à la fenête expédition
        bton_expedition = (Button)findViewById(R.id.buttonExpedition);

        btnGuides = (Button)findViewById(R.id.guides_button);

        bton_expedition.setOnClickListener(new View.OnClickListener() {
            /**
             * Permet d'aller sur la page de la liste des expéditions.
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpeditionActivity.class);
                startActivity(intent);
            }
        });

        //passer du menu principal à la page des guides

        /**
         * Permet d'aller sur la page de la liste de guides.
         */
        btnGuides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GuideList.class);
                startActivity(intent);
            }
        });

        //mise en place du menu qui se déploit latéralement

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            /**
             * Gère la réaction des différents boutons présents dans le menu latéral.
             * @param item
             * @return
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.about_us:
                        Intent intent22 = new Intent(MainActivity.this, Aboutus.class);
                        startActivity(intent22);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_users:
                        Intent intent2 = new Intent(MainActivity.this, Login.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_setting:
                        Intent intent3 = new Intent(MainActivity.this, settings.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.excursionFast:
                        Intent intent4 = new Intent(MainActivity.this, ExpeditionActivity.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.guideFast:
                        Intent intent5 = new Intent(MainActivity.this, GuideList.class);
                        startActivity(intent5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

                return true;
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
    }


    /**
     * Permet de quitter l'application.
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Voulez-vous quitter l'app ?");
        alertDialog.setCancelable(false);
        //alertDialog.setMessage("");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> finish());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Non", (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }

}
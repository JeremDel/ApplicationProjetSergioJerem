package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

//import com.example.applicationprojetsergiojerem.Login;
import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.ui.settings.settings;

public class BaseActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";

    protected FrameLayout frameLayout;
    protected DrawerLayout drawerLayout;

    protected static int position;

    /**
     * Affiche le menu latéral déployable.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);

        }else{
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        toggle.syncState();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    /**
     * Permet de fermer le menu.
     */
    @Override
    public void onBackPressed(){
        // If menu is open, close it and return
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        // Else just perform the back action
        BaseActivity.position = 0;
        super.onBackPressed();
    }

    /**
     * Appel le menu via le menu principal.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    /**
     * Gère la sélection des éléments présents dans le menu.
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public void logout(){
        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
        editor.remove(BaseActivity.PREFS_USER);
        editor.apply();

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}

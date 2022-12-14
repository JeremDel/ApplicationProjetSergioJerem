package com.example.applicationprojetsergiojerem.exo.ui.excursion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.async.ImageLoadTask;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.ui.BaseActivity;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import java.util.List;

import com.example.applicationprojetsergiojerem.exo.viewmodel.excursion.ExcursionListViewModel;
import com.example.applicationprojetsergiojerem.exo.viewmodel.excursion.ExcursionViewModel;
import com.example.applicationprojetsergiojerem.exo.viewmodel.guide.GuideListViewModel;
import com.example.applicationprojetsergiojerem.exo.viewmodel.guide.GuideViewModel;

public class EditExcursionActivity extends BaseActivity {

    private Excursion excursion;
    private boolean isEditMode;
    private Toast toast;

    private EditText etPrice, etDistance, etName, etLocations, etUrl;
    private TextView tvCurrentGuide;
    private Button btnSave, btnTestImg;
    private ImageButton ibtnDelete;
    private ImageView ivImage;

    private Spinner guideSpinner, difficultySpinner;

    private ExcursionViewModel viewModel;
    private Guide guide;

    private List<Guide> guides;

    private int creationGuideId = -1;

    /**
     * Création de la page d'édition (ou d'ajout) d'excursion.
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
        setContentView(R.layout.activity_edit_expedition);

        initiateView();

        btnSave.setOnClickListener(view -> {
            saveChanges(etPrice.getText().toString(), etDistance.getText().toString(), etName.getText().toString(),
                    etLocations.getText().toString(), difficultySpinner.getSelectedItem().toString(), etUrl.getText().toString());
            onBackPressed();
            toast.show();
        });

        int id = getIntent().getIntExtra("excursionId", -1);
        if (id == -1){
            setTitle(getString(R.string.title_activity_create_excursion));
            toast = Toast.makeText(this, getString(R.string.excursion_created), Toast.LENGTH_LONG);
            isEditMode = false;
        } else {
            setTitle(getString(R.string.title_activity_edit_excursion));
            toast = Toast.makeText(this, getString(R.string.excursion_editted), Toast.LENGTH_LONG);
            isEditMode = true;
        }

        if (isEditMode){
            ibtnDelete.setOnClickListener(view -> {
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setTitle("Are you sure you want to delete this excursion?");
                ad.setCancelable(false);
                ad.setMessage("This operation is not reversible. Once you deleted the excursion there is no way to get it back.");
                ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", ((dialog, which) -> deleteExcursion()));
                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "No", ((dialog, which) -> ad.dismiss()));

                ad.show();
            });
        }

        ExcursionViewModel.Factory factory = new ExcursionViewModel.Factory(getApplication(), getIntent().getIntExtra("excursionId", -1));
        viewModel = new ViewModelProvider(new ViewModelStore(), factory).get(ExcursionViewModel.class);
        if (isEditMode){
            viewModel.getExcursion().observe(this, excursionEntity -> {
                if (excursionEntity != null){
                    excursion = excursionEntity;
                    updateContent();
                }
            });
        }
    }

    /**
     * Suppression de l'excursion précédente.
     */
    private void deleteExcursion(){
        Toast deleteToast = Toast.makeText(this, "Excursion deleted", Toast.LENGTH_LONG);

        ExcursionListViewModel.Factory factory = new ExcursionListViewModel.Factory(getApplication());
        ExcursionListViewModel excursions = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(ExcursionListViewModel.class);
        excursions.deleteExcursion(excursion, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.i("EDIT MODE", "Excursion deleted");
                deleteToast.show();
                onBackPressed();
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i("EDIT MODE", "Could not delete excursion", exception);
            }
        });
    }

    /**
     * Récupération des informations de l'excursion de base.
     */
    private void initiateView(){
        etPrice = findViewById(R.id.etPrice);
        etDistance = findViewById(R.id.etDistance);
        etName = findViewById(R.id.etName);
        etLocations = findViewById(R.id.etLocations);
        etUrl = findViewById(R.id.etUrl);

        difficultySpinner = findViewById(R.id.spinDifficulty);
        guideSpinner = findViewById(R.id.spGuideSelection);

        ArrayAdapter<CharSequence> diffAdapter = ArrayAdapter.createFromResource(this, R.array.difficulties, android.R.layout.simple_spinner_item);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(diffAdapter);

        tvCurrentGuide = findViewById(R.id.tvCurrentGuide);

        btnSave = findViewById(R.id.btnSave);
        btnTestImg = findViewById(R.id.btnTestImg);
        ibtnDelete = findViewById(R.id.btnDelete);

        ivImage = findViewById(R.id.ivImage);

        addSpinner();

        btnTestImg.setOnClickListener(View -> {
            if (! etUrl.getText().toString().equals(""))
                new ImageLoadTask(etUrl.getText().toString(), ivImage).execute();
            else
                Toast.makeText(this, "First you have to specify an URL!!", Toast.LENGTH_LONG).show();
        });
    }

    /**
     * Possibilité de modifier les infos du formulaire.
     */
    private void updateContent(){
        if (excursion != null){
            etPrice.setText(String.valueOf(excursion.getPrice()));
            etDistance.setText(String.valueOf(excursion.getDistance()));
            etName.setText(excursion.getName());
            etLocations.setText(excursion.getLocations());
            etUrl.setText(excursion.getPicPath());

            new ImageLoadTask(excursion.getPicPath(), ivImage).execute();

            int position = -1;
            switch (excursion.getDifficulty()){
                case "Easy":
                    position = 0;
                    break;

                case "Medium":
                    position = 1;
                    break;

                case "Hard":
                    position = 2;
                    break;
            }

            difficultySpinner.setSelection(position);

            GuideViewModel.Factory factory = new GuideViewModel.Factory(getApplication(), excursion.getGuide());
            GuideViewModel guideViewModel = new ViewModelProvider(new ViewModelStore(), factory).get(GuideViewModel.class);
            guideViewModel.getGuide().observe(this, guideEntity -> {
                if (guideEntity != null){
                    this.guide = guideEntity;
                    tvCurrentGuide.setText("Current guide: " + guide.getName() + " " + guide.getLastName());
                }
            });
        }
    }

    /**
     * Ajout des menus déroulants
     */
    private void addSpinner(){
        GuideListViewModel.Factory factory1 = new GuideListViewModel.Factory(getApplication());
        GuideListViewModel guidesVM = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory1).get(GuideListViewModel.class);
        guidesVM.getGuides().observe(this, guideEntities -> {
            if (guideEntities != null){
                guides = guideEntities;

                ArrayAdapter<Guide> adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, guides);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                guideSpinner.setAdapter(adapter1);

                if (getIntent().getIntExtra("excursionId", -1) == -1)
                    guideSpinner.setSelection(0);

                guideSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        updateExcursionGuide();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    /**
     * Mise en place de nouveaux guides
     */
    private void updateExcursionGuide(){
        Guide chosenGuide = (Guide) guideSpinner.getSelectedItem();

        if (isEditMode)
            excursion.setGuide(chosenGuide.id);
        else
            creationGuideId = chosenGuide.id;

        tvCurrentGuide.setText("Current guide: " + chosenGuide);
    }

    /**
     * Enregistrer toutes les nouvelles informations.
     * @param price
     * @param distance
     * @param name
     * @param locations
     * @param difficulty
     * @param picPath
     */
    private void saveChanges(String price, String distance, String name, String locations, String difficulty, String picPath){
        if (isEditMode){
            excursion.setPrice(Integer.parseInt(price));
            excursion.setDistance(Float.parseFloat(distance));
            excursion.setName(name);
            excursion.setLocations(locations);
            excursion.setDifficulty(difficulty);
            excursion.setPicPath(picPath);

            viewModel.updateAccount(excursion, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d("TAG - Edit excursion", "Succesfully updated excursion " + excursion.getId());
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.d("TAG - Edit excursion", "There was an error while updating the excursion " + excursion.getId(), exception);
                }
            });
        } else {
            double priceD = Double.parseDouble(price);
            int priceCorrected = (int) priceD;

            double distanceD = Double.parseDouble(distance);
            float distanceRight = (float)distanceD;

            Excursion newExcursion = new Excursion(priceCorrected, distanceRight, name, locations, difficulty, picPath, creationGuideId);
            viewModel.createExcursion(newExcursion, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d("TAG - Create excursion", "Succesfully created excursion");
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.d("TAG - Create excursion", "There was an error while creating a new excursion", exception);
                }
            });
        }
    }
}

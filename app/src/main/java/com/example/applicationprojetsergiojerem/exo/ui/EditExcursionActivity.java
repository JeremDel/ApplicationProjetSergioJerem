package com.example.applicationprojetsergiojerem.exo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import java.util.ArrayList;
import java.util.List;

import viewmodel.excursion.ExcursionViewModel;
import viewmodel.guide.GuideListViewModel;
import viewmodel.guide.GuideViewModel;

public class EditExcursionActivity extends BaseActivity {
    private Excursion excursion;
    private boolean isEditMode;
    private Toast toast;

    private EditText etPrice, etDistance, etName, etLocations;
    private TextView tvCurrentGuide;
    private Button btnSave;

    private Spinner guideSpinner, difficultySpinner;

    private ExcursionViewModel viewModel;
    private Guide guide;

    private List<Guide> guides;
    private ArrayList<String> guideNames = new ArrayList<>();

    private int creationGuideId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expedition);

        initiateView();

        btnSave.setOnClickListener(view -> {
            saveChanges(etPrice.getText().toString(), etDistance.getText().toString(), etName.getText().toString(),
                    etLocations.getText().toString(), difficultySpinner.getSelectedItem().toString());
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

    private void initiateView(){
        etPrice = findViewById(R.id.etPrice);
        etDistance = findViewById(R.id.etDistance);
        etName = findViewById(R.id.etName);
        etLocations = findViewById(R.id.etLocations);

        difficultySpinner = findViewById(R.id.spinDifficulty);
        guideSpinner = findViewById(R.id.spGuideSelection);

        ArrayAdapter<CharSequence> diffAdapter = ArrayAdapter.createFromResource(this, R.array.difficulties, android.R.layout.simple_spinner_item);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(diffAdapter);

        tvCurrentGuide = findViewById(R.id.tvCurrentGuide);

        btnSave = findViewById(R.id.btnSave);

        addSpinner();
    }

    private void updateContent(){
        if (excursion != null){
            etPrice.setText(String.valueOf(excursion.getPrice()));
            etDistance.setText(String.valueOf(excursion.getDistance()));
            etName.setText(excursion.getName());
            etLocations.setText(excursion.getLocations());

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

    private void addSpinner(){
        GuideListViewModel.Factory factory1 = new GuideListViewModel.Factory(getApplication());
        GuideListViewModel guidesVM = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory1).get(GuideListViewModel.class);
        guidesVM.getGuides().observe(this, guideEntities -> {
            if (guideEntities != null){
                guides = guideEntities;

                ArrayAdapter<Guide> adapter1 = new ArrayAdapter<Guide>(getApplicationContext(), android.R.layout.simple_spinner_item, guides);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                guideSpinner.setAdapter(adapter1);

                if (getIntent().getIntExtra("excursionId", -1) == -1)
                    guideSpinner.setSelection(1);

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

    private void updateExcursionGuide(){
        Guide chosenGuide = (Guide) guideSpinner.getSelectedItem();

        if (isEditMode)
            excursion.setGuide(chosenGuide.id);
        else
            creationGuideId = chosenGuide.id;

        tvCurrentGuide.setText("Current guide: " + chosenGuide);
    }

    private void saveChanges(String price, String distance, String name, String locations, String difficulty){
        if (isEditMode){
            excursion.setPrice(Integer.parseInt(price));
            excursion.setDistance(Float.parseFloat(distance));
            excursion.setName(name);
            excursion.setLocations(locations);
            excursion.setDifficulty(difficulty);

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
            String priceBase = price.toString();
            double priceD = Double.parseDouble(priceBase);
            int priceCorrected = (int) priceD;

            String distanceBase = distance.toString();
            double distanceD = Double.parseDouble(distanceBase);
            int distanceRight = (int)distanceD;

            Excursion newExcursion = new Excursion(priceCorrected, distanceRight, name, locations, difficulty, "", creationGuideId); // TODO Change the hardcoded guide
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

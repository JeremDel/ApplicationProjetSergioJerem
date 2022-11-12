package com.example.applicationprojetsergiojerem.exo.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import viewmodel.excursion.ExcursionViewModel;

public class EditExcursionActivity extends BaseActivity {
    private Excursion excursion;
    private boolean isEditMode;
    private Toast toast;

    private EditText etPrice, etDistance, etName, etLocations, etDiffculty;
    private Button btnSave;

    private ExcursionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_expedition, frameLayout);

        initiateView();

        btnSave.setOnClickListener(view -> {
            saveChanges(etPrice.getText().toString(), etDistance.getText().toString(), etName.getText().toString(),
                    etLocations.getText().toString(), etDiffculty.getText().toString());
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
        viewModel = new ViewModelProvider(this).get(ExcursionViewModel.class);
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
        etDiffculty = findViewById(R.id.etDifficulty);

        btnSave = findViewById(R.id.btnSave);
    }

    private void updateContent(){
        if (excursion != null){
            etPrice.setText(excursion.getPrice());
            etDistance.setText("" + excursion.getDistance());
            etName.setText(excursion.getName());
            etLocations.setText(excursion.getLocations());
            etDiffculty.setText(excursion.getDifficulty());
        }
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
            Excursion newExcursion = new Excursion(Integer.parseInt(price), Float.parseFloat(distance), name, locations, difficulty, "", 1); // TODO Change the hardcoded guide
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

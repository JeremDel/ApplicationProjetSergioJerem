package com.example.applicationprojetsergiojerem.exo.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;

import viewmodel.excursion.ExcursionViewModel;

public class ExpeditionDetails extends BaseActivity {
    private Excursion excursion;
    private TextView tvPrice, tvDistance, tvName, tvLocations, tvDifficulty;

    private ExcursionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_expedition_details, frameLayout);

        int id = getIntent().getIntExtra("excursionId", -1);
        initiateView();

        ExcursionViewModel.Factory factory = new ExcursionViewModel.Factory(getApplication());
        viewModel = new ViewModelProvider(this).get(ExcursionViewModel.class);
        viewModel.getExcursion().observe(this, excursionEntity -> {
            if (excursionEntity != null){
                excursion = excursionEntity;
                updateContent();
            }
        });
    }

    private void initiateView(){
        tvPrice = findViewById(R.id.tvPrice);
        tvDistance = findViewById(R.id.tvDistance);
        tvName = findViewById(R.id.tvName);
        tvLocations = findViewById(R.id.tvLocations);
        tvDifficulty = findViewById(R.id.tvDifficulty);
    }

    private void updateContent(){
        if (excursion != null){
            setTitle(excursion.getName());

            tvPrice.setText(excursion.getPrice());
            tvDistance.setText("" + excursion.getDistance());
            tvName.setText(excursion.getName());
            tvLocations.setText(excursion.getLocations());
            tvDifficulty.setText(excursion.getDifficulty());
        }
    }
}

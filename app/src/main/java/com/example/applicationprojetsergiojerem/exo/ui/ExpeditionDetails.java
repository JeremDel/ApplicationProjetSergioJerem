package com.example.applicationprojetsergiojerem.exo.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

import viewmodel.excursion.ExcursionViewModel;
import viewmodel.guide.GuideViewModel;

public class ExpeditionDetails extends BaseActivity {
    private Excursion excursion;
    private Guide guide;
    private TextView tvPrice, tvDistance, tvName, tvLocations, tvDifficulty;
    private TextView tvExcursionGuideName, tvExcursionGuideLastName, tvExcursionGuideEmail, tvExcursionGuidePhone;

    private ExcursionViewModel viewModel;
    private GuideViewModel gViewModel;

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

        GuideViewModel.Factory gFactory = new GuideViewModel.Factory(getApplication(), excursion.getGuide());
        gViewModel = new ViewModelProvider(this).get(GuideViewModel.class);
        gViewModel.getGuide().observe(this, guideEntity -> {
            if (guideEntity != null){
                guide = guide;
                updateGuide();
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

    private void updateGuide(){
        if (guide != null){
            tvExcursionGuideName.setText(guide.getName());
            tvExcursionGuideLastName.setText(guide.getLastName());
            tvExcursionGuideEmail.setText(guide.getEmail());
            tvExcursionGuidePhone.setText(guide.getPhoneNumber());
        }
    }
}

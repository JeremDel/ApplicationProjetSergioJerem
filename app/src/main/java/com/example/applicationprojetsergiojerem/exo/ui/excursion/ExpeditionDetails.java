package com.example.applicationprojetsergiojerem.exo.ui.excursion;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.async.ImageLoadTask;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.ui.BaseActivity;

import com.example.applicationprojetsergiojerem.exo.viewmodel.excursion.ExcursionViewModel;
import com.example.applicationprojetsergiojerem.exo.viewmodel.guide.GuideViewModel;

public class ExpeditionDetails extends BaseActivity {
    private Excursion excursion;
    private Guide guide;
    private int guideId;
    private TextView tvPrice, tvDistance, tvName, tvLocations, tvDifficulty;
    private TextView tvExcursionGuideName, tvExcursionGuideLastName, tvExcursionGuideEmail, tvExcursionGuidePhone;
    private ImageView ivImage, ivGuide;

    private ExcursionViewModel viewModel;
    private GuideViewModel gViewModel;


    /**
     * Création de la page détaillant les infos précises de l'expédition.
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
        setContentView(R.layout.activity_expedition_details);

        int id = getIntent().getIntExtra("excursionID", -1);
        initiateView();

        ExcursionViewModel.Factory factory = new ExcursionViewModel.Factory(getApplication(), id);
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(ExcursionViewModel.class);
        viewModel.getExcursion().observe(this, excursionEntity -> {
            if (excursionEntity != null){
                excursion = excursionEntity;
                guideId = excursion.getGuide();
                updateContent();
            }
        });
    }

    /**
     * Mise en place des containers pour les infos.
     */
    private void initiateView(){
        tvPrice = findViewById(R.id.tvPrice);
        tvDistance = findViewById(R.id.tvDistance);
        tvName = findViewById(R.id.tvName);
        tvLocations = findViewById(R.id.tvLocations);
        tvDifficulty = findViewById(R.id.tvDifficulty);
        ivImage = findViewById(R.id.ivImage);
        ivGuide = findViewById(R.id.ivGuide);
    }

    /**
     * Récupération des informations de l'expédition.
     */
    private void updateContent(){
        if (excursion != null){
            setTitle(excursion.getName());

            tvPrice.setText(String.valueOf(excursion.getPrice()) + " CHF");
            tvDistance.setText(String.valueOf(excursion.getDistance()) + " km");
            tvName.setText(excursion.getName());
            tvLocations.setText(excursion.getLocations());
            tvDifficulty.setText(excursion.getDifficulty());

            new ImageLoadTask(excursion.getPicPath(), ivImage).execute();

            tvExcursionGuideName = findViewById(R.id.tvExcursionGuideName);
            tvExcursionGuideLastName = findViewById(R.id.tvExcursionGuideLastName);
            tvExcursionGuideEmail = findViewById(R.id.tvExcursionGuideEmail);
            tvExcursionGuidePhone = findViewById(R.id.tvExcursionGuidePhone);
        }


        GuideViewModel.Factory gFactory = new GuideViewModel.Factory(getApplication(), guideId);
        gViewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) gFactory).get(GuideViewModel.class);
        gViewModel.getGuide().observe(this, guideEntity -> {
            if (guideEntity != null){
                guide = guideEntity;
                updateGuide();
            }
        });
    }

    /**
     * Récupération des infos du guide lié à l'expédition.
     */
    private void updateGuide(){
        if (guide != null){
            tvExcursionGuideName.setText(guide.getName());
            tvExcursionGuideLastName.setText(guide.getLastName());
            tvExcursionGuideEmail.setText(guide.getEmail());
            tvExcursionGuidePhone.setText(String.valueOf(guide.getPhoneNumber()));

            new ImageLoadTask(guide.getPicPath(), ivGuide).execute();
        }
    }
}

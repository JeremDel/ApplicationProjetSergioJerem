package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import viewmodel.guide.GuideViewModel;

public class GuideDetails extends AppCompatActivity {
    private Guide guide;

    private TextView tvBirthDate, tvName, tvLastName, tvDescription, tvAddress, tvEmail, tvPhone;
    private FloatingActionButton edit;

    private GuideViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

        int guideId = getIntent().getIntExtra("guideID", 0);
        initiateView();


        GuideViewModel.Factory factory = new GuideViewModel.Factory(getApplication(), guideId);
        viewModel = new ViewModelProvider(this).get(GuideViewModel.class);
        viewModel.getGuide().observe(this, guideEntity -> {
           if (guideEntity != null){
               this.guide = guideEntity;
               updateContent();
           }
        });
    }

    private void updateContent(){
        if (guide != null){
            setTitle(guide.getName() + " " + guide.getLastName());

            tvBirthDate.setText(guide.getBirthdate());
            tvName.setText(guide.getName());
            tvLastName.setText(guide.getLastName());
            tvDescription.setText(guide.getDescription());
            tvAddress.setText(guide.getAddress());
            tvPhone.setText(guide.getPhoneNumber());
            tvEmail.setText(guide.getEmail());
        }
    }

    private void initiateView(){
        tvBirthDate = findViewById(R.id.tvBirthDate);
        tvName = findViewById(R.id.tvName);
        tvLastName = findViewById(R.id.tvLastName);
        tvDescription = findViewById(R.id.tvDescription);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
    }
}

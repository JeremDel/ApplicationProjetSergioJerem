package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import viewmodel.guide.GuideViewModel;

public class GuideDetails extends BaseActivity {
    private Guide guide;

    private TextView tvBirthDate, tvName, tvLastName, tvDescription, tvAddress, tvEmail, tvPhone;
    private FloatingActionButton edit;

    private GuideViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_guide, frameLayout);

        int guideId = getIntent().getIntExtra("guideID", -1);
        initiateView();


        GuideViewModel.Factory factory = new GuideViewModel.Factory(getApplication(), guideId);
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(GuideViewModel.class);
        viewModel.getGuide().observe(this, guideEntity -> {
           if (guideEntity != null){
               this.guide = guideEntity;
               updateContent();
           }
        });

        setTitle(guide.getName() + " " + guide.getLastName());

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{tvEmail.getText().toString()});
                startActivity(intent);
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"));
                intent.putExtra(Intent.EXTRA_PHONE_NUMBER, tvPhone.getText().toString());
                startActivity(intent);
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

package com.example.applicationprojetsergiojerem.exo.ui.guides;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.async.ImageLoadTask;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.ui.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.applicationprojetsergiojerem.exo.viewmodel.guide.GuideViewModel;

public class GuideDetails extends BaseActivity {
    private Guide guide;

    private TextView tvBirthDate, tvName, tvLastName, tvDescription, tvAddress, tvEmail, tvPhone;
    private ImageView imageGuide;
    private FloatingActionButton edit;

    private GuideViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //getLayoutInflater().inflate(R.layout.activity_guide, frameLayout);

        setContentView(R.layout.activity_guide);

        int guideId = getIntent().getIntExtra("guideID", -1);
        initiateView();


        GuideViewModel.Factory factory = new GuideViewModel.Factory(getApplication(), guideId);
        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(GuideViewModel.class);
        viewModel.getGuide().observe(this, entiteGuide -> {
           if (entiteGuide != null){
               guide = entiteGuide;
               setTitle(guide.getName() + " " + guide.getLastName());

               tvBirthDate.setText(guide.getBirthdate());
               tvName.setText(guide.getName());
               tvLastName.setText(guide.getLastName());
               tvDescription.setText(guide.getDescription());
               tvAddress.setText(guide.getAddress());
               tvPhone.setText(String.valueOf(guide.getPhoneNumber()));
               tvEmail.setText(guide.getEmail());

               new ImageLoadTask(guide.getPicPath(), imageGuide).execute();
           }
        });

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
                String data = "tel:" + tvPhone.getText().toString();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(data));
                //intent.putExtra(Intent.EXTRA_PHONE_NUMBER, tvPhone.getText().toString());
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
        tvBirthDate = findViewById(R.id.tvBirthDateGuideDetail);
        tvName = findViewById(R.id.tvNameGuideDetail);
        tvLastName = findViewById(R.id.tvLastNameGuideDetail);
        tvDescription = findViewById(R.id.tvDescriptionGuideDetail);
        tvAddress = findViewById(R.id.tvAddressGuideDetail);
        tvPhone = findViewById(R.id.tvPhoneGuideDetail);
        tvEmail = findViewById(R.id.tvEmailGuideDetail);
        imageGuide = findViewById(R.id.imageGuide);
    }
}

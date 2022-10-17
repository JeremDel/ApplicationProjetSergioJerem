package com.example.applicationprojetsergiojerem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuideList extends AppCompatActivity {

    private ImageView ivGuideIcon;
    private TextView tvGuideName, tvGuideDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guides_list);

        ivGuideIcon = (ImageView) findViewById(R.id.guideIcon);
        tvGuideDescription = (TextView) findViewById(R.id.guideDescription);
        tvGuideName = (TextView) findViewById(R.id.guideName);

        ivGuideIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGuideView();
            }
        });

        tvGuideName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGuideView();
            }
        });

        tvGuideDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGuideView();
            }
        });
    }

    private void goToGuideView(){
        Intent intent = new Intent(GuideList.this, GuideDetails.class);
        startActivity(intent);
    }
}

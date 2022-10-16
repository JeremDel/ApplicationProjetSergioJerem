package com.example.applicationprojetsergiojerem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuideDetails extends AppCompatActivity {
    private TextView tvEmail, tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guide);

        tvEmail = findViewById(R.id.contact_email);
        tvPhone = findViewById(R.id.contact_number);

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{tvEmail.getText().toString()});
                mailIntent.setType("message/rfc822");

                startActivity(mailIntent);
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + tvPhone.getText().toString()));
                startActivity(phoneIntent);
            }
        });
    }
}

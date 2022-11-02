package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.adapter.RecyclerAdapter;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GuideList extends AppCompatActivity {

    private ImageView ivGuideIcon;
    private TextView tvGuideName, tvGuideDescription;
    private FloatingActionButton addButton;

    private static final String TAG = "GuidesList";

    private List<Guide> guides;
    private RecyclerAdapter<Guide> adapter;
    private GuideListViewModel viewModel; // TODO Waiting for Jeremie to add the view models

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);

        RecyclerView recyclerView = findViewById(R.id.guidesRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        guides = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // Log
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + guides.get(position).getName());

                // Create new intent to the guide details and add the chosen guide id
                Intent intent = new Intent(GuideList.this, GuideDetails.class);
                intent.putExtra("guideID", guides.get(position).getId());

                // Launch the activity
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                // Log
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + guides.get(position).getName());

                // TODO Add a special option maybe?
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(GuideList.this, GuideEdit.class);
                    startActivity(intent);
                }
        );

        // TODO Waiting for the BG's code
        GuideListViewModel.Factory factory = new GuideListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(GuideListViewModel.class);
        viewModel.getGuides().observe(this, guidesEntities -> {
            if (guidesEntities != null) {
                guides = guidesEntities; // TODO check why the error...
                adapter.setData(guides);
            }
        });

        recyclerView.setAdapter(adapter);

        // TODO check if anything here is useful...
        ivGuideIcon = (ImageView) findViewById(R.id.guideIcon);
        tvGuideDescription = (TextView) findViewById(R.id.guideDescription);
        tvGuideName = (TextView) findViewById(R.id.guideName);
        addButton = (FloatingActionButton) findViewById(R.id.add);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideList.this, AddGuide.class);
                startActivity(intent);
            }
        });
    }

    private void goToGuideView(){
        Intent intent = new Intent(GuideList.this, GuideDetails.class);
        startActivity(intent);
    }
}

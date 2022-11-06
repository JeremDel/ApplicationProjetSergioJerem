package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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

import viewmodel.guide.GuideListViewModel;

public class GuideList extends AppCompatActivity {

    private ImageView ivGuideIcon;
    private TextView tvGuideName, tvGuideDescription;
    private FloatingActionButton addButton;

    private static final String TAG = "GuidesList";

    private List<Guide> guides;
    private RecyclerAdapter<Guide> adapter;
    private GuideListViewModel viewModel;

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

        GuideListViewModel.Factory factory = new GuideListViewModel.Factory(getApplication());
        viewModel = new ViewModelProvider(this).get(GuideListViewModel.class);
        viewModel.getGuides().observe(this, guidesEntities -> {
            if (guidesEntities != null) {
                guides = guidesEntities;
                adapter.setData(guides);
            }
        });

        recyclerView.setAdapter(adapter);
    }

}

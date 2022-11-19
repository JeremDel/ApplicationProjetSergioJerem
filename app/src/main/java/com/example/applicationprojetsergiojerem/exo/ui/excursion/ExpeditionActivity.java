package com.example.applicationprojetsergiojerem.exo.ui.excursion;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.adapter.ExcursionRecycleAdapter;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.ui.BaseActivity;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import com.example.applicationprojetsergiojerem.exo.viewmodel.excursion.ExcursionListViewModel;

public class ExpeditionActivity extends BaseActivity {
    private List<Excursion> excursions;
    private ExcursionRecycleAdapter adapter;
    private ExcursionListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);

        }else{
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursions);

        setContentView(R.layout.activity_excursions);
        setTitle(getString(R.string.title_excursion_list));

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        excursions = new ArrayList<>();
        adapter = new ExcursionRecycleAdapter(getApplicationContext(), new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // LOG
                Log.d("List of excursions", "Clicked in " + position);
                Log.d("List of excursions", "Clicked on " + excursions.get(position).getName());

                Intent intent = new Intent(ExpeditionActivity.this, ExpeditionDetails.class);
                intent.putExtra("excursionID", excursions.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Intent intent = new Intent(ExpeditionActivity.this, EditExcursionActivity.class);
                int expeditionID = excursions.get(position).getId();

                intent.putExtra("excursionId", expeditionID);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpeditionActivity.this, EditExcursionActivity.class);
                startActivity(intent);
            }
        });

        ExcursionListViewModel.Factory factory = new ExcursionListViewModel.Factory(getApplication());

        viewModel = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(ExcursionListViewModel.class);
        viewModel.getAllExcursions().observe(this, excursionEntities -> {
            if (excursionEntities != null) {
                excursions = excursionEntities;
                adapter.setData(excursions);
            }
        });

        recyclerView.setAdapter(adapter);
    }

}
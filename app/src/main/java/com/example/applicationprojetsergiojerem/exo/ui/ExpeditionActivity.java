package com.example.applicationprojetsergiojerem.exo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.adapter.RecyclerAdapter;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import viewmodel.excursion.ExcursionListViewModel;

public class ExpeditionActivity extends BaseActivity {

    /*
    private ImageButton button;
    private ImageView imageMuveran, image7lacs, imageWalser;
    private TextView titreMuveran, textMuveran, titre7lacs, text7lacs, titreWalser, textWalser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition);

        button = (ImageButton) findViewById(R.id.ExpeditionAdd);
        imageMuveran = (ImageView) findViewById(R.id.imageMuveran);
        image7lacs = (ImageView) findViewById(R.id.image7lacs);
        imageWalser = (ImageView) findViewById(R.id.imageWalser);
        titreMuveran = (TextView) findViewById(R.id.titreMuveran);
        titre7lacs = (TextView) findViewById(R.id.titre7lacs);
        titreWalser = (TextView) findViewById(R.id.titreWalser);


        imageMuveran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpeditionActivity.this, Muveran.class);
                startActivity(intent);
            }
        });

        titreMuveran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpeditionActivity.this, Muveran.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpeditionActivity.this, addexpedition.class);
                startActivity(intent);
            }
        });
    }

     */

    private List<Excursion> excursions;
    private RecyclerAdapter<Excursion> adapter;
    private ExcursionListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_excursions, frameLayout); // TODO Check what does the frame layout do

        setTitle(getString(R.string.title_excursion_list));

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        excursions = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
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
                // TODO
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

        viewModel = new ViewModelProvider(this).get(ExcursionListViewModel.class);
        viewModel.getOwnExcursion().observe(this, excursionEntities -> {
            if (excursionEntities != null) {
                excursions = excursionEntities;
                adapter.setData(excursions);
            }
        });

        recyclerView.setAdapter(adapter);
    }

}
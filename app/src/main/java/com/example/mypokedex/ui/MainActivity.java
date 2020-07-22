package com.example.mypokedex.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.mypokedex.R;
import com.example.mypokedex.adapter.PokedexAdapter;
import com.example.mypokedex.model.Pokedex;
import com.example.mypokedex.model.Pokemon;
import com.example.mypokedex.viewmodel.PokemonListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Pokemon> pokemonData;
    private RecyclerView recyclerView;
    private PokedexAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        recyclerView = findViewById(R.id.rvPokedex);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PokemonListViewModel pokemonModel = ViewModelProviders.of(this).get(PokemonListViewModel.class);
        pokemonModel.getPokemonList().observe(this, new Observer<Pokedex>() {
            @Override
            public void onChanged(Pokedex pokedex) {
                if(pokedex != null) {
                    pokemonData = pokedex.getResults();
                    mAdapter = new PokedexAdapter(MainActivity.this, pokemonData);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.requestFocus();
                }
            }
        });
    }
}

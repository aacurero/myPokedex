package com.example.mypokedex.ui;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypokedex.R;
import com.example.mypokedex.adapter.PokemonDataAdapter;
import com.example.mypokedex.model.PokemonDetail;
import com.example.mypokedex.viewmodel.PokemonDetailViewModel;

import java.util.ArrayList;

public class PokemonDetailActivity extends AppCompatActivity {
    private String name, url, imgUrl;
    private ImageView ivPokePicture;
    private RecyclerView rvPokeData;
    private PokemonDataAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        Bundle args = getIntent().getExtras();
        if(args != null){
            name = args.getString("name", "");
            url = args.getString("url", "");
            imgUrl = args.getString("imgUrl", "");
        }
        initView();
    }

    private void initView() {
        initToolbar();
        ivPokePicture = findViewById(R.id.ivPokeDetailPic);
        Glide.with(this)
                .load(imgUrl)
                .into(ivPokePicture);

        rvPokeData = findViewById(R.id.rvPokeDetailList);
        rvPokeData.setHasFixedSize(true);
        rvPokeData.setLayoutManager(new LinearLayoutManager(this));

        PokemonDetailViewModel detailViewModel = ViewModelProviders.of(this).get(PokemonDetailViewModel.class);
        detailViewModel.getPokemonList(url).observe(this, new Observer<ArrayList<PokemonDetail>>() {
            @Override
            public void onChanged(ArrayList<PokemonDetail> pokemonDetail) {
                if(pokemonDetail != null){
                    mAdapter = new PokemonDataAdapter(PokemonDetailActivity.this, pokemonDetail);
                    rvPokeData.setAdapter(mAdapter);
                }
            }
        });
    }

    private void initToolbar() {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}

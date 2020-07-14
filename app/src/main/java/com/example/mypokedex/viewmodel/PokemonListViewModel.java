package com.example.mypokedex.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mypokedex.interfaces.Api;
import com.example.mypokedex.model.Pokedex;
import com.example.mypokedex.model.Pokemon;
import com.example.mypokedex.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonListViewModel extends ViewModel {
    private MutableLiveData<Pokedex> pokemonData;

    public LiveData<Pokedex> getPokemonList(){
        if(pokemonData == null){
            pokemonData = new MutableLiveData<>();
            loadPokemonList();
        }
        return pokemonData;
    }

    private void loadPokemonList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Pokedex> call = api.getPokemonBD();

        call.enqueue(new Callback<Pokedex>() {
            @Override
            public void onResponse(Call<Pokedex> call, retrofit2.Response<Pokedex> response) {
                if(response.body() != null) {
                    List<Pokemon> pokeData = new ArrayList<>();
                    for(Pokemon pokemon : response.body().getResults()){
                        String idPokemon = pokemon.getUrl().substring(0, pokemon.getUrl().length()-1);
                        String id = idPokemon.substring(idPokemon.lastIndexOf("/")+1);
                        pokemon.setImg_url(Utils.IMG_URL + id + ".png");
                        pokeData.add(pokemon);
                    }
                    response.body().setResults(pokeData);
                    pokemonData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Pokedex> call, Throwable t) {
                Log.e("Servicio Pokedex", t.getMessage());
            }
        });
    }
}

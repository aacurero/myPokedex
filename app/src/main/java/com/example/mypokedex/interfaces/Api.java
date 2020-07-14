package com.example.mypokedex.interfaces;

import com.example.mypokedex.model.Pokedex;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://pokeapi.co/api/v2/";

    @GET("pokemon?limit=151&offset=0")
    Call<Pokedex> getPokemonBD();
}

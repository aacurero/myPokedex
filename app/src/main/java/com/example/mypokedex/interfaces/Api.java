package com.example.mypokedex.interfaces;

import com.example.mypokedex.model.Pokedex;
import com.example.mypokedex.model.PokemonDetail;
import com.example.mypokedex.utils.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    String BASE_URL = "https://pokeapi.co/api/v2/";

    @GET("pokemon?limit=151&offset=0")
    Call<Pokedex> getPokemonBD();

    @GET
    Call<ResponseBody> getPokemonDetail(@Url String url);
}

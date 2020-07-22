package com.example.mypokedex.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mypokedex.interfaces.Api;
import com.example.mypokedex.model.PokemonDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailViewModel extends ViewModel {
    private MutableLiveData<ArrayList<PokemonDetail>> pokemonData;
    private String url;

    public LiveData<ArrayList<PokemonDetail>> getPokemonList(String url){
        this.url = url;
        if(pokemonData == null){
            pokemonData = new MutableLiveData<>();
            loadPokemonData();
        }
        return pokemonData;
    }

    private void loadPokemonData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = api.getPokemonDetail(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null) {
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        JSONArray type = data.getJSONArray("types");
                        JSONArray abilities = data.getJSONArray("abilities");
                        JSONArray moves = data.getJSONArray("moves");
                        String location = data.getString("location_area_encounters");
                        ArrayList<PokemonDetail> details = new ArrayList<>();
                        if(type.length() > 0) {
                            details.add(new PokemonDetail("Tipo"));
                            for (int i = 0; i < type.length(); i++) {
                                JSONObject typeData = type.getJSONObject(i);
                                details.add(new PokemonDetail(typeData.getJSONObject("type").getString("name")));
                            }
                        }
                        if(abilities.length() > 0){
                            details.add(new PokemonDetail("Habilidades"));
                            for (int i = 0; i < abilities.length(); i++) {
                                JSONObject abilitiesData = abilities.getJSONObject(i);
                                details.add(new PokemonDetail(abilitiesData.getJSONObject("ability").getString("name")));
                            }
                        }
                        if(moves.length() > 0){
                            details.add(new PokemonDetail("Ataques"));
                            for (int i = 0; i < moves.length(); i++) {
                                JSONObject movesData = moves.getJSONObject(i);
                                details.add(new PokemonDetail(movesData.getJSONObject("move").getString("name")));
                            }
                        }
                        if(location != null) {
                            details.add(new PokemonDetail("Lugares"));
                            details.add(new PokemonDetail(location));
                        }
                        pokemonData.setValue(details);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Servicio Pokedex", t.getMessage());
            }
        });
    }
}

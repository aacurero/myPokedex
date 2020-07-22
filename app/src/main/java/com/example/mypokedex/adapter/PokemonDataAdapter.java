package com.example.mypokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypokedex.R;
import com.example.mypokedex.model.PokemonDetail;

import java.util.ArrayList;

public class PokemonDataAdapter extends RecyclerView.Adapter<PokemonDataAdapter.ViewHolder> {
    private ArrayList<PokemonDetail> mData;
    private Context mContext;

    public PokemonDataAdapter(Context mContext, ArrayList<PokemonDetail> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public PokemonDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonDataAdapter.ViewHolder holder, int position) {
        PokemonDetail pokemon = mData.get(position);
        if(isTitle(pokemon.getProperty())){
            holder.tvTitle.setText(pokemon.getProperty());
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvData.setVisibility(View.GONE);
        } else {
            holder.tvData.setText(pokemon.getProperty());
            holder.tvData.setVisibility(View.VISIBLE);
            holder.tvTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private boolean isTitle(String property){
        if(property.equals(mContext.getString(R.string.title_type)) ||
            property.equals(mContext.getString(R.string.title_evolution)) ||
            property.equals(mContext.getString(R.string.title_moves)) ||
            property.equals(mContext.getString(R.string.title_skills)) ||
            property.equals(mContext.getString(R.string.title_places))) {
            return true;
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvPokeDetTitle);
            tvData = itemView.findViewById(R.id.tvPokeDetData);
        }
    }
}

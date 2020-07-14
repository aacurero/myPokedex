package com.example.mypokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypokedex.R;
import com.example.mypokedex.model.Pokemon;

import java.util.List;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.ViewHolder>{
    private Context mContext;
    private List<Pokemon> mData;

    public PokedexAdapter(Context mContext, List<Pokemon> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public PokedexAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokedex, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexAdapter.ViewHolder holder, int position) {
        Pokemon pokemon = mData.get(position);

        Glide.with(mContext)
                .load(pokemon.getImg_url())
                .into(holder.ivPokedex);

        holder.tvPokedex.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPokedex;
        private TextView tvPokedex;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPokedex = itemView.findViewById(R.id.ivPokePic);
            tvPokedex = itemView.findViewById(R.id.tvPokeName);
        }
    }
}

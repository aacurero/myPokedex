package com.example.mypokedex.model;

import java.util.ArrayList;

public class PokemonDetail {
    private String property;

    public PokemonDetail(String property){
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}

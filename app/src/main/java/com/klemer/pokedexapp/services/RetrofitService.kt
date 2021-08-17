package com.klemer.pokedexapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    fun getInstance(path: String): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
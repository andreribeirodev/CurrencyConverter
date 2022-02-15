package com.example.currencyconverter.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//todo: Criar um singleton para ter acesso a qualquer momento
//todo: pesquisar oque é singleton
//todo: pesquisar oque é companion object
//todo: pesquisar oque é endpoint

// Aqui foi criado o utilizario para instânciar o Retrofit //

class NetworkUtils {
    companion object {
        fun getRetrofitInstance(patch: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(patch)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
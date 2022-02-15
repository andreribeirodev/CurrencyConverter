package com.example.currencyconverter.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path


//todo: criar metodos que o retrofit irá utilizar
// Essa é uma interface de consulta
interface Endpoint {

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies.json")
    fun getCurrency(): Call<JsonObject>

    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/{from}/{to}.json")
    fun getCurrencyRate(
        @Path("from", encoded = true) from: String,
        @Path("to", encoded = true) to: String): Call<JsonObject>
}
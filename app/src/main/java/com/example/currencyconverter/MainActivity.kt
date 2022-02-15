package com.example.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.currencyconverter.api.Endpoint
import com.example.currencyconverter.util.NetworkUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class MainActivity : AppCompatActivity() {

    private lateinit var spFrom: Spinner
    private lateinit var spTo: Spinner
    private lateinit var btConvert: Button
    private lateinit var tvResult: TextView
    private lateinit var etValueFrom: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        getCurrency()

        btConvert.setOnClickListener { convertMoney() }
    }

    fun initViews() {
        spFrom = findViewById(R.id.spFrom)
        spTo = findViewById(R.id.spTo)
        btConvert = findViewById(R.id.btConvert)
        tvResult = findViewById(R.id.tvResult)
        etValueFrom = findViewById(R.id.etValueFrom)
    }

    fun getCurrency() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrency().enqueue(object : retrofit2.Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var data = mutableListOf<String>()

                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)
                }

                val posBRL = data.indexOf("brl")
                val posUSD = data.indexOf("usd")

                //todo: popular o spinner, criando um adapter
                val adapter =
                    ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, data)
                spFrom.adapter = adapter
                spTo.adapter = adapter

                spFrom.setSelection(posBRL)
                spTo.setSelection(posUSD)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("Falhou mesmo !")
            }

        })
    }

    fun convertMoney() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencyRate(spFrom.selectedItem.toString(), spTo.selectedItem.toString())
            .enqueue(object : retrofit2.Callback<JsonObject> {

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    var data =
                        response.body()?.entrySet()?.find { it.key == spTo.selectedItem.toString() }
                    var rate: Double = data?.value.toString().toDouble()
                    val conversion = etValueFrom.text.toString().toDouble() * rate

                    tvResult.setText(conversion.toString())
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    println("Não foi !")
                }
            })
    }
}
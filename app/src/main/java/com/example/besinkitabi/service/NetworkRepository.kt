package com.example.besinkitabi.service

import com.example.besinkitabi.model.Besin
import retrofit2.http.GET

interface NetworkRepository {

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getBesinler(): List<Besin>


}
package parra.mario.climapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherApiClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
}
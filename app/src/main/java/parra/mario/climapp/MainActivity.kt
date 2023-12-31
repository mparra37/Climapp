package parra.mario.climapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var tv_temp: TextView
    lateinit var et_city: EditText
    lateinit var btn_buscar: Button
    lateinit var tv_temp_max: TextView
    lateinit var tv_temp_min: TextView
    val apiKey = "6803232807d2db181ffffe13a6e68f31"
    var city = "Tijuana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_temp = findViewById(R.id.tv_temperatura)
        et_city = findViewById(R.id.campo_ciudad)
        btn_buscar = findViewById(R.id.btn_buscar)
        tv_temp_max = findViewById(R.id.tv_temp_max)
        tv_temp_min = findViewById(R.id.tv_temp_min)

        btn_buscar.setOnClickListener{

            city = et_city.text.toString()
            search_weather()
        }

    }


    private fun search_weather(){

        val call = WeatherApiClient.weatherService.getCurrentWeather(city, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                Toast.makeText(applicationContext, response.message(), Toast.LENGTH_LONG).show()
                if(response.isSuccessful){
                    val weather = response.body()
                    tv_temp.text = "Temperature : ${weather?.main?.temp}°C \nHumedad: ${weather?.main?.humidity}" +
                            " \n   ${weather?.main?.temp_max}°C \n ${weather?.weather?.get(0)?.description}"
                    //tv_temp_max.text = "Maxima: ${weather?.main?.temp_max}°C"
                    //tv_temp_min.text = "Minima: ${weather?.weather?.get(0)?.description}°C"

                }else{
                    Log.e("MainActivity", response.message())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Ocurrió un error!", Toast.LENGTH_LONG).show()
            }

        })
    }
}
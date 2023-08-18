package parra.mario.climapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import parra.mario.climapp.WeatherApiClient
import parra.mario.climapp.WeatherResponse
import parra.mario.climapp.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {
    val apiKey = "6803232807d2db181ffffe13a6e68f31"
    var city = "Madrid"
    lateinit var textView: TextView
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        search_weather()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun search_weather(){

        val call = WeatherApiClient.weatherService.getCurrentWeather(city, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show()
                if(response.isSuccessful){
                    val weather = response.body()
                    textView.text = "Temperature : ${weather?.main?.temp}°C \nHumedad: ${weather?.main?.humidity}" +
                            " \n ${weather?.weather?.get(0)?.description}"
                    //tv_temp_max.text = "Maxima: ${weather?.main?.temp_max}°C"
                    //tv_temp_min.text = "Minima: ${weather?.weather?.get(0)?.description}°C"

                }else{
                    Log.e("MainActivity", response.message())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(context, "Ocurrió un error!", Toast.LENGTH_LONG).show()
            }

        })
    }
}
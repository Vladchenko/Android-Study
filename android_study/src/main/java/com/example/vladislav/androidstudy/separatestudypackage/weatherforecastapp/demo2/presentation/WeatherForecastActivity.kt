package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.presentation

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.presentation.viewmodel.WeatherForecastViewModel
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.presentation.viewmodel.WeatherForecastViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * TODO
 */
@AndroidEntryPoint
class WeatherForecastActivity : AppCompatActivity() {

    private enum class TemperatureType {
        CELSIUS,
        FAHRENHEIT
    }

    private lateinit var cityNameTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var degreesTypeTextView: TextView
    private lateinit var degreesValueTextView: TextView
    private lateinit var temperatureType: TemperatureType
    private lateinit var weatherTypeTextView: TextView
    private lateinit var weatherImageView: ImageView

    private lateinit var viewModel: WeatherForecastViewModel
    @Inject
    lateinit var viewModelFactory: WeatherForecastViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initViews()
        temperatureType = TemperatureType.CELSIUS
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherForecastViewModel::class.java)
        viewModel.getWeatherForecast("Kazan")   //TODO
        // getResponse()
    }

    private fun initViews() {
        cityNameTextView = findViewById(R.id.city_name_text_view)
        dateTextView = findViewById(R.id.date_text_view)
        degreesValueTextView = findViewById(R.id.degrees_value_text_view)
        degreesTypeTextView = findViewById(R.id.degrees_type_text_view)
        weatherTypeTextView = findViewById(R.id.weather_type_text_view)
        weatherImageView = findViewById(R.id.weather_type_image_view)
    }

    private fun getResponse() {
        val url = "http://api.openweathermap.org/data/2.5/weather?q=Kazan&appid=a8f0e797059b7959399040200fd43231"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                cityNameTextView.text = response.getString("name")
                setDegreesValuesAndType(response)
                dateTextView.text = getCurrentDate()

                weatherTypeTextView.text = response.getJSONArray("weather").getJSONObject(0).getString("description")
                val weatherImageResource = resources.getIdentifier(
                    "icon_" + weatherTypeTextView.text.toString().replace(" ", ""),
                    "drawable",
                    packageName
                )
                weatherImageView.setImageResource(weatherImageResource)
            },
            { error ->
                Log.e("WEATHER FORECASTER", error.message ?: "Some error !")
            }
        )

        val queue = Volley.newRequestQueue(this)
        queue.add(jsonObjectRequest)
    }

    private fun getCelsiusFromKelvinTemperature(kelvinTemp: Double) = kelvinTemp - 273.15

    private fun getFahrenheitFromKelvinTemperature(kelvinTemp: Double) = 1.8 * (kelvinTemp - 273) + 32.0

    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
        return formatter.format(date)
    }

    private fun setDegreesValuesAndType(response: JSONObject) {
        val kelvinTemperature = response.getJSONObject("main").getDouble("temp")
        if (temperatureType == TemperatureType.CELSIUS) {
            degreesValueTextView.text =
                getCelsiusFromKelvinTemperature(kelvinTemperature).roundToInt().toString()
            degreesTypeTextView.text = "℃"
        } else {
            degreesValueTextView.text =
                getFahrenheitFromKelvinTemperature(kelvinTemperature).roundToInt().toString()
            degreesTypeTextView.text = "℉"
        }
    }
}
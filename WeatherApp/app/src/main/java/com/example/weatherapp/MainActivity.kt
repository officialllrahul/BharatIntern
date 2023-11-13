package com.example.weatherapp

import android.util.Log  // Add this import statement
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request


class MainActivity : AppCompatActivity() {
    private val apiKey = "c51704719f6e98cdd1abf98a4e40e6f6"
    private val searchEditText: EditText by lazy { findViewById(R.id.search_edit_text) }
    private val weatherTextView: TextView by lazy { findViewById(R.id.weather_text_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.search_button)

        searchButton.setOnClickListener {
            val cityName = searchEditText.text.toString()
            if (cityName.isNotBlank()) {
                fetchWeather(cityName)
            }
        }
    }

    private fun fetchWeather(cityName: String) {
        // Show loading indicator
        // ...

        val url = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$apiKey&units=metric"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val weatherData = response.optJSONObject("main")
                val temperature = weatherData?.optDouble("temp") ?: 0.0
                val description = response.optJSONArray("weather")?.optJSONObject(0)?.optString("description") ?: ""

                val weatherText ="Temperature: $temperature°C, Description: $description"
                weatherTextView.text = weatherText
            },
            { error ->
                // Hide loading indicator
                // ...

                Log.e("WeatherApp", "Volley error: ${error.message}", error)
                Toast.makeText(this, "Failed to fetch weather data", Toast.LENGTH_SHORT).show()
            })

// Add the request to the request queue
        Volley.newRequestQueue(this).add(request)
    }
}

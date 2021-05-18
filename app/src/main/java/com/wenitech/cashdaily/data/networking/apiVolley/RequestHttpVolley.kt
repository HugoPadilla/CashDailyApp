package com.wenitech.cashdaily.data.networking.apiVolley

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.wenitech.cashdaily.features.home.model.City
import java.lang.Exception

class RequestHttpVolley(val context: Context) {

    /*if (NetWorkStatus.hayRed(activity as AppCompatActivity)){
        Toast.makeText(requireContext(), "Si hay red", Toast.LENGTH_SHORT).show()

        val apiKey = "a08ae9f705f78019aaf4bb96bff4430c"
        val idCity = "3674453"
        val language = "es"
        val unit = "metric"

        var requestHttpVolley = RequestHttpVolley(requireContext())
        var city = City()
        city = requestHttpVolley.solicitudHttpConVolley("https://api.openweathermap.org/data/2.5/weather?id=$idCity&appid=$apiKey&units=$unit&lang=$language")

    } else {
        Toast.makeText(requireContext(), "No hay red", Toast.LENGTH_SHORT).show()
    }*/

    fun solicitudHttpConVolley(url: String): City {

        var city = City()

        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            try {
                val gson = Gson()
                city = gson.fromJson(response, City::class.java)
                Log.d("volley", "solicitudHttpConVolley: ${response.toString()}")

            } catch (e: Exception) {
                Log.d("volley", "solicitudHttpConVolley: ${e.toString()}")
            }
        }, { volleyError ->
            Log.d("volley", "solicitudHttpConVolley: ${volleyError.toString()}")
        })

        queue.add(stringRequest)
        return city;
    }
}
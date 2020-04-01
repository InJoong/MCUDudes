package com.example.linearlayout

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import layout.MCUDude
import org.json.JSONObject

class MCUMarvelVolley(val url: String, val context: Context, val mcuAdapter: MCUDudeRecyclerAdapter){
    val queue = Volley.newRequestQueue(context)

    fun callMarvelAPI(){
        val dataHeroes = ArrayList<MCUDude>()

        val requestMarvel = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject>{
                response ->
                val  heroes = response.getJSONObject("data").getJSONArray("results")

                for(i in 0..heroes.length()-1){
                    val character = heroes.getJSONObject(i)
                    val image = character.getJSONObject("thumbnail")
                    val thumbnail = image.getString("path") + "." + image.getString("extension")
                    val mcuDude = MCUDude(character.getString("name"), character.getString("description"), thumbnail)
                    dataHeroes.add(mcuDude)
                }
                mcuAdapter.setData(dataHeroes)
            }, Response.ErrorListener {
                Toast.makeText(context, "Hubo un error", Toast.LENGTH_LONG).show()
            })

        queue.add(requestMarvel)
    }
}
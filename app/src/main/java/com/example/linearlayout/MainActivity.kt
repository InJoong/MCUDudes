package com.example.linearlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import layout.MCUDude
import org.json.JSONArray
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class MainActivity : AppCompatActivity() {

    private lateinit var mcuDudeAdapter: MCUDudeRecyclerAdapter
    var jAvenger: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView() //Adapter instanciado
        Log.i("MCUMARVEL API", getMarvelAPIUrl())
        MCUMarvelVolley(getMarvelAPIUrl(), this, mcuDudeAdapter).callMarvelAPI()
    }

    private fun setRecyclerView(){
        recycler_view_mcu.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mcuDudeAdapter = MCUDudeRecyclerAdapter()
            adapter = mcuDudeAdapter
        }
    }

    private fun getDataSet(): ArrayList<MCUDude>{
        val dudes = ArrayList<MCUDude>()
        jAvenger = readJSON()
        val jArray = JSONArray(jAvenger)
        Log.i("edu.example.linearlayout", jAvenger)

        for (array in 0 until jArray.length()-1){
            val jobject = jArray.getJSONObject(array)
            val name = jobject.getString("name/alias")
            val note = jobject.getString("notes")
            dudes.add(MCUDude(name, note, ""))
        }
        return dudes
    }

    fun readJSON():String? {
        var jContent: String? = null

        try{
            val inputs = assets.open("avengers.json")
            jContent = inputs.bufferedReader().use { it.readLine() }

        } catch (ex: Exception){
            ex.printStackTrace()
            Toast.makeText(this, "Thanos lo hizo de nuevo", Toast.LENGTH_LONG).show()
        }

        return jContent
    }

    fun String.md5(): String{
        val md5Al = MessageDigest.getInstance("MD5")
        return BigInteger(1, md5Al.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    fun getMarvelAPIUrl(): String{
        val PRIVATE_KEY = "4eda98930d08a38912b6456ff06dd464a67573d8"
        val PUBLIC_KEY = "1ad9b3398f4f4b255a690a6edba5ce9f"
        val tString = Timestamp(System.currentTimeMillis()).toString()
        val hString = tString + PRIVATE_KEY + PUBLIC_KEY
        val hash = hString.md5()

        var marvelAPI : String = "https://gateway.marvel.com:443/v1/public/characters?ts=" +
                tString +
                "&limit=100&apikey="+PUBLIC_KEY+"&hash="+hash
        return marvelAPI
    }
}

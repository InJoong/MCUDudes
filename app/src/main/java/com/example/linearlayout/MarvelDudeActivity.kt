package com.example.linearlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_marvel_dude.*
import layout.MCUDude

class MarvelDudeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marvel_dude)

        val dude = intent.getSerializableExtra("dude") as? MCUDude
        imageView2.setPicDude(dude!!.imageDude)
    }

    fun ImageView.setPicDude(url:String){
        val options = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
        Glide.with(this)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this)
    }
}

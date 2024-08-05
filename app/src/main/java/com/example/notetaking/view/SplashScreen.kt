package com.example.notetaking.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.notetaking.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.typeWriterView.animateText("Take notes for remember");
        binding.typeWriterView.avoidTextOverflowAtEdge(true)
       binding.typeWriterView.setCharacterDelay(100)//in ms

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }
}
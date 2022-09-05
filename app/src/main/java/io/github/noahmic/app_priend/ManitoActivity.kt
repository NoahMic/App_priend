package io.github.noahmic.app_priend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.github.noahmic.app_priend.databinding.ActivityManitoBinding

class ManitoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManitoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manito)

        binding.manitoStartBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val name = intent.getStringExtra("name").toString()
        binding.manitoName.text = name
    }
}
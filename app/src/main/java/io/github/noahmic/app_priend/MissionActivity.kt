package io.github.noahmic.app_priend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.github.noahmic.app_priend.databinding.ActivityMissionBinding

class MissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mission)
        val missionCreateAdapter = MissionCreateAdapter()
        binding.missionRecyclerview.adapter = missionCreateAdapter

    }

    override fun onStart() {
        super.onStart()
    }

}
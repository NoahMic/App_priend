package io.github.noahmic.app_priend

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.noahmic.app_priend.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainGroupTxt.setOnClickListener {
            startActivity(Intent(this, GroupActivity::class.java))
        }

        binding.mainMissionTxt.setOnClickListener{
            startActivity(Intent(this, MissionActivity::class.java))
        }

        binding.logoutBtn.setOnClickListener {
            auth?.signOut()
            Log.d(TAG, "onCreate: ${auth?.currentUser}")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        auth = Firebase.auth
        if (auth?.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setGroupAdapter(groupList: ArrayList<Group>) {
        val groupAdapter = GroupAdapter(groupList)
        binding.groupRecyclerview.adapter = groupAdapter
    }

    private fun setMissionAdapter(missionList: ArrayList<Mission>) {
        val missionAdapter = MissionAdapter(missionList)
        binding.missionRecyclerview.adapter = missionAdapter
    }

    override fun onStart() {
        super.onStart()
        val service = RetrofitClient.getService()
        if (auth?.currentUser != null) {
            service.getUser(auth?.uid.toString())?.enqueue(object : Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    if (response.isSuccessful) {
                        var result: UserInfo? = response.body()
                        if (!result?.groups.isNullOrEmpty()) {
                            setGroupAdapter(result?.groups as ArrayList<Group>)
                        }
                        if (!result?.missions.isNullOrEmpty()) {
                            setMissionAdapter(result?.missions as ArrayList<Mission>)
                        }
                    } else {
                        Log.d(TAG, "onResponse: 실패....")
                    }

                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Log.d(TAG, "onFailure: 레트로핏 버그... ${t.message.toString()}")
                }

            })
        }
    }
}
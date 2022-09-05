package io.github.noahmic.app_priend

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.noahmic.app_priend.databinding.ActivityGroupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupBinding
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group)
        auth = Firebase.auth

        binding.groupJoinBtn.setOnClickListener() {
            val groupCode = binding.groupCode.text
            val service = RetrofitClient.getService()
            Log.d(TAG, "onCreate: 그룹 액티비티 ${auth?.uid}")
            service.joinGroup(groupCode.toString(), auth?.currentUser?.uid.toString())
                ?.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        Toast.makeText(baseContext, "그룹 가입 성공", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(baseContext, "그룹 가입 실패", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        binding.groupCreateBtn.setOnClickListener() {
            startActivity(Intent(this, GroupCreateActivity::class.java))
        }
    }
}
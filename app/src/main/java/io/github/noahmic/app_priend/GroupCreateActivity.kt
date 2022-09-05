package io.github.noahmic.app_priend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.noahmic.app_priend.databinding.ActivityGroupCreateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupCreateBinding
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_create)

        auth = Firebase.auth
        binding.saveGroup.setOnClickListener {
            val service = RetrofitClient.getService()
            service.postGroup(CreateGroup(binding.groupName.text.toString(), auth?.currentUser!!.uid)).enqueue(object :
                Callback<Group> {
                override fun onResponse(call: Call<Group>, response: Response<Group>) {
                    if (response.isSuccessful) {
                        Toast.makeText(baseContext, "그룹 생성 성공", Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(baseContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(baseContext, "그룹 생성 개같이 멸망2 ^^", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Group>, t: Throwable) {
                    Toast.makeText(baseContext, "그룹 생성 개같이 멸망 ^^", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}
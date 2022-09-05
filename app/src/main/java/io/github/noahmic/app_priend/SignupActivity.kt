package io.github.noahmic.app_priend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.noahmic.app_priend.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()

        binding.signupGoBtn.setOnClickListener {
            signup(binding.signupEditEmail.text.toString(), binding.signupEditPw.text.toString())
        }

    }

    private fun signup(email: String, pw: String) {
        if (email.isNotEmpty() && pw.isNotEmpty()) {
            auth!!.createUserWithEmailAndPassword(email, pw).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth?.signInWithEmailAndPassword(email, pw)
                    val service = RetrofitClient.getService()
                    service.postUser(CreateUser(auth!!.uid, binding.signupEditName.text.toString())).enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                Toast.makeText(baseContext, "계정 생성 완료", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Toast.makeText(baseContext, "계정 생성da/s;d,a;skd 실패?", Toast.LENGTH_SHORT).show()
                        }

                    })
                    startActivity(Intent(baseContext, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(baseContext, MainActivity::class.java))
                    finish()
                }
            }
            if (auth?.currentUser != null) {
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }
        }
    }
}
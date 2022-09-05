package io.github.noahmic.app_priend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.noahmic.app_priend.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = Firebase.auth
        binding.loginStartBtn.setOnClickListener {
            login(binding.loginEditEmail.text.toString() ,binding.loginEditPw.text.toString())
        }
        binding.loginSignupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    private fun login(email: String, pw: String) {
        if (email.isNotEmpty() && pw.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, pw)
                ?.addOnCompleteListener(this) {
                    task -> if (task.isSuccessful) {
                        Toast.makeText(baseContext, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Toast.makeText(baseContext, "로그인 실패...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun moveMainPage(user: FirebaseUser?){
        if(user!= null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
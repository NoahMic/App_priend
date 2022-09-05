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
import io.github.noahmic.app_priend.databinding.ActivityMemberBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding
    private lateinit var name:String
    private lateinit var group:String
    private lateinit var owner:String
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member)
        auth = Firebase.auth

        binding.startManito.setOnClickListener {
            val service = RetrofitClient.getService()
            service.setManito(group).enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        for (user in result!!) {
                            if (user.uid == auth?.uid) {
//                                Log.d(TAG, "onResponse: ${user.manito}")
                                val manitos = user.manito.split(";")
                                for (manito in manitos) {
                                    val x = manito.split("|")
                                    if (x[0] == group) {
                                        service.getOneUser(x[1]).enqueue(object : Callback<User> {
                                            override fun onResponse(
                                                call: Call<User>,
                                                response: Response<User>
                                            ) {
                                                if (response.isSuccessful) {
                                                    val result = response.body()
                                                    val intent = Intent(baseContext, ManitoActivity::class.java)
                                                    intent.putExtra("name", result?.name)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                            }

                                            override fun onFailure(call: Call<User>, t: Throwable) {
                                                Toast.makeText(baseContext, "마니또 세팅 실패...", Toast.LENGTH_SHORT)
                                                    .show()
                                            }

                                        })
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Toast.makeText(baseContext, "마니또 세팅 실패..", Toast.LENGTH_SHORT)
                        .show()                }

            })
        }
    }

    private fun setMemberAdapter(memberList: ArrayList<Member>) {
        val memberAdapter = MemberAdapter(memberList)
        binding.memberRecycler.adapter = memberAdapter
    }

    override fun onStart() {
        super.onStart()
        val service = RetrofitClient.getService()
        group = intent.getStringExtra("group").toString()
        name = intent.getStringExtra("name").toString()
        owner = intent.getStringExtra("owner").toString()
        binding.manitoGroupName.text = name
        binding.groupCode.text = "#$group"
        binding.ownerName.text = owner
        service.getGroupMember(group)?.enqueue(object : Callback<GroupMembers> {
            override fun onResponse(call: Call<GroupMembers>, response: Response<GroupMembers>) {
                if (response.isSuccessful) {
                    var result: GroupMembers? = response.body()
                    if (result != null) {
                        setMemberAdapter(result.members as ArrayList<Member>)
                    }
                }
            }

            override fun onFailure(call: Call<GroupMembers>, t: Throwable) {
                Log.d(TAG, "onFailure: 레트로핏 버그... ${t.message.toString()}")
            }

        })
    }
}
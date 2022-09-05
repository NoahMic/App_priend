package io.github.noahmic.app_priend

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("/user/{uid}")
    fun getUser(@Path("uid") uid:String) : Call<UserInfo>

    @GET("/user")
    fun getOneUser(@Query("uid") uid:String) : Call<User>

    @GET("/group/{code}")
    fun getGroupMember(@Path("code") code: String) : Call<GroupMembers>

    @POST("/user")
    fun postUser(@Body body: CreateUser) : Call<User>

    @POST("/group")
    fun postGroup(@Body body: CreateGroup) : Call<Group>

    @POST("/group/{code}")
    fun joinGroup(@Path("code") code: String, @Query("uid") uid: String) : Call<User>

    @POST("/manito/{code}")
    fun setManito(@Path("code") code:String) :Call<List<User>>
}

/*
* {
*   "name" : "string".
*   ""
*
* }
*
* */
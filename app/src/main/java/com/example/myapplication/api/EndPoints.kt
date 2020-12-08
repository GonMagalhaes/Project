package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("/users/")
    fun getUsers(): Call<List<User>>

    @GET("/myslim/api/problem")
    fun getProblem(): Call<List<Problem>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("/myslim/api/user")
    fun postLogin(@Field("user") user: String?,
                  @Field("password") password: String?): Call<OutputPost>
}
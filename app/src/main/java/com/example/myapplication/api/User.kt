package com.example.myapplication.api

data class User(
        val id: Int,
        val user: String,
        val password: String
)
data class Problem(
        val id: Int,
        val lat: String,
        val lng: String,
        val description: String,
        val IdUser: Int
)
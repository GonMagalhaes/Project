package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
}

    fun logar(view: View) {
        val intent = Intent(this@Login, MapsActivity::class.java)
        startActivity(intent)
    }

    fun notas(view: View) {
        val intent = Intent(this@Login, MainActivity::class.java)
        startActivity(intent)
    }
}
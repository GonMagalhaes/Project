package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

    class Update : AppCompatActivity() {

        private lateinit var tituloText: EditText
        private lateinit var textoText: EditText
        private lateinit var dataText: EditText

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.update)

            val id = intent.getIntExtra("id",-1)
            tituloText = findViewById(R.id.update_titulo)
            textoText = findViewById(R.id.update_texto)
            dataText = findViewById(R.id.update_data)

            var titulo = intent.getStringExtra("titulo")
            var texto = intent.getStringExtra("texto")
            var data = intent.getStringExtra("data")

            tituloText.setText(titulo)
            textoText.setText(texto)
            dataText.setText(data)

            val update = findViewById<Button>(R.id.button_update)
            update.setOnClickListener {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(tituloText.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    replyIntent.putExtra(EXTRA_REPLY_DELETE,"update")
                    replyIntent.putExtra(EXTRA_REPLY_ID,id)
                    replyIntent.putExtra(EXTRA_REPLY_TITULO, tituloText.text.toString())
                    replyIntent.putExtra(EXTRA_REPLY_TEXTO, textoText.text.toString())
                    replyIntent.putExtra(EXTRA_REPLY_DATA, dataText.text.toString())
                    setResult(Activity.RESULT_OK, replyIntent)
                }

                finish()
            }

            val delete = findViewById<Button>(R.id.button_delete)
            delete.setOnClickListener {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(tituloText.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    replyIntent.putExtra(EXTRA_REPLY_DELETE,"delete")
                    replyIntent.putExtra(EXTRA_REPLY_ID,id)
                    replyIntent.putExtra(EXTRA_REPLY_TITULO, tituloText.text.toString())
                    replyIntent.putExtra(EXTRA_REPLY_TEXTO, textoText.text.toString())
                    replyIntent.putExtra(EXTRA_REPLY_DATA, dataText.text.toString())
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        }


        companion object {
            const val EXTRA_REPLY_TITULO = "com.example.android.titulo"
            const val EXTRA_REPLY_TEXTO = "com.example.android.texto"
            const val EXTRA_REPLY_DATA = "com.example.android.data"
            const val EXTRA_REPLY_DELETE = "com.example.android.delete"
            const val EXTRA_REPLY_ID = "com.example.android.id"

        }
    }

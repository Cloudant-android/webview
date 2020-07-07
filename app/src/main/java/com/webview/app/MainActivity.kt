package com.webview.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onClick()
    }


    private fun onClick(){


        btn_https.setOnClickListener {
            edit_url.setText("https://")
        }

        btn_http.setOnClickListener {
            edit_url.setText("http://")
        }

        btn_start.setOnClickListener {

            val url = edit_url.text.toString()

            if (url.isEmpty() || url == "https://" || url == "http://") {
                Toast.makeText(this,"请输入网址",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            startActivity(Intent(this,WebActivity::class.java)
                .putExtra("url",url))

        }



    }

}
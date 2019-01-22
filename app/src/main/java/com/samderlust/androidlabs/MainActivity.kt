package com.samderlust.androidlabs

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var linearLayout: Intent
    lateinit var relativeLayout: Intent
    lateinit var gridLayout: Intent
    lateinit var button: Button

    fun linearClick(v: View){
        startActivity(linearLayout)
    }

    fun gridClick(v: View){
        startActivity(gridLayout)
    }

    fun relativeClick(v: View){
        startActivity(relativeLayout)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button321)
        linearLayout = Intent(applicationContext, Linear::class.java)
        relativeLayout = Intent(applicationContext, Relative::class.java)
        gridLayout = Intent(applicationContext, Grid::class.java)

    }
}

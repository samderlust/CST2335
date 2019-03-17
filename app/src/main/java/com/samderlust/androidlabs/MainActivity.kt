package com.samderlust.androidlabs

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    fun onLogin(v: View){
        val profilePage = Intent(this, ProfileActivity::class.java)

        profilePage.putExtra("email", emailInput.text.toString())

        startActivity(profilePage)
    }

    fun gotoToolbar(v: View){
        val toolbarTest = Intent(this, TestToolbar::class.java)

        toolbarTest.putExtra("a", 1)

        startActivity(toolbarTest)
    }

    fun toWeather(v: View){
        val wIntent = Intent(this, WeatherForecast::class.java)

        startActivity(wIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val savedEmail = sharedPref.getString("emailSaved", "")

        emailInput.hint = savedEmail

        println("SAVE EMAIL ###########:" + savedEmail)

    }

    override fun onPause() {
        super.onPause()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("emailSaved",emailInput.text.toString())
//            commit()
            apply()
        }
    }

}

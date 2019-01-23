package com.samderlust.androidlabs

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    fun onLogin(v: View){
        val profilePage = Intent(applicationContext, ProfileActivity::class.java)

        profilePage.putExtra("email", emailInput.text.toString())

        startActivity(profilePage)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val savedEmail = sharedPref.getString(getString(R.string.emailSaving), "")

        emailInput.hint = savedEmail

        println("SAVE EMAIL ###########:" + savedEmail)

    }

    override fun onPause() {
        super.onPause()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.emailSaving),emailInput.text.toString())
            commit()
        }
    }

}

package com.samderlust.androidlabs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast

class TestToolbar : AppCompatActivity() {

    lateinit var sb: Snackbar
    var messToToast = "This is the initial message"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)

        val myToolbar = findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(myToolbar)

        sb = Snackbar.make(myToolbar, "Go back?", Snackbar.LENGTH_LONG)
            .setAction("OK"){e -> finish()}


        val ab = intent.getStringExtra("a")
        println("#############$ab")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item1 -> Toast.makeText(baseContext,messToToast,Toast.LENGTH_SHORT).show()
            R.id.item2 -> dialogShow()
            R.id.item3 -> sb.show()
            else -> Toast.makeText(baseContext,"You clicked on the overflow menu",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    /*override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if(featureId == AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR)
            Toast.makeText(baseContext,"You clicked on the overflow menu",Toast.LENGTH_SHORT).show()
        return super.onMenuOpened(featureId, menu)
    }*/

    fun dialogShow(){
        val dialog = layoutInflater.inflate(R.layout.dialog_box,null)
        val input = dialog.findViewById<EditText>(R.id.dialogInputText)

        val builder = AlertDialog.Builder(this)
        builder.setMessage("The message")
            .setPositiveButton("Positive"){d,which ->
                messToToast = input.text.toString()
            }
            .setNegativeButton("Negative"){d, which ->  }
            .setView(dialog)
        builder.create().show()
    }
}

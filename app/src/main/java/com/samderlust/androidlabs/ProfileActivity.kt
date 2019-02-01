package com.samderlust.androidlabs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_profile.*
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap





class ProfileActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1

    fun onTakePicture(v: View){
        dispatchTakePictureIntent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val email  = intent.getStringExtra("email")
        
        println(" EMAIL #########   " +email)

        profileEmailField.setText(email)
    }

    fun dispatchTakePictureIntent() {
    val takePictureIntent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
}


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data?.extras
            val imageBitmap = extras?.get("data") as Bitmap
            yourPicture.setImageBitmap(imageBitmap)
        }
    }



}

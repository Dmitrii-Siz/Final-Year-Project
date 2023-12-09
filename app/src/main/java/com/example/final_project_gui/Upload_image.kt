package com.example.final_project_gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

class UploadImagePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploadimagepage)

        val displayImg = findViewById<ImageView>(R.id.display_image)

        val singleImagePickerBtn = findViewById<Button>(R.id.chooseImage)

        val singlePhotoPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            uri -> displayImg.setImageURI(uri);
        }

        singleImagePickerBtn.setOnClickListener{
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    //navigate to the home page:
    fun backButton(view: View?){
        println("Back button pressed")
        startActivity(Intent(this,MainActivity::class.java))
    }
}
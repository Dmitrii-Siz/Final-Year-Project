package com.example.final_project_gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //navigate to the upload button page:
    fun uploadButton(view: View?){
        println("Upload pressed")
        startActivity(Intent(this,UploadImagePage::class.java));
    }

    fun scanButton(view: View?){
        println("Scan pressed")
    }
}
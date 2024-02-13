package com.example.final_project_gui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.VideoView

class MainActivity : AppCompatActivity() {

    //Animation Initial Variables:
    // declaring a null variable for VideoView
    var playAnimation: VideoView? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Link to the xml:
        playAnimation = findViewById<View>(R.id.playAnimation) as VideoView
        //Path to the first animation:
        val welcomeAnimationPath = Uri.parse("android.resource://" + packageName + "/" + R.raw.welcome_scene)
        playAnimation!!.setVideoURI(welcomeAnimationPath)
        //Makes the animation loop infinitely

        //Jumping animation finished, revert back to the previous one
        playAnimation!!.setOnCompletionListener {
            playAnimation!!.setVideoURI(welcomeAnimationPath)
            playAnimation!!.start()
        }

        //Interaction with the Animation:
        playAnimation!!.setOnClickListener {
            if (playAnimation!!.isPlaying) {
                //Jump animation loaded:
                val jumpAnimationPath = Uri.parse("android.resource://" + packageName + "/" + R.raw.jump_scene)
                playAnimation!!.pause()
                //Jump animation played:
                playAnimation!!.setVideoURI(jumpAnimationPath)
                playAnimation!!.start()
            } else {
                playAnimation!!.start()
            }
        }
        // Request focus and start the video
        playAnimation!!.requestFocus()
        playAnimation!!.start()
    }

    //navigate to the upload button page:
    fun uploadButton(view: View?){
        println("Upload pressed")
        playAnimation!!.pause()
        startActivity(Intent(this,UploadImagePage::class.java))
    }

    fun scanButton(view: View?){
        println("Scan pressed")
        //playAnimation!!.pause()
    }
}
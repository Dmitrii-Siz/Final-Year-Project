package com.example.finalprojectwithanimation


import android.net.Uri
import android.os.Bundle
import android.os.Handler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController

import android.widget.VideoView
import androidx.fragment.app.Fragment


/**
 * Welcome back Fragment:
 */
class WelcomeBackFragment : Fragment() {

    //Aimation stuff:
    private var vidview: VideoView? = null
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val screenWidthDp = resources.configuration.screenHeightDp
        //Displaying different fragments depending on the height of the screen:
        val layoutResId = when {
            screenWidthDp >= 650 -> R.layout.fragment_welcome_back
            else -> R.layout.fragment_welcome_back_small
        }
        val view2 = inflater.inflate(layoutResId, container, false)

        //Load the Animation:
        vidview = view2.findViewById(R.id.videoView2)
        val videoPath = "android.resource://${requireContext().packageName}/${R.raw.welcome_back_anim}"
        val imgView: ImageView = view2.findViewById(R.id.temp)

        //set the media Controller:
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(vidview!!)
        vidview!!.setVideoURI(Uri.parse(videoPath))
        vidview!!.requestFocus()
        vidview!!.start()
        //delay the appearance of the animation video:
        vidview!!.visibility = View.VISIBLE
        Handler().postDelayed({
            imgView.visibility = View.GONE

        }, 1401)

        return view2
    }

    //Code for playing the animation if the screen is minimised:
    override fun onStart() {
        super.onStart()
        vidview?.seekTo(currentPosition)
        vidview?.start()
    }

    override fun onResume() {
        super.onResume()
        if (!vidview!!.isPlaying) {
            vidview?.seekTo(currentPosition)
            vidview?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        vidview?.pause()
        currentPosition = vidview?.currentPosition ?: 0
    }

    override fun onStop() {
        vidview?.pause()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("position", currentPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position")
            vidview?.seekTo(currentPosition)
        }
    }


}
package com.example.finalprojectwithanimation

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView

/**
 * Welcome back Fragment:
 */
class WelcomeBackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view2 = inflater.inflate(R.layout.fragment_welcome_back, container, false)

        //Load the Animation:
        val vidview: VideoView = view2.findViewById(R.id.videoView2)
        val videoPath = "android.resource://${requireContext().packageName}/${R.raw.welcome_back_anim}"

        //set the media Controller:
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(vidview)
        vidview.setVideoURI(Uri.parse(videoPath))
        vidview.requestFocus()
        vidview.start()

        return view2
    }

}
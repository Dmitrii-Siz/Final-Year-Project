package com.example.finalprojectwithanimation

import android.net.Uri
import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.widget.AppCompatButton

/**
 * Initial Fragment:
 */

class InitialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //XML file:
        val view = inflater.inflate(R.layout.fragment_initial, container, false)

        //Locate both buttons:
        val yesButton: AppCompatButton = view.findViewById(R.id.yesButton)
        val noButton: AppCompatButton = view.findViewById(R.id.noButton)

        //Load the Animation:
        val videoview: VideoView = view.findViewById(R.id.videoView)
        val videoPath = "android.resource://${requireContext().packageName}/${R.raw.welcome_first_time}"

        //set the media Controller:
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoview)
        videoview.setVideoURI(Uri.parse(videoPath))
        videoview.requestFocus()
        videoview.start()

        // Load the sad animation:
        val sadVideoPath = "android.resource://${requireContext().packageName}/${R.raw.sad_anim}"


        //onClick listeners for both buttons:n
        yesButton.setOnClickListener {
            //Navigate to the Yes Fragment:
            val yesFragment = YesFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, yesFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        noButton.setOnClickListener {
            //Hiding the buttons:
            yesButton.visibility = View.GONE
            noButton.visibility = View.GONE

            //Replacing the previous animation:
            videoview.setVideoURI(Uri.parse(sadVideoPath))
            videoview.requestFocus()
            videoview.start()

            // Update the text message:
            val messageTextView: TextView = view.findViewById(R.id.messageTextView)
            messageTextView.text = "Alright, either way, enjoy your exploration!"


        }


        return view
    }

}
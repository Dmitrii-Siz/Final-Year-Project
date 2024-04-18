package com.example.finalprojectwithanimation

import android.net.Uri
import  android.os.Bundle
import android.os.Handler
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        //--------------------------------------------------------------------
        //XML file:
        val screenWidthDp = resources.configuration.screenHeightDp
        //Displaying different fragments depending on the height of the screen:
        val layoutResId = when {
            screenWidthDp >= 650 -> R.layout.fragment_initial
            else -> R.layout.fragment_initial_small
        }
        val view = inflater.inflate(layoutResId, container, false)

        //Locate both buttons:
        val yesButton: AppCompatButton = view.findViewById(R.id.yesButton)
        val noButton: AppCompatButton = view.findViewById(R.id.noButton)

        //Load the Animation:
        val videoview: VideoView = view.findViewById(R.id.videoView)
        val videoPath = "android.resource://${requireContext().packageName}/${R.raw.welcome_first_time}"
        val imgView: ImageView = view.findViewById(R.id.temp)

        //set the media Controller:
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoview)
        videoview.setVideoURI(Uri.parse(videoPath))
        videoview.requestFocus()
        videoview.start()
        //delay the appearance of the animation video:
        videoview.visibility = View.VISIBLE
        Handler().postDelayed({
            imgView.visibility = View.GONE

        }, 1401)

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
            videoview.setOnCompletionListener {
                messageTextView.text = "Either way, enjoy your exploration!"
            }
            Handler().postDelayed({
                messageTextView.text = "Alright..."

            }, 2500)

        }


        return view
    }

}
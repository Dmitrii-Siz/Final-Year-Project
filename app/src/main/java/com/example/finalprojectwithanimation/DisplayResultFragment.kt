package com.example.finalprojectwithanimation

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL


private const val ARG_IMAGE = "param1"

/**
 Fragment for processing the image that was passed in via Upload/Camera
 */
class DisplayResultFragment : Fragment() {

    private var imageBitmap: Bitmap? = null//save the image

    private var displayOut: TextView? = null

    //Aimation stuff:
    private var videoView: VideoView? = null
    private var currentPosition = 0

    //default method:
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenWidthDp = resources.configuration.screenHeightDp
        //Displaying different fragments depending on the height of the screen:
        val layoutResId = when {
            screenWidthDp >= 650 -> R.layout.fragment_display_result
            else -> R.layout.fragment_display_result_small
        }
        // Inflate the layout for this fragment
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //save image from arguments to imageBitmap
        imageBitmap = arguments?.getParcelable(ARG_IMAGE)

        //videoView:
        videoView = view.findViewById(R.id.videoViewDisplay)
        val imgView: ImageView = view.findViewById(R.id.temp)

        //animal name textView:
        displayOut = view.findViewById<TextView>(R.id.output)
        requireActivity().runOnUiThread {displayOut?.visibility = View.GONE}

        //display the bitmap image in viewImage:
        imageBitmap?.let { bitmap ->
            view.findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
        }
        //resize the image to match the model's input parameters:
        val scaledBitmap = preprocessImage(imageBitmap!!)


        //Animation Stuff:
        // Set video path to the first MP4 file
        videoView!!.setVideoPath("android.resource://${requireContext().packageName}/${R.raw.thinking_anim}")
        //onComplete Listener for 1st animation:
        videoView!!.setOnCompletionListener {
            // Switch to the second MP4 file
            videoView!!.setVideoPath("android.resource://${requireContext().packageName}/${R.raw.conclusion_anim}")
            //onComplete Listener for 2nd animation:
            videoView!!.setOnPreparedListener { mediaPlayer ->
                //playing the 2nd animation once the first one is done
                mediaPlayer.start()
                videoView!!.setOnCompletionListener {
                    // Make displayOut visible
                    requireActivity().runOnUiThread {displayOut?.visibility = View.VISIBLE}
                }
            }
        }
        //playing the 1st animation
        videoView!!.start()
        //delay the appearance of the animation video:
        videoView!!.visibility = View.VISIBLE
        Handler().postDelayed({
            imgView.visibility = View.GONE

        }, 1301)

        //converting the image into a byte array:
        val byteArrayImage = bitmapToByteArray(scaledBitmap)


        //Connecting to the server and getting a response:
        SendImageTask(byteArrayImage)

    }


    //Function to convert the image to a byte array
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    //Function to preprocess the image - resize it
    private fun preprocessImage(bitmap: Bitmap): Bitmap {
        // Resize image to 255x255
        return Bitmap.createScaledBitmap(bitmap, 255, 255, false)
    }

    private fun SendImageTask(imageData: ByteArray){
        //request url
        val secret = getString(R.string.url)
        val url = URL(secret)

        //Global scope - performs operations on a background I/O thread
        GlobalScope.launch(Dispatchers.IO) {
            var response = ""
            val connection = url.openConnection() as HttpURLConnection
            connection.apply {
                // Set request method
                requestMethod = "POST"
                // Set request headers
                setRequestProperty("Content-Type", "application/octet-stream")//sending byte array
                setRequestProperty("charset", "utf-8")
                setRequestProperty("Content-Length", imageData.size.toString())
                setRequestProperty("Connection", "Keep-Alive")
                // Enable input and output streams
                doOutput = true
                doInput = true
            }

            //Write image data to output stream
            connection.outputStream.use { outputStream ->
                outputStream.write(imageData)
            }

            //Get response from the server:
            response = connection.inputStream.bufferedReader().use {
                it.readText()
            }

            var output = processOutput(response)

            requireActivity().runOnUiThread {
                //Displaying the predicted animal
                displayOut?.text = "I have detected a ${output}"
            }

        }
    }

    //Currently only filters out the output by removing the "" marks
    //in the future this could be a function to validate the response from the server
    private fun processOutput(response: String): String {
        // Removing surrounding quotes
        var processedResponse = response.removeSurrounding("\"")
        // Capitalizing the string
        processedResponse = processedResponse.capitalize()
        return processedResponse
    }

    //static method (responsible for processing the arguments that were passed into the fragment):
    companion object {
        fun newInstance(param1: Bitmap): DisplayResultFragment {
            val fragment = DisplayResultFragment()
            val args = Bundle()
            args.putParcelable(ARG_IMAGE, param1)
            fragment.arguments = args
            return fragment
        }
    }

    //Code for playing the animation if the screen is minimised:
    override fun onStart() {
        super.onStart()
        videoView?.seekTo(currentPosition)
        videoView?.start()
    }

    override fun onResume() {
        super.onResume()
        if (!videoView!!.isPlaying) {
            videoView?.seekTo(currentPosition)
            videoView?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoView?.pause()
        currentPosition = videoView?.currentPosition ?: 0
    }

    override fun onStop() {
        videoView?.pause()
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
            videoView?.seekTo(currentPosition)
        }
    }


}
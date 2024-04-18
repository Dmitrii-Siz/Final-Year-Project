package com.example.finalprojectwithanimation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

//initial variables:
private const val CAMERA_PERMISSION_REQUEST_CODE = 123
private const val REQUEST_IMAGE_CAPTURE = 1
/**
 Fragment for opening the Camera
 required: users permission in the manifest (Camera permissions)
 */
class CameraFragment : Fragment() {
    //global variables:
    private lateinit var nextButton: Button//next fragment button
    private var bitmapImage: Bitmap? = null//image
    private lateinit var captureButton: Button //Global variable
    private lateinit var imageView: ImageView //global variable for the image
    private lateinit var description: TextView //global variable for the description

    //default onCreate fragment method:
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenWidthDp = resources.configuration.screenHeightDp
        //Displaying different fragments depending on the height of the screen:
        val layoutResId = when {
            screenWidthDp >= 650 -> R.layout.fragment_camera
            else -> R.layout.fragment_camera_small
        }
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hide the button to continue onto next fragment:
        nextButton = view.findViewById(R.id.nextButton)
        nextButton.visibility = View.GONE

        //button to take the picture
        captureButton = view.findViewById(R.id.captureButton)

        //default image for the imageView:
        imageView = requireView().findViewById(R.id.image)
        imageView.setImageResource(R.drawable.kev_with_camera)

        //description text view:
        description = view.findViewById(R.id.description)



        //event listener for the take photo button:
        captureButton.setOnClickListener {
            //checks for user's permissions:
            if (checkPermissions()) {
                takePhoto()
            } else {
                requestPermission()
            }
        }

        //event listener for nextButton:
        nextButton.setOnClickListener {
            //quick check that the bitmap actually exists:
            if (bitmapImage != null) {
                val displayResultFragment = DisplayResultFragment.newInstance(bitmapImage!!)
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, displayResultFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

    }

    //checks if the user has granted permissions to access the camera
    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    //requests permissions from the user:
    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }


    //asks for users permissions:
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            //granted permission:
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto()
            }
            //permission wasn't granted message:
            else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //take the photo:
    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    //method that's called when startActivityForResult returns true:
    //displays the next button
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //where the capture will be displayed
            val imageView: ImageView = requireView().findViewById(R.id.image)
            //thumbnail size of the image:
            bitmapImage = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmapImage)
            //make the next button visible and accessible:
            nextButton.visibility = View.VISIBLE
            description.visibility = View.GONE
            captureButton.text = "Retake Photo"
        }
    }
}
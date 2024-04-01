package com.example.finalprojectwithanimation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import java.io.IOException


/**
 Gallery Fragment to upload an image from gallery and send it to Display Fragment
 */
class GalleryFragment : Fragment() {

    //global variables:
    private lateinit var nextButton: Button//next fragment button
    private var bitmapImage: Bitmap? = null//image

    //Choose photo button:
    private lateinit var singleImagePickerBtn: Button

    //variable to store selected image URI
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set the default image and locate the ImageView:
        val image = view.findViewById<ImageView>(R.id.image)
        image.setImageResource(R.drawable.kev_gallery)

        singleImagePickerBtn = view.findViewById<Button>(R.id.choosePhoto)
        //hide the button to navigate onto the next fragment:
        nextButton = view.findViewById(R.id.nextButton)
        nextButton.visibility = View.GONE

        //set the image to the selected image
        val singlePhotoPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            //displays the image
            selectedImageUri = uri
            image.setImageURI(uri)


            //convert the image URI to bitmap:
            selectedImageUri?.let { uri ->
                bitmapImage = uriToBitmap(uri)
                if (bitmapImage != null) {
                    //make the next button visible and accessible:
                    nextButton.visibility = View.VISIBLE
                    singleImagePickerBtn.text = "Choose Another"
                }
                else{
                    println("There is an Error with converting image URI to bitmap")
                }
            }
        }

        //event listener for the choose image button:
        singleImagePickerBtn.setOnClickListener{
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        //event listener for nextButton:
        nextButton.setOnClickListener {
            val displayResultFragment = DisplayResultFragment.newInstance(bitmapImage!!)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, displayResultFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    // Function to convert URI to Bitmap
    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            inputStream?.use { stream ->
                Bitmap.createBitmap(BitmapFactory.decodeStream(stream))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}
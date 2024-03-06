package com.example.finalprojectwithanimation

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
//ml model stuff:
import com.example.finalprojectwithanimation.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

private const val ARG_IMAGE = "param1"

/**
 Fragment for processing the image that was passed in via Upload/Camera
 */
class DisplayResultFragment : Fragment() {

    private var imageBitmap: Bitmap? = null//save the image

    //default method:
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //save image from arguments to imageBitmap
        imageBitmap = arguments?.getParcelable(ARG_IMAGE)

        //display the bitmap image in viewImage:
        imageBitmap?.let { bitmap ->
            view.findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
        }
        //resize the image to match the model's input parameters:
        val scaledBitmap = imageBitmap?.let { Bitmap.createScaledBitmap(it, 255, 255, false) }

        //model itself:
        val model = Model.newInstance(requireContext())

        val tensorImg = TensorImage(DataType.FLOAT32)
        tensorImg.load(scaledBitmap)//pass the img to the tensorImage
        val byteBuffer = tensorImg.buffer//load the image from the buffer

        //input feature - matches the model one:
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 255, 255, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        //generates model output:
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        //closes the model
        model.close()

        //list of outputs:
        val animalNames = listOf(
            "badger", "bat", "bee", "beetle", "butterfly", "cat", "caterpillar", "cockroach", "cow", "crab", "crow", "deer", "dog", "donkey", "dragonfly", "duck", "eagle", "fly", "fox", "goat", "goose", "grasshopper", "hamster", "hare", "hedgehog", "horse", "ladybugs", "lobster", "mosquito", "moth", "mouse", "otter", "owl", "parrot", "pig", "pigeon", "racoon", "rat", "sheep", "snake", "sparrow", "squirrel", "swan", "turkey", "turtle", "woodpecker"
        )

        //find index with highest probability:
        var maxProbability = Float.MIN_VALUE
        var predictedClassIndex = -1
        //get the predicted class:
        for (i in outputFeature0.floatArray.indices) {
            if (outputFeature0.floatArray[i] > maxProbability) {
                maxProbability = outputFeature0.floatArray[i]
                predictedClassIndex = i
            }
        }
        //get the predicted animal name
        val predictedAnimalName = if (predictedClassIndex != -1) {
            animalNames.getOrElse(predictedClassIndex) { "Unknown" }
        } else {
            "Unknown"
        }
        //display the animal name:
        val displayOut = view.findViewById<TextView>(R.id.output)
        displayOut.text = "Animal Detected: $predictedAnimalName"//maybe I will add some text here in the future

        println("Probabilities:")
        for (i in outputFeature0.floatArray.indices) {
            val probability = outputFeature0.floatArray[i]
            val animalName = animalNames.getOrElse(i) { "Unknown" }
            println("$animalName: $probability")
        }
    }

    //static method (responsible for processing the arguments that were passed into the fragment):
    companion object {
        @JvmStatic
        fun newInstance(param1: Bitmap): DisplayResultFragment {
            val fragment = DisplayResultFragment()
            val args = Bundle()
            args.putParcelable(ARG_IMAGE, param1)
            fragment.arguments = args
            return fragment
        }
    }
}
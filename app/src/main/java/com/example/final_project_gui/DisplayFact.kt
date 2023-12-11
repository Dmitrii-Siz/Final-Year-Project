package com.example.final_project_gui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.final_project_gui.ml.ConvertedModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.InputStream

class DisplayFact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_fact)

        //retrieve the passed img:
        val image_uri_string = intent.getStringExtra("imageUri")

        //access the passed image:
        if (image_uri_string != null) {
            // Convert the image URI string back to Uri
            val image_uri = Uri.parse(image_uri_string)

            // Load the image into a Bitmap (you'll need to import the necessary classes)
            val inputStream: InputStream? = contentResolver.openInputStream(image_uri)
            val img_bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)

            // Resize the image
            val scaledBitmap = img_bitmap?.let { Bitmap.createScaledBitmap(it, 256, 256, true) }

            // pass through the imported model:
            val model = ConvertedModel.newInstance(getApplicationContext())

            val tensorImg = TensorImage(DataType.FLOAT32)
            tensorImg.load(scaledBitmap)//pass the img to the tensorImage
            val byteBuffer = tensorImg.buffer//load the image from the buffer

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            // Releases model resources if no longer used.
            model.close()
            // Close the input stream
            inputStream?.close()

            //current list of animals:
            val animalNames = listOf(
                "antelope", "badger", "bat", "bear", "bee", "beetle", "bison", "boar", "butterfly", "cat",
                "caterpillar", "chimpanzee", "cockroach", "cow", "coyote", "crab", "crow", "deer", "dog",
                "dolphin", "donkey", "dragonfly", "duck", "eagle", "elephant", "flamingo", "fly", "fox",
                "goat", "goldfish", "goose", "gorilla", "grasshopper", "hamster", "hare", "hedgehog",
                "hippopotamus", "hornbill", "horse", "hummingbird", "hyena", "jellyfish", "kangaroo", "koala",
                "ladybugs", "leopard", "lion", "lizard", "lobster", "mosquito", "moth", "mouse", "octopus",
                "okapi", "orangutan", "otter", "owl", "ox", "oyster", "panda", "parrot", "pelecaniformes",
                "penguin", "pig", "pigeon", "porcupine", "possum", "raccoon", "rat", "reindeer", "rhinoceros",
                "sandpiper", "seahorse", "seal", "shark", "sheep", "snake", "sparrow", "squid", "squirrel",
                "starfish", "swan", "tiger", "turkey", "turtle", "whale", "wolf", "wombat", "woodpecker", "zebra"
            )

            // Find the index with the highest probability
            var maxProbability = Float.MIN_VALUE
            var predictedClassIndex = -1

            for (i in outputFeature0.floatArray.indices) {
                if (outputFeature0.floatArray[i] > maxProbability) {
                    maxProbability = outputFeature0.floatArray[i]
                    predictedClassIndex = i
                }
            }

            // Get the predicted animal name
            val predictedAnimalName = if (predictedClassIndex != -1) {
                animalNames.getOrElse(predictedClassIndex) { "Unknown" }
            } else {
                "Unknown"
            }

            // Display the predicted animal name:
            val displayOut = findViewById<TextView>(R.id.output)
            displayOut.text = "$predictedAnimalName"


        }
    }

    //navigate to the upload page:
    fun backButton(view: View?){
        println("Back button pressed")
        startActivity(Intent(this,UploadImagePage::class.java))
    }
}
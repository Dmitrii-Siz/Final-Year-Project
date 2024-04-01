package com.example.finalprojectwithanimation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


/**
 * Review Quiz Fragment:
 */
class ReviewFragment : Fragment() {
    private var improvementList: ArrayList<Fact>? = null//global var

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //XML
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        //Retrieve data from arguments
        arguments?.let {
            improvementList = it.getParcelableArrayList(ARG_IMPROVEMENT_LIST)
        }

        // Find the TextViews and Button:

        val questionNumber: TextView = view.findViewById(R.id.questionNumber)
        val questionText: TextView = view.findViewById(R.id.questionText)
        val userAnswer: TextView = view.findViewById(R.id.userAnswer)
        val correctAnswer: TextView = view.findViewById(R.id.correctAnswer)
        val explanation: TextView = view.findViewById(R.id.explanation)

        val nextButton = view.findViewById<Button>(R.id.nextButton)

        //looping through the Arraylist:
        var index = 0//curr index
        var fact = improvementList?.get(index)

        //First Wrong Question:
        questionNumber.text = "Question ${index+1}/${improvementList?.size}"
        questionText.text = fact?.text
        //correct answer was inverted (user got the question wrong)
        if (fact != null) {
            userAnswer.text = if (fact.isTrue) "False" else "True"
            correctAnswer.text = if (fact?.isTrue == true) "True" else "False"
        }
        explanation.text = "Explanation: ${fact?.answer}"


        nextButton.setOnClickListener {
            index += 1
            if (index < improvementList!!.size) {
                fact = improvementList?.get(index)
                questionNumber.text = "Question ${index + 1}/${improvementList?.size}"
                questionText.text = fact?.text
                //correct answer was inverted (user got the question wrong)
                userAnswer.text = if (fact?.isTrue == true) "False" else "True"

                //correct answer:
                correctAnswer.text = if (fact?.isTrue == true) "True" else "False"
                explanation.text = "Explanation: ${fact?.answer}"

                //button visibility:
                nextButton.visibility = if (index+1 >= improvementList!!.size) View.GONE else View.VISIBLE
            }
        }


        return view
    }

    companion object {
        private const val ARG_IMPROVEMENT_LIST = "improvementList"

        fun newInstance(improvementList: ArrayList<Fact>): ReviewFragment {
            val fragment = ReviewFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_IMPROVEMENT_LIST, improvementList)
            }
            fragment.arguments = args
            return fragment
        }
    }
}


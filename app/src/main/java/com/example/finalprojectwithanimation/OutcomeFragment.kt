package com.example.finalprojectwithanimation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView


/**
 * Fragment to Display the user performed in the Quiz
 */
class OutcomeFragment : Fragment() {
    private lateinit var improvementList: List<String>
    private var totalCorrect: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_outcome, container, false)

        //Retrieve data from arguments
        arguments?.let {
            improvementList = it.getStringArrayList(ARG_IMPROVEMENT_LIST) ?: emptyList()
            totalCorrect = it.getInt(ARG_TOTAL_CORRECT)
        }

        //locate the score textView and the Fact Display container:
        val scoreTextView: TextView = view.findViewById(R.id.scoreTextView)
        val incorrectFactsContainer: LinearLayout = view.findViewById(R.id.incorrectFactsContainer)
        //Update the Score & Display the Correct facts in the container:
        scoreTextView.text = "Score: $totalCorrect/10"
        for (fact in improvementList) {
            val factTextView = TextView(requireContext())
            factTextView.text = fact
            incorrectFactsContainer.addView(factTextView)
        }

        return view
    }

    //New instance function to enable arguments to be passed in:
    companion object {
        private const val ARG_IMPROVEMENT_LIST = "improvementList"
        private const val ARG_TOTAL_CORRECT = "totalCorrect"

        fun newInstance(improvementList: List<String>, totalCorrect: Int): OutcomeFragment {
            val fragment = OutcomeFragment()
            val args = Bundle().apply {
                putStringArrayList(ARG_IMPROVEMENT_LIST, ArrayList(improvementList))
                putInt(ARG_TOTAL_CORRECT, totalCorrect)
            }
            fragment.arguments = args
            return fragment
        }
    }
}

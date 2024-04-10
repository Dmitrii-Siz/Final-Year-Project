package com.example.finalprojectwithanimation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat


/**
 * Fragment to Display the user performed in the Quiz
 */
class OutcomeFragment : Fragment() {
    private var improvementList: ArrayList<Fact>? = null
    private var totalCorrect: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_outcome, container, false)

        //Retrieve data from arguments
        arguments?.let {
            improvementList = it.getParcelableArrayList(ARG_IMPROVEMENT_LIST)
            totalCorrect = it.getInt(ARG_TOTAL_CORRECT)
        }


        //Review Button:
        val reviewButton = view.findViewById<Button>(R.id.reviewButton)

        //locate the message at the top (encouragement message and outcome message):
        val outcomeMessage: TextView = view.findViewById(R.id.outcomeMessage)
        val encouragementM: TextView = view.findViewById(R.id.encouragementMessage)

        //locate the progress bar and the percentage text inside:
        val progressBar = view.findViewById<ProgressBar>(R.id.circularProgressBar)
        val percentageTextView = view.findViewById<TextView>(R.id.percentageTextView)

        //Update the Message Depending on the Score with a when statement
        //Update the progress bar colour and encouragement message alongside:
        when {
            (totalCorrect > 7) -> {
                progressBar.progressDrawable.setTint(ContextCompat.getColor(requireContext(), R.color.green_700))
            }
            (totalCorrect in 5..7) -> {
                outcomeMessage.text = "Keep Pushing Forward!"
                outcomeMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange_800))
                encouragementM.text = "Not bad! You're on the right track to becoming a certified animal expert"
                progressBar.progressDrawable.setTint(ContextCompat.getColor(requireContext(), R.color.orange_800))
            }
            (totalCorrect < 5) -> {
                outcomeMessage.text = "Don't Give Up!"
                outcomeMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_700))
                encouragementM.text = "Every setback is a setup for a comeback. Keep learning and growing, and you'll soon become a true animal expert!"
                progressBar.progressDrawable.setTint(ContextCompat.getColor(requireContext(), R.color.red_700))
            }
            //the 80% Score and above is the default green colour and default message
        }
        //Update the Score & Display the Correct facts in the container:
        progressBar.progress = totalCorrect
        percentageTextView.text = "${totalCorrect*10}%"

        //In case the user gets 100% hide the review button:
        reviewButton.visibility = if (10 == totalCorrect) View.GONE else View.VISIBLE

        //Button eventListener:
        reviewButton.setOnClickListener {
            //empty list is passed in case the improvementList is null
            val reviewFragment = ReviewFragment.newInstance(improvementList!!)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, reviewFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    //New instance function to enable arguments to be passed in:
    companion object {
        private const val ARG_IMPROVEMENT_LIST = "improvementList"
        private const val ARG_TOTAL_CORRECT = "totalCorrect"

        fun newInstance(improvementList: ArrayList<Fact>, totalCorrect: Int): OutcomeFragment {
            val fragment = OutcomeFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_IMPROVEMENT_LIST, improvementList)
                putInt(ARG_TOTAL_CORRECT, totalCorrect)
            }
            fragment.arguments = args
            return fragment
        }
    }
}

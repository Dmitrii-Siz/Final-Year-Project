package com.example.finalprojectwithanimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class LoadGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loadgame, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Buttons:
        val playButton: Button = view.findViewById(R.id.playButton)
        val rulesButton: Button = view.findViewById(R.id.rulesButton)

        //popup fragment:
        val rulesPopupFragment = RulesPopupFragment()

        //Button event listener
        playButton.setOnClickListener {
            //Navigate to the GameFragment
            val gameFragment = GameFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, gameFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        //How to play Button event listener:
        rulesButton.setOnClickListener {
            //Displaying the popup:
            requireActivity().supportFragmentManager.beginTransaction()
                .add(android.R.id.content, rulesPopupFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}

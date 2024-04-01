package com.example.finalprojectwithanimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class RulesPopupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //XML:
        val view = inflater.inflate(R.layout.fragment_rules_popup, container, false)
        //transparent background View:
        val backgroundView = view.findViewById<View>(R.id.backgroundView)
        //close button:
        val closeButton: Button = view.findViewById(R.id.closeButton)
        //onclick listener for the transparent view
        backgroundView.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@RulesPopupFragment).commit()
        }
        //Close button on click listener:
        closeButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@RulesPopupFragment).commit()
        }
        return view
    }
}

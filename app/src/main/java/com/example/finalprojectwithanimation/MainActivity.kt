package com.example.finalprojectwithanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initial fragment (First fragment that will appear on the page):
        val initialFragment = InitialFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, initialFragment)
            .commit()

        //bottom navigation:
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //Deselect all items in BottomNavigationView
        bottomNavigationView.menu.setGroupCheckable(0, false, true)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.camera -> {
                    replaceFragment(CameraFragment())
                    true
                }
                R.id.gallery -> {
                    replaceFragment(GalleryFragment())
                    true
                }
                R.id.teach -> {
                    replaceFragment(TeachFragment())
                    true
                }
                else -> false
            }
        }
    }

    //Replaces the fragment inside the frame layout with the new fragment that the user clicked on
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }
}
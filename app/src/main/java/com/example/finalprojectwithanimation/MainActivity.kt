package com.example.finalprojectwithanimation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disableNightMode()
        setContentView(R.layout.activity_main)


        //First time opening the app?
        val preferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val alreadyOpened = preferences.getString("AlreadyOpened", "")


        if(alreadyOpened.equals("Yes")){
            //Not the first time of the user opening the App:
            val editor = preferences.edit()
            editor.putString("AlreadyOpened", "")
            editor.apply()
            //Welcome back fragment:
            val welcomebackFragment = WelcomeBackFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, welcomebackFragment)
                .commit()
        }else{
            //First time of the user opening the App:
            val editor = preferences.edit()
            editor.putString("AlreadyOpened", "Yes")
            editor.apply()

            //First Time fragment:
            val initialFragment = InitialFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, initialFragment)
                .commit()
        }

        //bottom navigation:
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //Deselect all items in BottomNavigationView
        bottomNavigationView.menu.setGroupCheckable(0, true, false)
        bottomNavigationView.menu.apply {
            findItem(R.id.camera).isChecked = false
            findItem(R.id.gallery).isChecked = false
            findItem(R.id.teach).isChecked = false
        }
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.camera -> {
                    replaceFragment(CameraFragment())
                    bottomNavigationView.menu.findItem(R.id.teach).isChecked = false
                    bottomNavigationView.menu.findItem(R.id.camera).isChecked = true
                    bottomNavigationView.menu.findItem(R.id.gallery).isChecked = false
                    true
                }
                R.id.gallery -> {
                    replaceFragment(GalleryFragment())
                    bottomNavigationView.menu.findItem(R.id.teach).isChecked = false
                    bottomNavigationView.menu.findItem(R.id.camera).isChecked = false
                    bottomNavigationView.menu.findItem(R.id.gallery).isChecked = true
                    true
                }
                R.id.teach -> {
                    replaceFragment(LoadGameFragment())
                    bottomNavigationView.menu.findItem(R.id.teach).isChecked = true
                    bottomNavigationView.menu.findItem(R.id.camera).isChecked = false
                    bottomNavigationView.menu.findItem(R.id.gallery).isChecked = false
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

    private fun disableNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
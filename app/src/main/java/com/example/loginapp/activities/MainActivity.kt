package com.example.loginapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.loginapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottonNavigationView: BottomNavigationView
    private lateinit var appTitleView: MaterialToolbar
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var textColor : String
    private lateinit var backgroundColor : String
    val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref: SharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        appTitleView = findViewById(R.id.app_title)
        bottonNavigationView = findViewById(R.id.bottom_navigation)
        setUpColors(sharedPref)
        NavigationUI.setupWithNavController(bottonNavigationView, navHostFragment.navController)
    }

    public fun setTitleText(title: String){
        appTitleView.title = title
    }

    public fun setUpColors(sharedPref: SharedPreferences){
        var backgroundColorVal = sharedPref.getString("backgroundColor", "#6197AC")
        var textColorVal: String? = sharedPref.getString("textColor", "#403E3E")

        if (backgroundColorVal != null) {
            backgroundColor = backgroundColorVal
            appTitleView.setBackgroundColor(Color.parseColor(backgroundColorVal))
        }
        if (textColorVal != null) {
            textColor = textColorVal
            appTitleView.setTitleTextColor(Color.parseColor(textColor))
        }
    }


    public fun openGalleryForImage(activityResult: ActivityResultLauncher<Intent>, context: Context) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activityResult.launch(intent)
    }

}
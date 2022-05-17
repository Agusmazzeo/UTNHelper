package com.example.loginapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class Settings : Fragment() {

    private lateinit var v : View
    private lateinit var themeSelectorSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_settings, container, false)
        themeSelectorSwitch = v.findViewById(R.id.theme_selector_switch)
        return v
    }

    override fun onStart() {
        super.onStart()

        val context = requireContext()
        val sharedPref: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

        val activity = (activity as MainActivity)
        if (activity != null) {
            activity.setTitleText("Settings")
        }
        setThemes(sharedPref.getBoolean("darkModeOn", false), sharedPref, activity)
        themeSelectorSwitch.setChecked(sharedPref.getBoolean(
            "darkModeOn",
            false
        ))
        themeSelectorSwitch.setOnCheckedChangeListener{_ , isChecked ->
            setThemes(isChecked, sharedPref, activity)
        }
    }

    fun setThemes(darkModeOn: Boolean, sharedPref: SharedPreferences, activity: MainActivity ){
        var backgroundColor =  "#6197AC"
        var textColor = "#403E3E"
        val editor = sharedPref.edit()

        if(darkModeOn){
            backgroundColor = "#000000"
            textColor = "#FFFFFF"
        }
        editor.putBoolean("darkModeOn", darkModeOn)
        editor.putString("backgroundColor", backgroundColor)
        editor.putString("textColor", textColor)
        editor.apply()
        activity.setUpColors(sharedPref)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}
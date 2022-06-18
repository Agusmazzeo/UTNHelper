package com.example.loginapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.repository.UserRepository

class UserInfo : Fragment() {
    private lateinit var v : View
    private lateinit var name_view : EditText
    private lateinit var email_view : EditText
    private lateinit var phone_view: EditText
    private lateinit var save_button: Button
    private lateinit var settings_button: Button
    private lateinit var image_view: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_user_info, container, false)
        save_button = v.findViewById(R.id.user_info_save)
        settings_button = v.findViewById(R.id.user_info_settings)
        name_view = v.findViewById(R.id.user_name)
        email_view = v.findViewById(R.id.user_email)
        phone_view = v.findViewById(R.id.user_phone)
        image_view = v.findViewById(R.id.user_info_image_view)
        return v

    }

    override fun onStart() {
        super.onStart()

        val context = requireContext()
        val sharedPref: SharedPreferences = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val activity = (activity as MainActivity)
        if (activity != null) {
            activity.setTitleText("User Information")
        }

        name_view.setText(sharedPref.getString("UserName","")!!)
        email_view.setText(sharedPref.getString("UserEmail","")!!)
        phone_view.setText(sharedPref.getString("UserPhone","")!!)

        image_view.setOnClickListener{

        }

        save_button.setOnClickListener{
            var new_name = name_view.text.toString()
            var new_email = email_view.text.toString()
            var new_phone = phone_view.text.toString()

            if(new_name != "" && (new_email != "" || new_phone != "")){
                editor.putString("UserName", new_name)
                editor.putString("UserEmail", new_email)
                editor.putString("UserPhone", new_phone)
                editor.apply()
                findNavController().popBackStack()
            }
        }

        settings_button.setOnClickListener{
            var settingsPageAction = UserInfoDirections.actionFragmentUserInfoToSettings()
            v.findNavController().navigate(settingsPageAction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
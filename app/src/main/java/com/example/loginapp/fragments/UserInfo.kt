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
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.viewmodels.UserInfoViewModel

class UserInfo : Fragment() {
    private lateinit var v : View
    private lateinit var name_view : EditText
    private lateinit var email_view : EditText
    private lateinit var phone_view: EditText
    private var userId: String? = ""
    private lateinit var save_button: Button
    private lateinit var settings_button: Button
    private lateinit var image_view: ImageView
    private val viewModel: UserInfoViewModel by viewModels()

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

        userId = sharedPref.getString("UUID", "")
        name_view.setText(sharedPref.getString("NAME","")!!)
        email_view.setText(sharedPref.getString("EMAIL","")!!)
        phone_view.setText(sharedPref.getString("PHONE","")!!)
        var userImage = sharedPref.getString("ICON","")
        if(userImage != ""){
            Glide.with(context!!).load(userImage).into(image_view)
        }

        image_view.setOnClickListener{

        }

        save_button.setOnClickListener{
            var new_name = name_view.text.toString()
            var new_email = email_view.text.toString()
            var new_phone = phone_view.text.toString()

            if(new_name != "" && new_email != "" && new_phone != ""){
                editor.putString("NAME", new_name)
                editor.putString("EMAIL", new_email)
                editor.putString("PHONE", new_phone)
                if(userId!=null){
                    viewModel.updateUser(userId!!, new_name, new_email, new_phone)
                }
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
package com.example.loginapp.fragments.addDataPages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.viewmodels.addDataPages.AddUserViewModel

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AddUserPage : Fragment() {
    private lateinit var v : View
    private val viewModel: AddUserViewModel by viewModels()
    private lateinit var new_name_view : EditText
    private lateinit var new_email_view : EditText
    private lateinit var new_phone_view: EditText
    private lateinit var reset_button: Button
    private lateinit var submit_button: Button
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_add_user_page, container, false)
        reset_button = v.findViewById(R.id.btn_reset)
        submit_button = v.findViewById(R.id.btn_submit)
        new_name_view = v.findViewById(R.id.new_user_name)
        new_email_view = v.findViewById(R.id.new_user_email)
        new_phone_view = v.findViewById(R.id.new_user_phone)
        return v

    }

    override fun onStart() {
        super.onStart()

        val activity = (activity as MainActivity)

        if (activity != null) {
            activity.setTitleText("Add User")
        }

        reset_button.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            new_name_view.setText("")
            new_email_view.setText("")
            new_phone_view.setText("")
        }

        // set on-click listener
        submit_button.setOnClickListener {
            var new_name = new_name_view.text.toString()
            var new_email = new_email_view.text.toString()
            var new_phone = new_phone_view.text.toString()
            var assigned_course = 0

            viewModel.createUser(new_name, new_email, new_phone, assigned_course){
                findNavController().popBackStack()
            }

        }

    }



    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}
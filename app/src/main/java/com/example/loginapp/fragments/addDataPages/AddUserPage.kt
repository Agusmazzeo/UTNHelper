package com.example.loginapp.fragments.addDataPages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.activities.LoginActivity
import androidx.lifecycle.Observer
import com.example.loginapp.viewmodels.addDataPages.AddUserViewModel

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AddUserPage : Fragment() {
    private lateinit var v : View
    private val viewModel: AddUserViewModel by viewModels()
    private lateinit var user_image: ImageView
    private lateinit var new_name_view : EditText
    private lateinit var new_email_view : EditText
    private lateinit var new_phone_view: EditText
    private lateinit var new_password_view : EditText
    private lateinit var reset_button: Button
    private lateinit var submit_button: Button
    private lateinit var role_field: AutoCompleteTextView
    private var roleOptions = listOf("Student", "Teacher")
    private lateinit var activityResult: ActivityResultLauncher<Intent>

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
        new_password_view = v.findViewById(R.id.new_user_password)
        user_image = v.findViewById(R.id.user_image_view)
        role_field = v.findViewById(R.id.dropdown_menu_text)

        val adapter = context?.let { ArrayAdapter(it, R.layout.dropdown_menu_item, roleOptions) }
        if (adapter != null) {
            role_field.setAdapter(adapter)
        }
        activityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.loadUserImage(result)
        }
        return v

    }

    override fun onStart() {
        super.onStart()
        val context = context

        val activity = (activity as LoginActivity   )

        viewModel.userImageUri.observe(viewLifecycleOwner, Observer { result ->
            Glide.with(context!!).load(result).into(user_image)
        })

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
            var new_password = new_password_view.text.toString()
            var new_role = role_field.text.toString()

            viewModel.createUser(new_name, new_email, new_phone, new_password, new_role){
                findNavController().popBackStack()
            }

        }

        user_image.setOnClickListener{
            if (context != null) {
                activity.openGalleryForImage(activityResult, context)
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
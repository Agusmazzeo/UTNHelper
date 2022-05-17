package com.example.loginapp.fragments.userDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository

class BasicInfo(user: UserModel) : Fragment() {

    companion object {
        fun newInstance(user: UserModel) = BasicInfo(user)
    }
    private lateinit var v: View
    var user : UserModel = user
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var phone: EditText
    lateinit var saveButton: Button
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_details_basic_info, container, false)
        name = v.findViewById(R.id.user_detail_name)
        email = v.findViewById(R.id.user_detail_email)
        phone = v.findViewById(R.id.user_detail_phone)
        saveButton = v.findViewById(R.id.user_details_save_button)
        return v
    }

    override fun onStart() {
        super.onStart()

        val context = getContext()

        if (context != null) {
            val db = AppDatabase.getAppDataBase(context)
            if (db != null) {
                userRepository = UserRepository(db.userDao())
            }
        }
        name.setText(user.name.toString())
        email.setText(user.email.toString())
        phone.setText(user.phone.toString())

            saveButton.setOnClickListener {
                var new_name = name.text.toString()
                var new_email = email.text.toString()
                var new_phone = phone.text.toString()
                userRepository.updateUser(user.id, new_name, new_email, new_phone)
                findNavController().popBackStack()
            }
    }

}
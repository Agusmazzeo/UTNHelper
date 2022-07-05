package com.example.loginapp.fragments.userDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginapp.R
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.viewmodels.userDetails.BasicInfoViewModel

class BasicInfo(user: UserModel) : Fragment() {

    companion object {
        fun newInstance(user: UserModel) = BasicInfo(user)
    }
    private val viewModel: BasicInfoViewModel by viewModels()
    private lateinit var v: View
    var user : UserModel = user
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var phone: TextView
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_details_basic_info, container, false)
        name = v.findViewById(R.id.course_detail_name)
        email = v.findViewById(R.id.course_detail_code)
        phone = v.findViewById(R.id.course_detail_owner)
        return v
    }

    override fun onStart() {
        super.onStart()

        name.setText(user.name.toString())
        email.setText(user.email.toString())
        phone.setText(user.phone.toString())

    }

}
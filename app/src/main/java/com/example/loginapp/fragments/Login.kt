package com.example.loginapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.activities.LoginActivity
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.databinding.FragmentLoginBinding
import com.example.loginapp.fragments.dataLists.UsersListDirections
import com.example.loginapp.viewmodels.LoginActivityViewModel
import com.example.loginapp.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginFragment : Fragment() {
    lateinit var v: View
    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    lateinit var loginButton: Button
    lateinit var createButton: Button
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var userInfo: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        loginButton = v.findViewById(R.id.LoginButton)
        createButton = v.findViewById(R.id.CreateButton)
        emailText = v.findViewById(R.id.editTextName)
        passwordText = v.findViewById(R.id.editTextPassword)
        return v
    }

    override fun onStart() {
        super.onStart()
        val activity = (activity as LoginActivity)
        userInfo = activity.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
        viewModel.cleanUpUserInfo(userInfo)
        viewModel.successLogin.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }

        })
        viewModel.errorLogin.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                Snackbar.make(v, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
            }

        })

        loginButton.setOnClickListener{
            viewModel.validateUserData(emailText.text.toString(), passwordText.text.toString(), userInfo)
        }



        createButton.setOnClickListener{
            var addUserAction = LoginFragmentDirections.actionLoginFragmentToAddUserPage()
            v.findNavController().navigate(addUserAction)
        }
    }
}
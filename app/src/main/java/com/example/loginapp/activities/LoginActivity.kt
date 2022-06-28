package com.example.loginapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.loginapp.R
import com.example.loginapp.viewmodels.LoginActivityViewModel

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    lateinit var loginButton: Button
    lateinit var createButton: Button
    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.LoginButton)
        createButton = findViewById(R.id.CreateButton)
        emailText = findViewById<EditText>(R.id.editTextName)
        passwordText = findViewById<EditText>(R.id.editTextPassword)
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener{
            viewModel.validateUserData(emailText.text.toString(), passwordText.text.toString(),
                onSuccessCallback = {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, onErrorCallback = {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            )
        }

        createButton.setOnClickListener{
            viewModel.createUserAccount(emailText.text.toString(), passwordText.text.toString(),
                onSuccessCallback = {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, onErrorCallback = {
                    Toast.makeText(baseContext, "User Account creation failed.",
                        Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
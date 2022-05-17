package com.example.loginapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.loginapp.R

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var nameText: EditText
    lateinit var passwordText: EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.LoginButton)
        nameText = findViewById<EditText>(R.id.editTextName)
        passwordText = findViewById<EditText>(R.id.editTextPassword)
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener{
            if(nameText.text.length > 0 && passwordText.text.length > 0){
                val sharedPref: SharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putString("UserName", nameText.text.toString())
                editor.putString("UserPassword", passwordText.text.toString())
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
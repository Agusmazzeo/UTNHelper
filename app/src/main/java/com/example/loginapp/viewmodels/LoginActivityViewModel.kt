package com.example.loginapp.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.utils.AuthHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginActivityViewModel (app: Application) : AndroidViewModel(app){

}
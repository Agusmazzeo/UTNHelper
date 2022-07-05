package com.example.loginapp.viewmodels

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.activities.LoginActivity
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.CourseRepository
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.utils.AuthHandler
import com.example.loginapp.utils.URIPathHelper
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
    private var userRepository: UserRepository = UserRepository()

    init { }

}
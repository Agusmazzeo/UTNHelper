package com.example.loginapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.repository.CourseRepository
import com.example.loginapp.utils.URIPathHelper

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {

    init {
        val context = getApplication<Application>().applicationContext
    }
}
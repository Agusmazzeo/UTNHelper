package com.example.loginapp.viewmodels.userDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository

class BasicInfoViewModel(app: Application) : AndroidViewModel(app) {

    private lateinit var userRepository: UserRepository;

    init {
//        userRepository = UserRepository(UserDaoFB())
//        val context = getApplication<Application>().applicationContext
//        if (context != null) {
//            val db = AppDatabase.getAppDataBase(context)
//            if (db != null) {
//                userRepository = UserRepository(db.userDao())
//            }
//        }
    }

    fun updateUser(user: UserModel){
        var new_name = user.name.toString()
        var new_email = user.email.toString()
        var new_phone = user.phone.toString()
        var assigned_course = 0
//        userRepository.updateUser(user.id, new_name, new_email, new_phone, assigned_course)
    }


}
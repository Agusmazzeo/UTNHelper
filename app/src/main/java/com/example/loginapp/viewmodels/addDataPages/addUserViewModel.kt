package com.example.loginapp.viewmodels.addDataPages

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.database.firebase.UserDaoFB
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository

class AddUserViewModel (app: Application) : AndroidViewModel(app){

    private lateinit var userRepository: UserRepository;
    private var context = getApplication<Application>().applicationContext

    init {
//        userRepository = UserRepository(UserDaoFB())
//        if (context != null) {
//            val db = AppDatabase.getAppDataBase(context)
//            if (db != null) {
//                userRepository = UserRepository(db.userDao())
//            }
//        }
    }

    fun createUser(new_name: String, new_email: String, new_phone: String, assigned_course: Int, callback: ()->Unit){
        var response: Boolean = false
        if(new_name != "" && (new_email != "" || new_phone != "")){
//            userRepository.createUser(new_name, new_email, new_phone, assigned_course)
            callback()
        }
    }
}
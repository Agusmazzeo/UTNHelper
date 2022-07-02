package com.example.loginapp.viewmodels.addDataPages

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddUserViewModel (app: Application) : AndroidViewModel(app){

    private var userRepository: UserRepository =  UserRepository()

    fun createUser(new_name: String, new_email: String, new_phone: String, assigned_course: Int, callback: ()->Unit){

        if(new_name != "" && (new_email != "" || new_phone != "")){
            viewModelScope.launch {
                var result = async { userRepository.createUser(new_name, new_email, new_phone, assigned_course)}
                if(result.await()){
                    callback()
                }
            }
        }
    }
}
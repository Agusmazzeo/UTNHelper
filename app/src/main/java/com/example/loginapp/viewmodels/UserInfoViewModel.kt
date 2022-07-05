package com.example.loginapp.viewmodels
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.utils.AuthHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class UserInfoViewModel (app: Application) : AndroidViewModel(app){

    private var userRepository: UserRepository =  UserRepository()

    fun updateUser(id: String, name: String, email: String, phone: String){
        viewModelScope.launch {
            var updateTask = async { userRepository.updateUser(id, name, email, phone)}
            updateTask.await()
        }
    }
}
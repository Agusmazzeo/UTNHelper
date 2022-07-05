package com.example.loginapp.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.utils.AuthHandler
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel (app: Application) : AndroidViewModel(app){

    private var authHandler: AuthHandler = AuthHandler()
    private var userRepository: UserRepository =  UserRepository()
    var successLogin = MutableLiveData<Boolean>(false)
    var errorLogin = MutableLiveData<Boolean>(false)

    var EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun validateUserData(email: String, password: String, userInfo: SharedPreferences){
        viewModelScope.launch {
            if(email.isNotEmpty() && validateEmailFormat(email) && password.isNotEmpty()){
                var loginTask = async { authHandler.signInWithEmailAndPassword(email, password)}
                var result = loginTask.await()
                if (result != null) {
                    if(result.user != null){
                        saveCurrentUserInfo(result.user!!.uid, userInfo)
                    }
                }
                else{
                    errorLogin.value = true
                }
            }
        }
    }

    fun cleanUpUserInfo(userInfo: SharedPreferences){
        with(userInfo.edit()) {
            clear()
            apply()
        }
    }

    fun saveCurrentUserInfo(userId: String, userInfo: SharedPreferences){
        viewModelScope.launch {
            var userTask = async { userRepository.getUserById(userId) }
            var user = userTask.await()
            if(user != null){
                with(userInfo.edit()) {
                    putString("UUID", userId)
                    putString("ROLE", user.role)
                    apply()
                    successLogin.value = true
                }
            }
        }
    }

    private fun validateEmailFormat(email: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }
}
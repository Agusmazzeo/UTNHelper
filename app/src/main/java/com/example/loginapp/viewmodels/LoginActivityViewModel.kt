package com.example.loginapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.utils.AuthHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginActivityViewModel (app: Application) : AndroidViewModel(app){

    private var authHandler: AuthHandler = AuthHandler()
    var EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun validateUserData(email: String, password: String, onSuccessCallback: ()->Unit, onErrorCallback: ()->Unit){
        viewModelScope.launch {
            if(email.isNotEmpty() && validateEmailFormat(email) && password.isNotEmpty()){
                var result = async { authHandler.signInWithEmailAndPassword(email, password)}
                if(result.await()){
                    onSuccessCallback()
                }else{
                    onErrorCallback()
                }
            }
        }
    }

    fun createUserAccount(email: String, password: String, onSuccessCallback: ()->Unit, onErrorCallback: ()->Unit){
        viewModelScope.launch {
            if(email.isNotEmpty() && validateEmailFormat(email) && password.isNotEmpty()){
                var result = async { authHandler.createUserWithEmailAndPassword(email, password)}
                if(result.await()){
                    onSuccessCallback()
                }else{
                    onErrorCallback()
                }
            }
        }
    }

    private fun validateEmailFormat(email: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }
}
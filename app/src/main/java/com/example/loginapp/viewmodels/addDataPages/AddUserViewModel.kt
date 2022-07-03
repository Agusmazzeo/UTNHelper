package com.example.loginapp.viewmodels.addDataPages

import android.app.Activity
import android.app.Application
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.utils.AuthHandler
import com.example.loginapp.utils.StorageHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class AddUserViewModel (app: Application) : AndroidViewModel(app){

    private var userRepository: UserRepository =  UserRepository()
    private var authHandler = AuthHandler()
    private var storageHandler = StorageHandler()
    private var context = getApplication<Application>().applicationContext
    private lateinit var imageUri: Uri
    private var EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    var userImageUri = MutableLiveData<Uri>()


    fun createUser(new_name: String, new_email: String, new_phone: String, new_password: String, new_role: String, callback: ()->Unit){

        if(new_name.isNotEmpty()
            && new_phone.isNotEmpty()
            && new_email.isNotEmpty()
            && validateEmailFormat(new_email)
            && new_password.isNotEmpty()
            && new_role.isNotEmpty()
        ){
            viewModelScope.launch {
                var userAuthCreate = async { authHandler.createUserWithEmailAndPassword(new_email, new_password) }
                var userAuthCreateResult = userAuthCreate.await()
                if(userAuthCreateResult){
                    var userImageUrlTask = async{storageHandler.saveUserImage(new_name, userImageUri.value!!)}
                    var userImageUrl = userImageUrlTask.await()
                    var userCreate = async { userRepository.createUser(new_name, new_email, new_phone, new_role, userImageUrl.toString())}
                    var userCreateResult = userCreate.await()
                    if(userCreateResult){
                        callback()
                    }
                }
            }
        }
    }

    fun loadUserImage(result: ActivityResult){
        if (result.resultCode == Activity.RESULT_OK) {
            if (context != null) {
                imageUri = result.data?.data!!
                if (imageUri != null){
                    userImageUri.value = imageUri
                }
            }
        }
    }

    private fun validateEmailFormat(email: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }
}
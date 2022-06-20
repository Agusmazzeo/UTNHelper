package com.example.loginapp.viewmodels.dataLists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UsersListViewModel(app: Application) : AndroidViewModel(app) {
    private var userRepository: UserRepository = UserRepository()
    var usersList = MutableLiveData<MutableList<UserModel?>>()

    fun getUsers(){
        usersList.value?.clear()
        viewModelScope.launch {
            usersList.value = async {userRepository.getUsers()}.await()
        }
    }
}
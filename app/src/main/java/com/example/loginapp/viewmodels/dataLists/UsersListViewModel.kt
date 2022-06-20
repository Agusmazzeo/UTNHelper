package com.example.loginapp.viewmodels.dataLists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.database.firebase.UserDaoFB
import com.example.loginapp.entities.User
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UsersListViewModel(app: Application) : AndroidViewModel(app) {
    private var userRepository: UserRepository = UserRepository()
    var usersList = MutableLiveData<MutableList<UserModel?>>()

    init {
        viewModelScope.launch {
            var usersResult = async { getUsers() }
            usersResult.await()
        }
    }

    suspend fun getUsers(){
        usersList.value = userRepository.getUsers()
    }
}
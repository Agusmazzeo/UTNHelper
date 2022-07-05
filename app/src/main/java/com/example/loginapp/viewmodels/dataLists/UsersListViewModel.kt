package com.example.loginapp.viewmodels.dataLists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UsersListViewModel(app: Application) : AndroidViewModel(app) {
    private var userRepository: UserRepository = UserRepository()
    var usersList = MutableLiveData<MutableList<UserModel?>>()
    var currentUser = MutableLiveData<UserModel>()

    fun getUsers(){
        usersList.value?.clear()
        viewModelScope.launch {
            usersList.value = userRepository.getUsers()
        }
    }

    fun populateDataByUserRole(userId: String, userRole: String){
        usersList.value?.clear()
        when(userRole){
            "Student" -> {
                getTeachersFromEnrolledCourses(userId)
            }
            "Teacher" -> {
                getStudentsFromCourses(userId)
            }
        }
    }

    private fun getTeachersFromEnrolledCourses(userId: String){
        viewModelScope.launch {
            var teachersTask = async{userRepository.getTeachersFromEnrolledCourses(userId)}
            usersList.value = teachersTask.await()
        }
    }

    private fun getStudentsFromCourses(userId: String){
        viewModelScope.launch {
            var studentsTasks = async{userRepository.getStudentsFromCourses(userId) }
            usersList.value = studentsTasks.await()
        }
    }

     fun getCurrentUserById(userId: String){
        viewModelScope.launch {
            var userTask = async{ userRepository.getUserById(userId)}
            currentUser.value = userTask.await()
        }
    }

}
package com.example.loginapp.viewmodels.userDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.CourseModel
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CourseBasicInfoViewModel(app: Application) : AndroidViewModel(app) {

    private var userRepository: UserRepository = UserRepository();
    var ownerName = MutableLiveData<String>()

    init {

    }

    fun getCourseOwnerName(course: CourseModel){
        viewModelScope.launch {
            var ownerTask = async {userRepository.getUserById(course.owner)}
            var owner = ownerTask.await()
            if (owner != null){
                ownerName.value = owner.name
            }
        }
    }


}
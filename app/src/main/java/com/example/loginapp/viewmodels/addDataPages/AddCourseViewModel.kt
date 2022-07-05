package com.example.loginapp.viewmodels.addDataPages

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.CourseRepository
import com.example.loginapp.utils.StorageHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AddCourseViewModel (app: Application) : AndroidViewModel(app){

    private var courseRepository: CourseRepository = CourseRepository();
    private var storageHandler = StorageHandler()
    private var context = getApplication<Application>().applicationContext
    private lateinit var imageUri: Uri
    var courseImageUri = MutableLiveData<Uri>()
    var currentUser = MutableLiveData<UserModel>()


    fun loadCourseImage(result: ActivityResult){
        if (result.resultCode == Activity.RESULT_OK) {
            if (context != null) {
                imageUri = result.data?.data!!
                if (imageUri != null){
                    courseImageUri.value = imageUri
                }
            }
        }
    }

    fun createCourse(userId: String, name: String, code: String, classLink: String, callback: ()->Unit){
        if(name != "" && code != "" && courseImageUri.value != null && userId != "") {
            viewModelScope.launch {
                var courseImageUrlTask = async{storageHandler.saveCourseImage(name, courseImageUri.value!!)}
                var courseImageUrl = courseImageUrlTask.await()
                var courseCreateResult = async{ courseRepository.createCourse(name, code, courseImageUrl.toString(), userId, classLink) }
                if(courseCreateResult.await()) {
                    callback()
                }
            }
        }
    }

    fun selectCourseImage(activityMethod: Intent, activityResult: Intent){

    }
}
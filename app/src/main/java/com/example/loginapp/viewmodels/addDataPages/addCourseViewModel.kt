package com.example.loginapp.viewmodels.addDataPages

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.repository.CourseRepository


class addCourseViewModel (app: Application) : AndroidViewModel(app){

    private lateinit var courseRepository: CourseRepository;
    private var context = getApplication<Application>().applicationContext
    private lateinit var image_uri: Uri
    var course_image_uri = MutableLiveData<Uri>()


    fun loadCourseImage(result: ActivityResult){
        if (result.resultCode == Activity.RESULT_OK) {
            if (context != null) {
                image_uri = result.data?.data!!
                if (image_uri != null){
                    course_image_uri.value = image_uri
                }
            }
        }
    }

    fun createCourse(new_name: String, callback: ()->Unit){
//        if(new_name != "" && course_image_uri.value != null) {
//            courseRepository.createCourse(new_name, image_uri.toString())
//            callback()
//        }
    }

    fun selectCourseImage(activityMethod: Intent, activityResult: Intent){

    }
}
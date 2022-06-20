package com.example.loginapp.viewmodels.dataLists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.CourseModel
import com.example.loginapp.repository.CourseRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoursesListViewModel(app: Application) : AndroidViewModel(app)  {
    private var courseRepository: CourseRepository = CourseRepository()
    var coursesList = MutableLiveData<MutableList<CourseModel?>>()

    fun getCourses(){
        coursesList.value?.clear()
        viewModelScope.launch {
            coursesList.value = async {courseRepository.getCourses()}.await()
        }
    }
}
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
    var joinRequestCorrectlySent = MutableLiveData<Int>(-1)

    fun getCourses(userId: String, userRole: String){
        coursesList.value?.clear()
        viewModelScope.launch {
            if(userRole == "Student"){
                coursesList.value = async {courseRepository.getMyEnrolledCourses(userId)}.await()
            }
            else if(userRole == "Teacher"){
                coursesList.value = async {courseRepository.getMyCourses(userId)}.await()
            }
        }
    }

    fun sendJoinRequestToCourse(userId: String, courseCode: String){
        viewModelScope.launch {
            var courseTask = async {courseRepository.getCourseByCode(courseCode)}
            var course = courseTask.await()
            if(course != null && !course.pendingEnrollments.contains(userId) && !course.enrollments.contains(userId)){
                course.pendingEnrollments.add(userId)
                var updateCourseTask = async {courseRepository.updateCoursePendingEnrollments(course)}
                updateCourseTask.await()
                joinRequestCorrectlySent.value = 1
            }
            else{
                joinRequestCorrectlySent.value = 0
            }
            joinRequestCorrectlySent.value = -1
        }
    }
}
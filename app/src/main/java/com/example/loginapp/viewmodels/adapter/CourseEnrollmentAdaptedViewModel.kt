package com.example.loginapp.viewmodels.adapter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.CourseModel
import com.example.loginapp.repository.CourseRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CourseEnrollmentAdapterViewModel(app: Application) : AndroidViewModel(app) {

    private var courseRepository: CourseRepository = CourseRepository();

    init {}

    fun enrollUserToCourse(userId: String, courseModel: CourseModel){
        viewModelScope.launch {
            courseModel.enrollments.add(userId)
            courseModel.pendingEnrollments.remove(userId)
            var updateCourseEnrollmentsTask = async {courseRepository.updateCourseEnrollments(courseModel)}
            var updateCoursePendingEnrollmentsTask = async {courseRepository.updateCoursePendingEnrollments(courseModel)}
            updateCourseEnrollmentsTask.await()
            updateCoursePendingEnrollmentsTask.await()
        }
    }

    fun rejectEnrollmentRequest(userId: String, courseModel: CourseModel){
        viewModelScope.launch {
            courseModel.pendingEnrollments.remove(userId)
            var updateCoursePendingEnrollmentsTask = async {courseRepository.updateCoursePendingEnrollments(courseModel)}
            updateCoursePendingEnrollmentsTask.await()
        }
    }


}
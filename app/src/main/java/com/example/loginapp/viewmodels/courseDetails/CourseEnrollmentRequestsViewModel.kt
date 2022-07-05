package com.example.loginapp.viewmodels.courseDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.CourseModel
import com.example.loginapp.models.EnrollmentModel
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.CourseRepository
import com.example.loginapp.repository.UserRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CourseEnrollmentRequestsViewModel(app: Application) : AndroidViewModel(app) {

    private var courseRepository: CourseRepository = CourseRepository();
    private var userRepository: UserRepository = UserRepository();
    var enrollmentList = MutableLiveData<MutableList<EnrollmentModel?>>()

    init {}

    fun getCourseEnrollmentRequests(courseModel: CourseModel){
        viewModelScope.launch {
            var enrollmentRequestUsers: MutableList<EnrollmentModel?> = mutableListOf()
            for(userId in courseModel.pendingEnrollments) {
                var userTask = async { userRepository.getUserById(userId) }
                var user = userTask.await()
                if(user != null) {
                    enrollmentRequestUsers.add(EnrollmentModel(
                        user=user,
                        course=courseModel
                    ))
                }
            }
            enrollmentList.value = enrollmentRequestUsers
        }
    }

    fun enrollUserToCourse(userId: String, courseModel: CourseModel){
        viewModelScope.launch {
            courseModel.enrollments.add(userId)
            courseModel.pendingEnrollments.remove(userId)
            var updateCourseEnrollmentsTask = async {courseRepository.updateCourseEnrollments(courseModel)}
            updateCourseEnrollmentsTask.await()
            var updateCoursePendingEnrollmentsTask = async {courseRepository.updateCoursePendingEnrollments(courseModel)}
            updateCoursePendingEnrollmentsTask.await()
            enrollmentList.value?.removeIf { enrollment->enrollment?.user?.id == userId }
        }
    }

    fun rejectEnrollmentRequest(userId: String, courseModel: CourseModel){
        viewModelScope.launch {
            courseModel.pendingEnrollments.remove(userId)
            var updateCoursePendingEnrollmentsTask = async {courseRepository.updateCoursePendingEnrollments(courseModel)}
            updateCoursePendingEnrollmentsTask.await()
            enrollmentList.value?.removeIf { enrollment->enrollment?.user?.id == userId }
        }
    }

}
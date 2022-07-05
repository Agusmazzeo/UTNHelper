package com.example.loginapp.repository

import android.util.Log
import com.example.loginapp.entities.Course
import com.example.loginapp.entities.CourseDoc
import com.example.loginapp.models.CourseModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class CourseRepository (){
    private val db = Firebase.firestore
    private var coursesList : MutableList<CourseModel?> = mutableListOf()

    suspend fun getCourses () : MutableList<CourseModel?>{
        var query = db.collection("courses").get()
        var result = query.await()
        for (document in result) {
            coursesList.add(
                CourseModel(
                document.id,
                document.data["name"] as String,
                document.data["code"] as String,
                document.data["icon"] as String,
                document.data["owner"] as String,
                document.data?.getOrDefault("link", "") as String,
                document.data["enrollments"] as MutableList<String>,
                document.data["pendingEnrollments"] as MutableList<String>
            )
            )
        }
        return coursesList
    }

    suspend fun getMyEnrolledCourses (userId: String) : MutableList<CourseModel?>{
        var query = db.collection("courses").whereArrayContains("enrollments", userId).get()
        var result = query.await()
        for (document in result) {
            coursesList.add(
                CourseModel(
                    document.id,
                    document.data["name"] as String,
                    document.data["code"] as String,
                    document.data["icon"] as String,
                    document.data["owner"] as String,
                    document.data?.getOrDefault("link", "") as String,
                    document.data["enrollments"] as MutableList<String>,
                    document.data["pendingEnrollments"] as MutableList<String>
                )
            )
        }
        return coursesList
    }

    suspend fun getMyCourses (userId: String) : MutableList<CourseModel?>{
        var query = db.collection("courses").whereEqualTo("owner", userId).get()
        var result = query.await()
        for (document in result) {
            coursesList.add(
                CourseModel(
                    document.id,
                    document.data["name"] as String,
                    document.data["code"] as String,
                    document.data["icon"] as String,
                    document.data["owner"] as String,
                    document.data?.getOrDefault("link", "") as String,
                    document.data["enrollments"] as MutableList<String>,
                    document.data["pendingEnrollments"] as MutableList<String>
                )
            )
        }
        return coursesList
    }

    suspend fun getCourseByCode (courseCode: String) : CourseModel?{
        var course: CourseModel? = null
        var query = db.collection("courses").whereEqualTo("code", courseCode).get()
        var result = query.await()
        if(!result.isEmpty){
            var document = result.documents[0]
            if(document != null){
                    course = CourseModel(
                    document.id,
                    document.data?.get("name") as String,
                    document.data?.get("code") as String,
                    document.data?.get("icon") as String,
                    document.data?.get("owner") as String,
                    document.data?.getOrDefault("link", "") as String,
                    document.data?.get("enrollments") as MutableList<String>,
                    document.data?.get("pendingEnrollments") as MutableList<String>)
            }
        }
        return course
    }

    suspend fun createCourse(name: String, code: String, icon: String, owner: String, classLink: String): Boolean{
        var course = CourseDoc(name = name, code = code, icon = icon, owner = owner, link = classLink)
        var result = false
        try {
            var query = db.collection("courses").add(course)
            query.await()
            result = true
        }catch (e: Exception) {
            Log.d("Create Course action: ", e.message!!)
        }
        return result
    }

    suspend fun updateCoursePendingEnrollments(courseModel: CourseModel): Boolean{
        var result = false
        try {
            var query = db.collection("courses").document(courseModel.id).update(
                mapOf("pendingEnrollments" to courseModel.pendingEnrollments ))
            query.await()
            result = true
        }catch (e: Exception) {
            Log.d("Update Course action: ", e.message!!)
        }
        return result
    }

    suspend fun updateCourseEnrollments(courseModel: CourseModel): Boolean{
        var result = false
        try {
            var query = db.collection("courses").document(courseModel.id).update(
                mapOf("enrollments" to courseModel.enrollments ))
            query.await()
            result = true
        }catch (e: Exception) {
            Log.d("Update Course action: ", e.message!!)
        }
        return result
    }

    suspend fun deleteCourseByID(id: String): Boolean{
        var result = false
        try {
            var deleteTask = db.collection("courses").document(id).delete()
            deleteTask.await()
            result = true
        }
        catch (e: Exception){
            Log.d("Create Course action: ", e.message!!)
        }
        return result
    }

}
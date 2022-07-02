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
            )
            )
        }
        return coursesList
    }

    suspend fun createCourse(name: String, code: String, icon: String): Boolean{
        var course = CourseDoc(id=null, name = name, code = code, icon = icon)
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

//    fun getCourses () : MutableList<Course?>{
//        var result = courseDao.getAll()
//        if(result == null){
//            result = mutableListOf()
//        }
//        return result
//    }
//
//
//    fun updateCourse(id: Int, name: String, icon: String){
//        var course: Course = Course(id = id, name = name, icon = icon)
//        courseDao.update(course)
//    }
//
//    fun deleteCourseByID(id: Int){
//        courseDao.deleteByID(id)
//    }
//
//    fun deleteAllUsers(){
//        courseDao.deleteAll()
//    }
}
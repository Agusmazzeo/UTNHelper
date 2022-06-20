package com.example.loginapp.repository

import com.example.loginapp.models.CourseModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class CourseRepository (){
    val db = Firebase.firestore
    private var coursesList : MutableList<CourseModel?> = mutableListOf()

    suspend fun getCourses () : MutableList<CourseModel?>{
        var query = db.collection("courses").get()
        var result = query.await()
        for (document in result) {
            coursesList.add(
                CourseModel(
                document.id,
                document.data["name"] as String,
                document.data["icon"] as String,
            )
            )
        }
        return coursesList
    }
//    fun getCourses () : MutableList<Course?>{
//        var result = courseDao.getAll()
//        if(result == null){
//            result = mutableListOf()
//        }
//        return result
//    }
//
//    fun createCourse(name: String, icon: String){
//        var course: Course = Course(name = name, icon = icon)
//        courseDao.add(course)
//    }
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
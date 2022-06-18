package com.example.loginapp.repository

import com.example.loginapp.database.CourseDao
import com.example.loginapp.database.UserDao
import com.example.loginapp.entities.Course
import com.example.loginapp.entities.User


class CourseRepository (private val courseDao: CourseDao){
    private var courseList : MutableList<Course?> = mutableListOf()

    init{
        courseList = getCourses()
    }

    fun getCourses () : MutableList<Course?>{
        var result = courseDao.getAll()
        if(result == null){
            result = mutableListOf()
        }
        return result
    }

    fun createCourse(name: String, icon: String){
        var course: Course = Course(name = name, icon = icon)
        courseDao.add(course)
    }

    fun updateCourse(id: Int, name: String, icon: String){
        var course: Course = Course(id = id, name = name, icon = icon)
        courseDao.update(course)
    }

    fun deleteCourseByID(id: Int){
        courseDao.deleteByID(id)
    }

    fun deleteAllUsers(){
        courseDao.deleteAll()
    }
}
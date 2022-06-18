package com.example.loginapp.repository

import com.example.loginapp.database.UserDao
import com.example.loginapp.entities.User


class UserRepository (private val userDao: UserDao){
    private var userList : MutableList<User?> = mutableListOf()

    init{
        userList = getUsers()
    }

    fun getUsers () : MutableList<User?>{
        var result = userDao.getAllUsers()
        if(result == null){
            result = mutableListOf()
        }
        return result
    }

    fun createUser(name: String, email: String, phone: String, courseId: Int){
        var user: User = User(name = name, email = email, phone = phone, courseId = courseId)
        userDao.addUser(user)
    }

    fun updateUser(id: Int, name: String, email: String, phone: String, courseId: Int){
        var user: User = User(id = id, name = name, email = email, phone = phone, courseId = courseId)
        userDao.updateUser(user)
    }

    fun deleteUserByID(id: Int){
        userDao.deleteByID(id)
    }

    fun deleteAllUsers(){
        userDao.deleteAll()
    }
}
package com.example.loginapp.repository

import android.util.Log
import com.example.loginapp.entities.UserDoc
import com.example.loginapp.models.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class UserRepository (){
    val db = Firebase.firestore
    private var usersList : MutableList<UserModel?> = mutableListOf()

    suspend fun getUsers () : MutableList<UserModel?>{
        var query = db.collection("users").get()
        var result = query.await()
        for (document in result) {
            usersList.add(UserModel(
                document.id,
                document.data["name"] as String,
                document.data["email"] as String,
                document.data["phone"] as String
            ))
        }
        return usersList
    }

    suspend fun createUser(name: String, email: String, phone: String, courseId: Int): Boolean{
        var user = UserDoc(name, email, phone, courseId)
        var result = false
        try {
            var query = db.collection("users").add(user)
            query.await()
            result = true
        }catch (e: Exception) {
            Log.d("Create User action: ", e.message!!)
        }
        return result
    }

//    fun createUser(name: String, email: String, phone: String, courseId: Int){
//        var user: User = User(name = name, email = email, phone = phone, courseId = courseId)
//        userDao.addUser(user)
//    }
//
//    fun updateUser(id: Int, name: String, email: String, phone: String, courseId: Int){
//        var user: User = User(id = id, name = name, email = email, phone = phone, courseId = courseId)
//        userDao.updateUser(user)
//    }
//
//    fun deleteUserByID(id: Int){
//        userDao.deleteByID(id)
//    }
//
//    fun deleteAllUsers(){
//        userDao.deleteAll()
//    }
}
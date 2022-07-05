package com.example.loginapp.repository

import android.util.Log
import com.example.loginapp.entities.UserDoc
import com.example.loginapp.models.UserModel
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class UserRepository (){
    private val db = Firebase.firestore
    private var usersList = mutableListOf<UserModel?>()

    suspend fun getUsers () : MutableList<UserModel?>{
        var query = db.collection("users").get()
        var result = query.await()
        for (document in result) {
            usersList.add(UserModel(
                document.id,
                document.data["name"] as String,
                document.data["email"] as String,
                document.data["phone"] as String,
                document.data["role"] as String,
                document.data["icon"] as String
            ))
        }
        return usersList
    }

    suspend fun getUserById (userId: String) : UserModel?{
        return try {
            var query = db.collection("users").document(userId).get()
            var result = query.await()
            UserModel(
                userId,
                result.data?.get("name") as String,
                result.data?.get("email") as String,
                result.data?.get("phone") as String,
                result.data?.get("role") as String,
                result.data?.get("icon") as String
            )
        } catch (e: Exception) {
            Log.d("Create User action: ", e.message!!)
            null
        }
    }

    suspend fun updateUser(id: String, name: String, email: String, phone: String){
        try {
            var updateQuery = db.collection("users").document(id).update(mapOf("name" to name, "email" to email, "phone" to phone))
            var result = updateQuery.await()

        }catch (e: Exception) {
            Log.d("Update User action: ", e.message!!)
        }
    }

    suspend fun createUser(id: String, name: String, email: String, phone: String, role: String, icon: String): Boolean{
        var user = UserDoc(name, email, phone, role, icon)
        var result = false
        try {
            var query = db.collection("users").document(id).set(user)
            query.await()
            result = true
        }catch (e: Exception) {
            Log.d("Create User action: ", e.message!!)
        }
        return result
    }

    suspend fun getTeachersFromEnrolledCourses(userId: String): MutableList<UserModel?>{
        var teachersFromEnrolledCourses = mutableListOf<UserModel?>()
        try {
            var userCourses = db.collection("courses").whereArrayContains("enrollments", userId).get().await()
            var userIds = mutableListOf<String>()
            for (document in userCourses){
                userIds.add(0,document.data["owner"] as String)
            }
            var query = db.collection("users").whereIn(FieldPath.documentId(), userIds).get()
            var result = query.await()
            for(document in result){
                teachersFromEnrolledCourses.add(UserModel(
                    document.id,
                    document.data["name"] as String,
                    document.data["email"] as String,
                    document.data["phone"] as String,
                    document.data["role"] as String,
                    document.data["icon"] as String
                ))
            }

        }catch (e: Exception) {
            Log.d("Get students action: ", e.message!!)
        }
        return teachersFromEnrolledCourses
    }

    suspend fun getStudentsFromCourses(userId: String): MutableList<UserModel?>{
        var studentsFromCourses = mutableListOf<UserModel?>()
        try {
            var userCourses = db.collection("courses").whereEqualTo("owner", userId).get().await()
            var userIds = mutableListOf<String>()
            for (document in userCourses){
                var enrolledUserIds = document.data["enrollments"] as MutableList<String>
                userIds.addAll(0,enrolledUserIds)
            }
            var query = db.collection("users").whereIn(FieldPath.documentId(), userIds).get()
            var result = query.await()
            for(document in result){
                studentsFromCourses.add(UserModel(
                    document.id,
                    document.data["name"] as String,
                    document.data["email"] as String,
                    document.data["phone"] as String,
                    document.data["role"] as String,
                    document.data["icon"] as String
                ))
            }

        }catch (e: Exception) {
            Log.d("Get students action: ", e.message!!)
        }
        return studentsFromCourses
    }

}
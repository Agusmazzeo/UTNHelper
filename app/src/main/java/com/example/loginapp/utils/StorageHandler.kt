package com.example.loginapp.utils

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class StorageHandler {

    private var storage = Firebase.storage
    private var storageRef = storage.reference

    suspend fun saveUserImage(userName: String, image: Uri):Uri?{
        try {
            var filename = "User/Images/${userName}_${image.lastPathSegment}"
            var uploadTask = storageRef.child(filename).putFile(image)
            var result = uploadTask.await()
            return storageRef.child(filename).downloadUrl.await()
        }catch (e: Exception){
            Log.d("Error during Course Image creation: ", e.message!!)
            return null
        }
    }

    suspend fun saveCourseImage(courseName: String, image: Uri):Uri?{
        try {
            var filename = "Course/Images/${courseName}_${image.lastPathSegment}"
            var uploadTask = storageRef.child(filename).putFile(image)
            var result = uploadTask.await()
            return storageRef.child(filename).downloadUrl.await()
        }catch (e: Exception){
            Log.d("Error during Course Image creation: ", e.message!!)
            return null
        }
    }

    suspend fun deleteCourseImage(filename: String):Boolean{
        try {
            var uploadTask = storage.getReferenceFromUrl(filename).delete()
            var result = uploadTask.await()
            return true
        }catch (e: Exception){
            Log.d("Error during Course Image delete: ", e.message!!)
            return false
        }
    }
}
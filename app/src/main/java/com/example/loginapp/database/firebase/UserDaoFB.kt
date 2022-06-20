package com.example.loginapp.database.firebase

import com.example.loginapp.models.UserModel
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserDaoFB {
    val db = Firebase.firestore

     fun getAllUsers(): MutableList<UserModel?>{
        val usersList: MutableList<UserModel?> = mutableListOf()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->

            }
            .addOnFailureListener { _ ->
            }
        return usersList
    }
}
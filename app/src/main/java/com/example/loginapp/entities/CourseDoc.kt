package com.example.loginapp.entities

data class CourseDoc(
    val id: String?,
    val name : String,
    val code : String,
    val icon: String,
    val owner: String,
    val enrollments: List<String>? = mutableListOf(),
    val pendingEnrollments: List<String>? = mutableListOf()
)
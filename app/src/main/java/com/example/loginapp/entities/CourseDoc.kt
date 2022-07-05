package com.example.loginapp.entities

data class CourseDoc(
    val name : String,
    val code : String,
    val icon: String,
    val owner: String,
    val link: String,
    val enrollments: List<String>? = mutableListOf(),
    val pendingEnrollments: List<String>? = mutableListOf()
)
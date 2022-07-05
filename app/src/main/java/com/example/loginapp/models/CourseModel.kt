package com.example.loginapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CourseModel (
    var id : String,
    var name : String,
    var code : String,
    var icon : String,
    var owner: String,
    var enrollments: MutableList<String>,
    var pendingEnrollments: MutableList<String>
    ):
    Parcelable {

    init {
        this.id = id
        this.name = name
        this.code = code
        this.icon = icon
        this.owner = owner
        this.enrollments = enrollments
        this.pendingEnrollments = pendingEnrollments
    }
}
package com.example.loginapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel (
    var id : String,
    var name : String,
    var email: String,
    var phone: String,
    var role: String,
    var icon: String
    ): Parcelable {

    init {
        this.id = id
        this.name = name
        this.email = email
        this.phone = phone
        this.role = role
        this.icon = icon
    }
}

@Parcelize
class EnrollmentModel(
    var user: UserModel,
    var course: CourseModel
): Parcelable{
    init{
        this.user = user
        this.course = course
    }
}
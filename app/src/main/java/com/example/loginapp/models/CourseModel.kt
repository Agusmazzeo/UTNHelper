package com.example.loginapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CourseModel (var id : String, var name : String, var icon : String):
    Parcelable {

    init {
        this.id = id
        this.name = name
        this.icon = icon
    }
}
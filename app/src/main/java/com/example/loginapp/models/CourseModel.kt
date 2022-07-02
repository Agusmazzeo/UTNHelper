package com.example.loginapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CourseModel (var id : String, var name : String, var code : String, var icon : String):
    Parcelable {

    init {
        this.id = id
        this.name = name
        this.code = code
        this.icon = icon
    }
}
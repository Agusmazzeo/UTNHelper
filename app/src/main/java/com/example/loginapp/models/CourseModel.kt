package com.example.loginapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CourseModel (var id : Int = 0, var name : String):
    Parcelable {

    init {
        this.id = id
        this.name = name
    }
}
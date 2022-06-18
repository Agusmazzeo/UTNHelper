package com.example.loginapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User (  id : Int = 0, name : String, email: String, phone: String, courseId: Int) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "email")
    var email : String

    @ColumnInfo(name = "phone")
    var phone : String

    @ColumnInfo(name = "courseId")
    var courseId: Int



    init {
        this.id = id
        this.name = name
        this.email = email
        this.phone = phone
        this.courseId = courseId

    }
}
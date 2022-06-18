package com.example.loginapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
class Course (  id : Int = 0, name : String, icon : String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "icon")
    var icon : String


    init {
        this.id = id
        this.name = name
        this.icon = icon
    }
}
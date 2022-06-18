package com.example.loginapp.database

import androidx.room.*
import com.example.loginapp.entities.Course

@Dao
public interface CourseDao {

    @Query("SELECT * FROM courses ORDER BY id")
    fun getAll(): MutableList<Course?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(course: Course?)

    @Update
    fun update(course: Course?)

    @Delete
    fun delete(course: Course?)

    @Query("DELETE FROM courses")
    fun deleteAll()

    @Query("DELETE FROM courses WHERE id = :id")
    fun deleteByID(id: Int)

    @Query("SELECT * FROM courses WHERE id = :id")
    fun getByID(id: Int): Course?

}
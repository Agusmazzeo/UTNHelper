package com.example.loginapp.database

import androidx.room.*
import com.example.loginapp.entities.User

@Dao
public interface UserDao {

    @Query("SELECT * FROM users ORDER BY users.id")
    fun getAllUsers(): MutableList<User?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User?)

    @Update
    fun updateUser(user: User?)

    @Delete
    fun delete(user: User?)

    @Query("DELETE FROM users")
    fun deleteAll()

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteByID(id: Int)

    @Query("SELECT * FROM users WHERE users.id = :id")
    fun getUserByID(id: Int): User?

}
package com.example.loginapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel (var id : String, var name : String, var email: String, var phone: String ): Parcelable {

    init {
        this.id = id
        this.name = name
        this.email = email
        this.phone = phone

    }
}
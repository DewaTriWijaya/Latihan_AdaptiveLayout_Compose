package com.codelabs.dewa.myreply.data

import androidx.annotation.DrawableRes

data class Account(
    val id: Long,
    val firstName: Int,
    val lastName: Int,
    val email: Int,
    @DrawableRes val avatar: Int
) {
    val fullName: String = "$firstName $lastName"
}
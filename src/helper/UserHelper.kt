package com.aspanu.whistOnline.helper

import com.aspanu.whistOnline.model.User
import kotlin.random.Random

class UserHelper {
    fun createUser(): User {
        return User(Random.nextInt())
    }
}
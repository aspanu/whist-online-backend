package com.aspanu.whistOnline.helper

import com.aspanu.whistOnline.model.User
import com.aspanu.whistOnline.model.userStorage
import kotlin.random.Random

class UserHelper {
    fun createUser(): User {
        val user = User(Random.nextInt())
        userStorage[user.id] = user
        return user
    }

    fun getUserForId(userId: Int): User {
        return userStorage[userId] ?: throw IllegalArgumentException("Could not find user for id: $userId.")
    }
}
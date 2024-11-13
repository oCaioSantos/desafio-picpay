package com.picpay.desafio.android.fixtures

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.domain.model.User

object UserFixtures {

    private val firstNames = listOf(
        "Lucas", "Ryan", "Ethan", "Noah", "Adrian",
        "Dylan", "Liam", "Oliver", "Mason", "Caleb"
    )

    private val lastNames = listOf(
        "Johnson", "Anderson", "Harris", "Miller", "Thompson",
        "Garcia", "Martinez", "Robinson", "Clark", "Lewis"
    )

    private fun getRandomName() = "${firstNames.random()} ${lastNames.random()}"

    private fun getUsername(name: String) = "@${name.replace(" ", "").lowercase()}"

    private fun getUserImg(id: Int) =
        "https://randomuser.me/api/portraits/men/${if (id > 99) 99 else id}.jpg"

    fun getUserList(size: Int = 5) = List(size) {
        getSingleUser(
            id = it,
            name = getRandomName(),
        )
    }

    fun getJsonUserList(size: Int = 5): String {
        return Gson().toJson(getUserList(size))
    }

    fun getUserListFromJson(list: String): List<User> {
        return Gson().fromJson(list, object : TypeToken<List<User>>() {})
    }

    private fun getSingleUser(
        id: Int = 1,
        name: String = getRandomName(),
    ): User {
        return User(
            id = id,
            name = name,
            username = getUsername(name),
            img = getUserImg(id)
        )
    }


}
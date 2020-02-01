package me.oleg.taglibro.viewmodels

import android.util.Log
import me.oleg.taglibro.data.repositry.UserRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) {
    var userName: String = ""
        get
        set(value) {
            if (field != value) {
                field = value
            }
        }
    var password: String = ""
        get
        set(value) {
            if (field != value) {
                field = value
            }
        }

    fun login() {
        Log.i("Login: ", "userName: $userName, password: $password")


        val user = userRepository.getUser(userName, password)
    }
}


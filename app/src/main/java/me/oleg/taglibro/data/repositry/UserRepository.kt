package me.oleg.taglibro.data.repositry

import io.reactivex.Single
import me.oleg.taglibro.data.model.User
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val restService: RestService
) {

    fun getUser(userName: String, password: String): Single<User?>? {
        return restService.getUser(userName, password)
    }
}
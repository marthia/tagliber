package me.oleg.taglibro.data.repositry

import io.reactivex.Single
import me.oleg.taglibro.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface RestService {

    @GET("users/username:{id}/password:{password}")
    fun getUser(

        @Path("username") username: String,
        @Path("password") password: String

    ): Single<User?>?
}
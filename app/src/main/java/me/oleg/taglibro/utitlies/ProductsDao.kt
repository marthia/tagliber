package me.oleg.taglibro.utitlies

import java.util.concurrent.Flow

interface ProductsDao {

    suspend fun load(path: String): Flow
}
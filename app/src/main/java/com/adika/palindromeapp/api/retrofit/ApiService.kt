package com.adika.palindromeapp.api.retrofit

import com.adika.palindromeapp.api.response.ResponseGetAllUsers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<ResponseGetAllUsers>
}
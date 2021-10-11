package com.damcoassignment.api

import com.damcoassignment.model.ColorsListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(com.damcoassignment.BuildConfig.BASE_URL + "colors?")
    suspend fun getColorsList(
        @Query("keywords") keywords: String,
        @Query("numResults") numResults: String,
        @Query("format") format: String

    ): Response<ArrayList<ColorsListModel>>
}
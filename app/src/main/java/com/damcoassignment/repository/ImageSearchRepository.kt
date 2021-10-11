package com.damcoassignment.repository

import com.damcoassignment.api.ApiService
import javax.inject.Inject

class ImageSearchRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getColorsList(keyWords: String, numResults: String, format: String) =
        apiService.getColorsList(keyWords, numResults, format)
}
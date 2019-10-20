package com.sys1yagi.jetpack_compose_github_app.infrastructure.repository

import androidx.lifecycle.MutableLiveData
import com.sys1yagi.jetpack_compose_github_app.infrastructure.api.ApiClient
import com.sys1yagi.jetpack_compose_github_app.infrastructure.api.GithubRepositorySearchResult
import com.sys1yagi.jetpack_compose_github_app.infrastructure.coroutines.AppDispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.IllegalStateException

class GithubRepositoryRepository(
    private val apiClient: ApiClient,
    private val appDispatchers: AppDispatchers
) {

    suspend fun searchGithubRepository(query: String): GithubRepositorySearchResult =
        withContext(appDispatchers.IO) {
            val result = MutableLiveData<GithubRepositorySearchResult>()
            if (query.isEmpty()) {
                result.postValue(null)
            }
            val response = apiClient.searchRepository(query).execute()
            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException()
            } else {
                throw HttpException(response)
            }
        }
}

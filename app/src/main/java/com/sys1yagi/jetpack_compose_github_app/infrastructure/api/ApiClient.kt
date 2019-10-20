package com.sys1yagi.jetpack_compose_github_app.infrastructure.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    // If you add the suspend keyword, the GET annotation will disappear for some reason 🤔
    @GET("search/repositories")
    fun searchRepository(@Query("q") query: String): Call<GithubRepositorySearchResult>
}

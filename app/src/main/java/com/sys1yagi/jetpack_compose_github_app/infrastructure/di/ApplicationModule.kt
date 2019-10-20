package com.sys1yagi.jetpack_compose_github_app.infrastructure.di

import com.google.gson.GsonBuilder
import com.sys1yagi.jetpack_compose_github_app.infrastructure.api.ApiClient
import com.sys1yagi.jetpack_compose_github_app.infrastructure.coroutines.AppDispatchers
import com.sys1yagi.jetpack_compose_github_app.infrastructure.repository.GithubRepositoryRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val singleton = module {
    single {
        AppDispatchers()
    }

    single {
        GsonBuilder().create()
    }

    single {
        OkHttpClient.Builder().build()
    }

    single<Retrofit> {
        val apiEndpoint = "https://api.github.com"
        Retrofit.Builder()
            .baseUrl(apiEndpoint)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(ApiClient::class.java)
    }

    single {
        GithubRepositoryRepository(
            get(),
            get()
        )
    }
}

package com.sys1yagi.jetpack_compose_github_app.infrastructure.api

import com.google.gson.annotations.SerializedName

data class GithubRepositoryOwner(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

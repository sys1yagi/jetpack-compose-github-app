package com.sys1yagi.jetpack_compose_github_app.infrastructure.api

import com.google.gson.annotations.SerializedName

data class GithubRepository(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("owner")
    val owner: GithubRepositoryOwner
)

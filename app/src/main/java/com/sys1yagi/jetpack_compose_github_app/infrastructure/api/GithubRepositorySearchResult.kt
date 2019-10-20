package com.sys1yagi.jetpack_compose_github_app.infrastructure.api

import com.google.gson.annotations.SerializedName

data class GithubRepositorySearchResult(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<GithubRepository>
) {
    companion object {
        fun empty() = GithubRepositorySearchResult(
            0,
            false,
            emptyList()
        )
    }
}

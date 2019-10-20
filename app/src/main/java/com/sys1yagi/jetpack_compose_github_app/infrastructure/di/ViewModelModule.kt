package com.sys1yagi.jetpack_compose_github_app.infrastructure.di

import com.sys1yagi.jetpack_compose_github_app.ui.repositorysearch.GithubRepositorySearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule by lazy {
    module {
        viewModel {
            GithubRepositorySearchViewModel(get())
        }
    }
}

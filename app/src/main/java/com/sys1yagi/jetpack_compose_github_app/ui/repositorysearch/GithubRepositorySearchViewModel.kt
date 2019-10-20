package com.sys1yagi.jetpack_compose_github_app.ui.repositorysearch

import androidx.compose.Model
import androidx.lifecycle.*
import com.sys1yagi.jetpack_compose_github_app.infrastructure.api.GithubRepositorySearchResult
import com.sys1yagi.jetpack_compose_github_app.infrastructure.repository.GithubRepositoryRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GithubRepositorySearchViewModel(
    private val githubRepositoryRepository: GithubRepositoryRepository
) : ViewModel() {

    val state = State()

    fun setQuery(query: String) {
        viewModelScope.launch {
            state.viewState = GithubRepositorySearchViewState.Progress
            runCatching {
                githubRepositoryRepository.searchGithubRepository(query)
            }.fold(
                onSuccess = {
                    state.githubRepositorySearchResult = it
                    if (it.items.isEmpty()) {
                        state.viewState = GithubRepositorySearchViewState.Empty
                    } else {
                        state.viewState = GithubRepositorySearchViewState.ShowContent
                    }
                },
                onFailure = { e ->
                    e.printStackTrace()
                    if (e is CancellationException) {
                        return@fold
                    }
                    state.viewState = GithubRepositorySearchViewState.Error(e)
                })
        }
    }
}

sealed class GithubRepositorySearchViewState {
    object Progress : GithubRepositorySearchViewState()
    object ShowContent : GithubRepositorySearchViewState()
    object Empty : GithubRepositorySearchViewState()
    class Error(val t: Throwable) : GithubRepositorySearchViewState()
}

@Model
class State {
    var viewState: GithubRepositorySearchViewState = GithubRepositorySearchViewState.ShowContent
    var githubRepositorySearchResult: GithubRepositorySearchResult? = null
}

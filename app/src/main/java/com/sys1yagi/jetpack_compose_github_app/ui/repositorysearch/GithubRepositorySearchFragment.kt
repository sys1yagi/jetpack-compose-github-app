package com.sys1yagi.jetpack_compose_github_app.ui.repositorysearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.fragment.app.Fragment
import androidx.ui.core.*
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.input.EditorStyle
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.text.TextStyle
import com.sys1yagi.jetpack_compose_github_app.infrastructure.api.GithubRepository
import com.sys1yagi.jetpack_compose_github_app.ui.composeable.SingleLineEditText
import org.koin.androidx.viewmodel.ext.android.viewModel

class GithubRepositorySearchFragment : Fragment() {
    private val viewModel: GithubRepositorySearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FrameLayout(requireContext()).apply {
            setContent {
                mainLayout(viewModel, ::openGithubRepository)
            }
        }
    }

    private fun openGithubRepository(githubRepository: GithubRepository) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubRepository.htmlUrl))
        startActivity(intent)
    }
}

@Composable
fun mainLayout(
    viewModel: GithubRepositorySearchViewModel,
    onOpenGithubRepository: (GithubRepository) -> Unit
) {
    val viewState = viewModel.state.viewState
    MaterialTheme {
        FlexColumn {
            inflexible {
                searchForm { query ->
                    if (query.isEmpty()) {
                        return@searchForm
                    }
                    viewModel.setQuery(query)
                }
            }
            flexible(1f) {
                searchResult(viewModel, onOpenGithubRepository)
                searchResultEmpty(viewModel)
                progress(viewModel)
                gotError(viewModel)
            }
        }
    }
}

@Composable
fun searchForm(
    onSearch: (String) -> Unit
) {
    val state = +state { EditorModel() }
    Padding(8.dp) {
        FlexRow {
            flexible(1f) {
                SingleLineEditText(
                    state,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                    editorStyle = EditorStyle(textStyle = TextStyle(fontSize = 16.sp)),
                    onImeActionPerformed = {
                        onSearch(state.value.text)
                    }
                )
            }
            inflexible {
                Button(text = "Search ", onClick = {
                    onSearch(state.value.text)
                })
            }
        }
    }
}

@Composable
fun searchResult(
    viewModel: GithubRepositorySearchViewModel,
    onOpenGithubRepository: (GithubRepository) -> Unit
) {
    if (viewModel.state.viewState !is GithubRepositorySearchViewState.ShowContent) {
        return
    }
    val searchResult = viewModel.state.githubRepositorySearchResult ?: return
    VerticalScroller {
        Column {
            searchResult.items.map { githubRepository ->
                githubRepositoryItem(githubRepository, onClick = {
                    onOpenGithubRepository(it)
                })
            }
        }
    }
}

@Composable
fun githubRepositoryItem(
    githubRepository: GithubRepository,
    onClick: (GithubRepository) -> Unit
) {
    val gray = Color.Gray
    Button(
        style = ButtonStyle(
            color = Color.Transparent,
            shape = +themeShape { button }
        ),
        onClick = { onClick(githubRepository) }
    ) {
        Column {
            Text(
                text = githubRepository.name,
                style = TextStyle(fontSize = 14.sp)
            )
            Text(
                text = githubRepository.description ?: "",
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
            Text(
                text = githubRepository.htmlUrl,
                style = TextStyle(
                    color = gray,
                    fontSize = 10.sp
                )
            )
            Align(alignment = Alignment.CenterRight) {
                Text(
                    text = githubRepository.owner.login,
                    style = TextStyle(
                        color = gray,
                        fontSize = 10.sp
                    )
                )
            }
            Divider(
                modifier = Spacing(top = 8.dp),
                color = gray,
                height = 0.5.dp
            )
        }
    }
}

@Composable
fun progress(viewModel: GithubRepositorySearchViewModel) {
    if (viewModel.state.viewState !is GithubRepositorySearchViewState.Progress) {
        return
    }
    Center {
        CircularProgressIndicator()
    }
}

@Composable
fun searchResultEmpty(viewModel: GithubRepositorySearchViewModel) {
    if (viewModel.state.viewState !is GithubRepositorySearchViewState.Empty) {
        return
    }
    Center {
        Text(text = "見つかりませんでした")
    }
}

@Composable
fun gotError(viewModel: GithubRepositorySearchViewModel) {
    val viewState = viewModel.state.viewState
    if (viewState !is GithubRepositorySearchViewState.Error) {
        return
    }
    Center {
        Text(text = "エラー :${viewState.t.message}")
    }
}


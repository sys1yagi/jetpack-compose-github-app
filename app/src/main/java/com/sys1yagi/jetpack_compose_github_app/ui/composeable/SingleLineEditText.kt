package com.sys1yagi.jetpack_compose_github_app.ui.composeable

import androidx.compose.Composable
import androidx.compose.State
import androidx.ui.core.EditorModel
import androidx.ui.core.TextField
import androidx.ui.core.dp
import androidx.ui.input.EditorStyle
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.layout.Padding

@Composable
fun SingleLineEditText(
    state: State<EditorModel>,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified,
    editorStyle: EditorStyle? = null,
    onImeActionPerformed: (ImeAction) -> Unit = {
    }
) {
    Padding(8.dp) {
        TextField(
            value = state.value,
            onValueChange = { new ->
                state.value = if (new.text.any { it == '\n' }) {
                    state.value
                } else {
                    new
                }
            },
            keyboardType = keyboardType,
            imeAction = imeAction,
            editorStyle = editorStyle,
            onImeActionPerformed = onImeActionPerformed
        )
    }
}

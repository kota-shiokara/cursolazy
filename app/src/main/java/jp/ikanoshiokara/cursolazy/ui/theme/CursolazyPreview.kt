package jp.ikanoshiokara.cursolazy.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CursolazyPreview(
    content: @Composable () -> Unit
) {
    CursolazyTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}
package kmp.parth.findtime.android.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun <T> AnimatedSwipeDismiss(
    item: T,
    background: @Composable (isDismissed: Boolean) -> Unit,
    onDismiss: (T) -> Unit,
    modifier: Modifier = Modifier,
    enter: EnterTransition = expandVertically(),
    exit: ExitTransition = shrinkVertically(
        animationSpec = tween(
            durationMillis = 500,
        )
    ),
    content: @Composable (isDismissed: Boolean) -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { true },
        positionalThreshold = { 200f }
    )

    val isDismissed = dismissState.currentValue == SwipeToDismissBoxValue.EndToStart

    SideEffect {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            onDismiss(item)
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = !isDismissed,
        enter = enter,
        exit = exit
    ) {
        SwipeToDismissBox(
            modifier = modifier,
            state = dismissState,
            enableDismissFromStartToEnd = false,
            enableDismissFromEndToStart = true,
            backgroundContent = { background(isDismissed) },
            content = { content(isDismissed) }
        )
    }
}

@Preview
@Composable
private fun AnimatedSwipeDismissPreview() {
    MyApplicationTheme {
        AnimatedSwipeDismiss(
            item = "Test",
            background = { isDismissed ->
                Text("Dismissed: $isDismissed")
            },
            content = { isDismissed ->
                Text("Content: Swipe me!")
            },
            onDismiss = {}
        )
    }
}
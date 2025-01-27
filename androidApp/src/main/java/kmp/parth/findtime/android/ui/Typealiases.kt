package kmp.parth.findtime.android.ui

import androidx.compose.runtime.Composable

typealias OnAddType = (List<String>) -> Unit
typealias onDismissType = () -> Unit // Dismissing a dialog
typealias composeFun = @Composable () -> Unit
typealias topBarFun = @Composable (Int) -> Unit

@Composable
fun EmptyComposable() {
}
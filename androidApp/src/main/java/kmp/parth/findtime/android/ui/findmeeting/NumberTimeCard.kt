package kmp.parth.findtime.android.ui.findmeeting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberTimeCard(
    label: String,
    hour: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(bottom = 16.dp),
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            NumberPicker(
                hour = hour,
                range = IntRange(0, 23),
                onStateChanged = {
                    hour.value = it
                }
            )
        }
    }
}
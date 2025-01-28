package kmp.parth.findtime.android.ui.worldclocks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmp.parth.findtime.TimeZoneHelper
import kmp.parth.findtime.TimeZoneHelperImpl
import kmp.parth.findtime.android.ui.AnimatedSwipeDismiss
import kmp.parth.findtime.android.ui.MyApplicationTheme
import kotlinx.coroutines.delay

const val timeMillis = 1000 * 60L

@Composable
fun TimeZoneScreen(
    currentTimezoneStrings: SnapshotStateList<String>
) {
    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var time by remember { mutableStateOf(timezoneHelper.currentTime()) }
        LaunchedEffect(Unit) {
            while (true) {
                time = timezoneHelper.currentTime()
                delay(timeMillis)
            }
        }
        LocalTimeCard(
            city = timezoneHelper.currentTimeZone(),
            time = time,
            date = timezoneHelper.getDate(timezoneHelper.currentTimeZone())
        )
        LazyColumn(
            state = listState
        ) {
            items(currentTimezoneStrings.size, key = { timezone ->
                timezone
            }) { index ->
                val timezoneString = currentTimezoneStrings[index]
                AnimatedSwipeDismiss(
                    item = timezoneString,
                    background = { isDismissed ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .background(Color.Red)
                                .padding(horizontal = 20.dp)
                        ) {
                            val alpha by animateFloatAsState(
                                targetValue = if (isDismissed) 0f else 1f,
                                animationSpec = tween(durationMillis = 300),
                                label = "Dismiss Animation"
                            )
                            
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.align(Alignment.CenterEnd),
                                tint = Color.White.copy(alpha = alpha)
                            )
                        }
                    },
                    onDismiss = { zone ->
                        if (currentTimezoneStrings.contains(zone)) {
                            currentTimezoneStrings.remove(zone)
                        }
                    }
                ) {
                    TimeCard(
                        timezone = timezoneString,
                        hours = timezoneHelper.hoursFromTimeZone(timezoneString),
                        time = timezoneHelper.getTime(timezoneString),
                        date = timezoneHelper.getDate(timezoneString)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeZoneScreenPreview() {
    MyApplicationTheme {
        val timezones = remember {
            mutableStateListOf(
                "America/New_York",
                "Europe/London",
                "Asia/Tokyo",
                "Pacific/Auckland"
            )
        }
        TimeZoneScreen(currentTimezoneStrings = timezones)
    }
}
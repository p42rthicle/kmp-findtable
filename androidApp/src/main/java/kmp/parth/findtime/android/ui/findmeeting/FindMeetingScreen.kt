package kmp.parth.findtime.android.ui.findmeeting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmp.parth.findtime.TimeZoneHelper
import kmp.parth.findtime.TimeZoneHelperImpl
import kmp.parth.findtime.android.ui.MyApplicationTheme
import kmp.parth.findtime.android.ui.worldclocks.isSelected

@Composable
fun FindMeetingScreen(
    timezoneStrings: List<String>
) {
    val listState = rememberLazyListState()
    var startTime by remember { mutableIntStateOf(8) }
    var endTime = remember { mutableIntStateOf(17) }

    val selectedTimeZones = remember {
        val selected = SnapshotStateMap<Int, Boolean>()
        for (i in timezoneStrings.indices) selected[i] = true
        selected
    }

    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()
    val showMeetingDialog = remember { mutableStateOf(false) }
    val meetingHours = remember { SnapshotStateList<Int>() }

    if (showMeetingDialog.value) {
        MeetingDialog(
            hours = meetingHours,
            onDismiss = {
                showMeetingDialog.value = false
            }
        )
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Time Range",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
        ) {
            NumberTimeCard(
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp),
                label = "End",
                hour = endTime
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = "Time Zones",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState
        )
        {
            itemsIndexed(timezoneStrings) { i, timezone ->
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = isSelected(selectedTimeZones, i),
                            onCheckedChange = {
                                selectedTimeZones[i] = it
                            }
                        )
                        Text(timezone, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            }
        }
        Spacer(Modifier.weight(0.1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 4.dp, end = 4.dp)
        ) {

            OutlinedButton(
                colors =
                ButtonDefaults.outlinedButtonColors(
                    containerColor =
                    MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    meetingHours.clear()
                    meetingHours.addAll(
                        timezoneHelper.search(
                            startTime,
                            endTime.intValue,
                            getSelectedTimeZones(
                                timezoneStrings,
                                selectedTimeZones
                            )
                        )
                    )
                    showMeetingDialog.value = true
                }) {
                Text("Search")
            }
        }
        Spacer(Modifier.size(16.dp))
    }
}

fun getSelectedTimeZones(
    timezoneStrings: List<String>,
    selectedStates: Map<Int, Boolean>
): List<String> {
    val selectedTimezones = mutableListOf<String>()
    selectedStates.keys.map {
        val timezone = timezoneStrings[it]
        if (isSelected(selectedStates, it) && !selectedTimezones.contains(timezone)) {
            selectedTimezones.add(timezone)
        }
    }
    return selectedTimezones
}

@Preview
@Composable
private fun FindMeetingScreenPreview() {
    MyApplicationTheme {
        FindMeetingScreen(
            timezoneStrings = listOf(
                "New Delhi",
                "New York",
                "London",
                "Paris",
                "Tokyo"
            )
        )
    }
}
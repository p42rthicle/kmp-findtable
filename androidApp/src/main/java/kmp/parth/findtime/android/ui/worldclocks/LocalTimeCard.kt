package kmp.parth.findtime.android.ui.worldclocks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmp.parth.findtime.android.ui.MyApplicationTheme
import kmp.parth.findtime.android.ui.endGradientColor
import kmp.parth.findtime.android.ui.startGradientColor

@Composable
fun LocalTimeCard(
    city: String,
    time: String,
    date: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(startGradientColor, endGradientColor)
                        )
                    )
                    .padding(16.dp) // Overall padding inside the card
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(8.dp)) // Add top space
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically // Align items vertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f) // Take up equal space in the row
                                .padding(end = 8.dp), // Space between columns
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text("Your Location", style = MaterialTheme.typography.bodySmall)
                            Text(city, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 4.dp))
                            Text(time, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 4.dp))
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp), // Space between columns
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(time, style = MaterialTheme.typography.headlineSmall)
                            Text(date, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LocalTimeCardPreview() {
    MyApplicationTheme {
        LocalTimeCard(
            city = "New Delhi",
            time = "12:00",
            date = "12/12/2022"
        )
    }
}
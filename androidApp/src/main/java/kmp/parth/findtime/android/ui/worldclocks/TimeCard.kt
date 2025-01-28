package kmp.parth.findtime.android.ui.worldclocks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp.parth.findtime.android.ui.MyApplicationTheme

@Composable
fun TimeCard(
    timezone: String,
    hours: Double,
    time: String,
    date: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(120.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.65f)
                            .padding(end = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = timezone,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                text = hours.toString(),
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            val hrs = if (hours > 1) "hrs" else "hr"
                            Text(
                                text = "$hrs from local",
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp
                                ),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = time,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Text(
                            text = date,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TimeCardPreview() {
    MyApplicationTheme {
        TimeCard(
            timezone = "New Delhi",
            hours = 2.0,
            time = "12:00",
            date = "12/12/2022"
        )
    }
}
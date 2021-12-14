package com.example.tippingpoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tippingpoint.ui.theme.TippingPointTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TippingPointTheme {
                MyApp {
                    TopHeader()
                }
            }
        }
    }
}

// Container functions
@Composable
fun MyApp(content: @Composable () -> Unit) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        content()
    }
}

//@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(40.dp)))
            .padding(10.dp),
        elevation = 4.dp,
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total per person",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$$total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun MainContent() {
    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
    shape = RoundedCornerShape(corner = CornerSize(8.dp)),
    border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column {
            
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TippingPointTheme {
        MyApp {
            Text(text = "Hello Again!")
        }
    }
}
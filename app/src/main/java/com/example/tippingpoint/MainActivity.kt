package com.example.tippingpoint

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tippingpoint.components.InputField
import com.example.tippingpoint.ui.theme.TippingPointTheme
import com.example.tippingpoint.utils.getTotalPerPerson
import com.example.tippingpoint.utils.getTotalTip
import com.example.tippingpoint.widgets.RoundIconButton

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TippingPointTheme {
                MyApp {
                    Column() {
//                        TopHeader()
                        MainContent()
                    }
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
fun TopHeader(totalPerPerson: Double = 100.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7), elevation = 4.dp
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

@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent() {
    BillForm() { billAmt ->
        Log.d("AMT", "MainContent: $billAmt")
    }
}

@ExperimentalComposeUiApi
@Composable
// This will host the states
fun BillForm(
    modifier: Modifier = Modifier,
    onValChanged: (String) -> Unit = {}
) {

    val totalBillState = remember {
        mutableStateOf("0.00")
    }

    val validState = remember(totalBillState.value) {
        // Look inside the text field
        // check if it's valid
        totalBillState.value.trim().isNotEmpty()
                && totalBillState.value.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    // Value to keep track of the people to split among
    val numOfPeopleState = remember {
        mutableStateOf(1)
    }

    // Use the keyboard controller to hide it or show it
    val keyboardController = LocalSoftwareKeyboardController.current

    // Slider state
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    // total tip amount state
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val sliderValuePercent = (sliderPositionState.value * 100).toInt()

    // Formatted tip amount
    val tipTotalInTwoDecimals = "%.2f".format(tipAmountState.value)

    // Total per person
    val totalPerPersonState = remember {
        mutableStateOf(0.00)
    }

    TopHeader(totalPerPersonState.value)
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(modifier = Modifier.fillMaxWidth(),
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    // We want to make sure the text is valid
                    if (!validState) {
                        return@KeyboardActions
                    }
                    // We need a way to dismiss the keyboard
                    // The value is valid here
                    onValChanged(totalBillState.value.trim())
                    totalPerPersonState.value = getTotalPerPerson(totalBillState.value.toDouble(), numOfPeopleState.value, sliderValuePercent.toDouble())
                    keyboardController?.hide()
                })
            Row(
                modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Split",
                    modifier = Modifier.align(alignment = CenterVertically)
                )
                Spacer(modifier = Modifier.width(120.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = CenterVertically
                ) {
                    // Now we call the button here
                    RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                        numOfPeopleState.value += 1
                        totalPerPersonState.value = getTotalPerPerson(totalBillState.value.toDouble(), numOfPeopleState.value, sliderValuePercent.toDouble())
                    })
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "${numOfPeopleState.value}")
                    Spacer(modifier = Modifier.width(10.dp))
                    RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                        if (numOfPeopleState.value > 1) {
                            numOfPeopleState.value -= 1
                            totalPerPersonState.value = getTotalPerPerson(totalBillState.value.toDouble(), numOfPeopleState.value, sliderValuePercent.toDouble())
                        }

                    })
                }

            }
            // Tip Row
            Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                Text(
                    text = "Tip",
                    modifier = Modifier.align(CenterVertically)
                )
                Spacer(modifier = Modifier.width(200.dp))
                // Bill percentage value
                Text(text = "$$tipTotalInTwoDecimals", modifier = Modifier.align(CenterVertically))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "$sliderValuePercent%")
                Spacer(modifier = Modifier.height(14.dp))
                // Slider
                Slider(
                    value = sliderPositionState.value, onValueChange = { newVal ->
                        sliderPositionState.value = newVal
                        // Calculate total tip now whenever we slide
                        tipAmountState.value = getTotalTip(
                            totalBillState.value.toDouble(),
                            sliderValuePercent.toDouble()
                        )
                        totalPerPersonState.value = getTotalPerPerson(totalBillState.value.toDouble(), numOfPeopleState.value, sliderValuePercent.toDouble())
                    }, modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 10,
                    enabled = validState
                )
            }
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
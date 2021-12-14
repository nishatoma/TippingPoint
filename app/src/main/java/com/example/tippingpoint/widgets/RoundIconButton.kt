package com.example.tippingpoint.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Basically this is the button but with bigger size
val IconButtonSizeModifier = Modifier.size(40.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colors.background,
    elevation: Dp = 4.dp
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onClick.invoke()
                // When we click the button make it look "bigger"
                // as an effect
            }
            .then(IconButtonSizeModifier),
        shape = CircleShape,
        backgroundColor = backgroundColor,
        elevation = elevation
    ) {
        // Inside this card we want to add an icon
        // for + and -
        Icon(
            imageVector = imageVector, contentDescription = "Plus or Minus Icon",
            tint = tint
        )
    }
}
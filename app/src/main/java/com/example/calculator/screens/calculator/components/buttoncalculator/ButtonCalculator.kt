package com.example.calculator.screens.calculator.components.buttoncalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonCalculator(color: Color, text: String, textColor: Color, fontSize: TextUnit = 50.sp, onClick: (() -> Unit)? = null ) {
    Surface (color = Color.Transparent) {
        Button(
            onClick = {
                if (onClick != null) {
                    onClick()
                }
            },
            modifier = Modifier
                .height(85.dp)
                .width(85.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text(text = text, color = textColor, fontSize = fontSize)
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun DefaultPreviewOfButtonCalculator(){
    ButtonCalculator(color = Color.LightGray, text = "AC", textColor = Color.Black)
}
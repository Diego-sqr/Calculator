package com.example.calculator.screens.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calculator.screens.calculator.components.buttoncalculator.ButtonCalculator
import com.example.formandroidcompose.router.Screen

@Composable
fun Calculator(navController: NavController) {
    Surface(
        color = Color.Black, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "0",
                    fontWeight = FontWeight.Bold,
                    fontSize = 60.sp,
                    color = Color(0xFFD4D4D2)
                )
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ButtonCalculator(
                    color = Color(0xFFD4D4D2),
                    text = "AC",
                    textColor = Color(0xFF1C1C1C),
                    fontSize = 25.sp
                )
                ButtonCalculator(
                    color = Color(0xFFD4D4D2),
                    text = "+/=",
                    textColor = Color(0xFF1C1C1C),
                    fontSize = 20.sp
                )
                ButtonCalculator(
                    color = Color(0xFFD4D4D2),
                    text = "%",
                    textColor = Color(0xFF1C1C1C),
                    fontSize = 25.sp
                )
                ButtonCalculator(color = Color(0xFFFF9500), text = "÷", textColor = Color.White)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "7",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "8",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "9",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(color = Color(0xFFFF9500), text = "X", textColor = Color.White, fontSize = 30.sp)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "4",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "5",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "6",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(color = Color(0xFFFF9500), text = "-", textColor = Color.White)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "1",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "2",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = "3",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(color = Color(0xFFFF9500), text = "+", textColor = Color.White)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(Modifier.fillMaxWidth(0.5f)) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF505050)),

                        ) {
                        Text(text = "0", color = Color(0xFFD4D4D2), fontSize = 50.sp)
                    }
                }
                ButtonCalculator(
                    color = Color(0xFF505050),
                    text = ".",
                    textColor = Color(0xFFD4D4D2)
                )
                ButtonCalculator(
                    color = Color(0xFFFF9500),
                    text = "=",
                    textColor = Color.White,
                    onClick = {
                        navController.navigate(
                            Screen.Menu.route
                        )
                    })
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun DefaultPreviewOfCalculator() {
    Calculator(navController = rememberNavController())
}
package com.example.calculator.router

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calculator.screens.addemergencycontact.AddEmergencyContact
import com.example.calculator.screens.calculator.Calculator
import com.example.calculator.screens.map.MapScreen
import com.example.calculator.screens.menu.Menu
import com.example.formandroidcompose.router.Screen

@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = Screen.Calculator.route){
        composable(
            route = Screen.Calculator.route
        ){
            Calculator(navController = navController)
        }
        composable(
            route = Screen.Menu.route
        ){
            Menu(navController = navController)
        }
        composable(
            route = Screen.AddEmergencyContact.route
        ){
            AddEmergencyContact(navController = navController)
        }
        composable(
            route = Screen.Map.route
        ){
            MapScreen(navController = navController)
        }
    }
}
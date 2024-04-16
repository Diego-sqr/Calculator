package com.example.formandroidcompose.router

sealed class Screen(val route: String) {
    object Calculator : Screen(route = "calculator")
    object Menu : Screen(route = "menu")
    object AddEmergencyContact : Screen(route = "add_emergency_contact")
    object Map : Screen(route = "map")
}

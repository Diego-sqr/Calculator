package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.calculator.repositories.ContactRepository
import com.example.calculator.router.SetupNavGraph
import com.example.calculator.services.ContactService

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            val contactService = ContactService(context)
            val contactRepository = ContactRepository(context)

            contactService.onCreate(contactRepository.writableDatabase)

            Surface(modifier = Modifier.fillMaxSize()) {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}



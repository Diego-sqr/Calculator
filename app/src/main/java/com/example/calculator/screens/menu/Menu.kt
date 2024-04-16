package com.example.calculator.screens.menu

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calculator.screens.map.viewmodels.LocationViewModel
import com.example.calculator.services.ContactService
import com.example.calculator.services.SendSms
import com.example.formandroidcompose.router.Screen

@Composable
fun Menu(navController: NavController) {
    val context = LocalContext.current
    val contactService = ContactService(context)
    val sendSms = SendSms()
    val contacts = contactService.getContacts()

    fun mToast() {
        Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
    }

    val locationViewModel: LocationViewModel = viewModel()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                locationViewModel.startLocationUpdates()
            } else {
                // Trate o caso de permissão negada
            }
        }
    )

    LaunchedEffect(key1 = true) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationViewModel.startLocationUpdates()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val currentAddress = locationViewModel.address.value

    Surface(
        color = Color.Black, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            sendSms.SendSMSButtonWithPermission(
                contacts,
                "${if (currentAddress?.thoroughfare == null) "Sem rua" else currentAddress.thoroughfare}, " +
                        "${if (currentAddress?.subLocality == null) "Sem bairro" else currentAddress.subLocality}, " +
                        "${if (currentAddress?.subThoroughfare == null) "S/N" else currentAddress.subThoroughfare}, " +
                        if (currentAddress?.postalCode == null) "Sem cep" else currentAddress.postalCode
            )
            Button(
                onClick = {
                    navController.navigate(
                        Screen.Map.route
                    )
                },
                Modifier
                    .fillMaxWidth(0.9f)
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "VERIFICAR LOCAL SEGURO")
            }
            Button(
                onClick = {
                    navController.navigate(
                        Screen.AddEmergencyContact.route
                    )
                },
                Modifier
                    .fillMaxWidth(0.9f)
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "CONTATOS DE EMERGÊNCIA")
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun DefaultPreviewOfMenu() {
    Menu(navController = rememberNavController())
}
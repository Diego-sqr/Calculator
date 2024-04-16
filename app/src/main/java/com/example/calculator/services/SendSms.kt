package com.example.calculator.services

import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calculator.models.Contact
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class SendSms() {

    @Composable
    fun SendSMSButtonWithPermission(contacts: ArrayList<Contact>, address: String) {
        val context = LocalContext.current

        val smsPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    if (contacts.isNotEmpty()) {
                        contacts.forEach {
                            sendSMS(context, it.phone, "SOS, PRECISO DE AJUDA - $address")
                        }
                    }
                }
            }
        )

        Button(
            onClick = {
                smsPermissionLauncher.launch(android.Manifest.permission.SEND_SMS)
            },
            Modifier
                .fillMaxWidth(0.9f)
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = MaterialTheme.shapes.small
        ) {
            Text(text = "ACIONAR EMERGÊNCIA")
        }
    }

    private fun sendSMS(context: Context, phoneNumber: String, message: String) {

        val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(SmsManager::class.java)
        } else {
            SmsManager.getDefault()
        }

        val msg = smsManager.divideMessage(message)
        smsManager.sendMultipartTextMessage("+55$phoneNumber", null, msg, null, null)
        Toast.makeText(context, "Executou", Toast.LENGTH_SHORT).show()
    }

//    fun sendSms(number: String) {
//        val client = OkHttpClient()
//        StrictMode
//            .setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
//
//        val myData = Contact("+55${number}", "Esse é um sms de teste para validar o número")
//
//        val gson = Gson()
//        val json = gson.toJson(myData)
//
//        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
//
//        val request = Request.Builder()
//            .url("https://sms-twilio-api.azurewebsites.net/EnvioSms")
//            .post(body)
//            .build()
//
//        client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) println(IOException("Unexpected code $response")) else println(
//                response.body!!.string()
//            )
//        }
//    }

}
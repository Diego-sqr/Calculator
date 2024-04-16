package com.example.calculator.screens.addemergencycontact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calculator.models.Contact
import com.example.calculator.services.ContactService
import com.example.calculator.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmergencyContact(navController: NavController) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var contacts by remember { mutableStateOf(listOf<Contact>()) }
    var contactSelected by remember { mutableStateOf<Contact?>(null) }
    var isModalVisible by remember { mutableStateOf(false) }

    val contactService = ContactService(context)

    fun getContacts() {
        val contactsResponse = contactService.getContacts()

        contacts = contactsResponse
    }

    LaunchedEffect(key1 = Unit) {
        getContacts()
    }

    fun createContact() {
        if(name == "" || phone == "") return;
        val newContact = Contact(name = name, phone = phone)

        contactService.createContact(newContact)
        getContacts()
    }

    fun updateContact() {
        if (contactSelected != null) {
            val newContact = Contact(
                id = contactSelected?.id,
                name = name,
                phone = phone
            )
            contactService.updateContact(newContact)
            getContacts()
        }
    }

    fun deleteContact(id: String) {
        contactService.deleteContact(id)
        getContacts()
    }

    Surface(
        color = Color.Black, modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

            LazyColumn {
                contacts.forEach { contact ->
                    item {
                        key(contact.id) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = CenterVertically
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(0.8f),
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                                ) {
                                    Text(text = contact.name, color = Color.White)
                                    Text(text = contact.phone, color = Color.White)
                                }
                                IconButton(onClick = {
                                    name = contact.name
                                    phone = contact.phone
                                    contactSelected = contact
                                    isModalVisible = true
                                }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                                IconButton(onClick = { deleteContact(contact.id!!) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { isModalVisible = true },
                    modifier = Modifier.background(
                        color = Purple40,
                        shape = MaterialTheme.shapes.extraLarge
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }

            }
        }
    }
    if (isModalVisible) {
        AlertDialog(
            onDismissRequest = {
                name = ""
                phone = ""
                contactSelected = null
                isModalVisible = false
            },
            title = {
                Row(verticalAlignment = CenterVertically) {
                    Button(
                        onClick = {
                            name = ""
                            phone = ""
                            contactSelected = null
                            isModalVisible = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) {
                        Text(text = "X", color = Color.Black, fontSize = 16.sp)
                    }
                    Text(if (contactSelected != null) "Editar contato" else "Novo contato")
                }
            },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "Nome")
                        }
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Nome") }
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "Telefone")
                        }
                        TextField(
                            value = phone,
                            onValueChange = {
                                if (it.length < 12) {
                                    phone = it
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "9999999999") }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (contactSelected != null) updateContact() else createContact()
                        name = ""
                        phone = ""
                        contactSelected = null
                        isModalVisible = false
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = if (contactSelected != null) "Editar contato" else "Adicionar contato")
                }
            },

            containerColor = Color.White
        )

    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun DefaultPreviewOfAddEmergencyContact() {
    AddEmergencyContact(navController = rememberNavController())
}
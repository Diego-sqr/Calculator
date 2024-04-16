package com.example.calculator.services

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.calculator.models.Contact
import com.example.calculator.repositories.ContactRepository

class ContactService(context: Context) {
    private val contactRepository = ContactRepository(context)

    fun onCreate(db: SQLiteDatabase?){
        contactRepository.onCreate(db)
    }

    fun createContact(contact: Contact){
        contactRepository.createContact(contact);
    }

    fun getContacts(): ArrayList<Contact>{
        return contactRepository.getContacts();
    }

    fun updateContact(contact: Contact){
        contactRepository.updateContact(contact);
    }

    fun deleteContact(id: String){
        contactRepository.deleteContact(id);
    }
}
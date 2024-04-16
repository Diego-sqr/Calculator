package com.example.calculator.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.calculator.models.Contact
import java.util.UUID

class ContactRepository(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "calculator.db"
        private const val TBL_Contact = "tbl_contacts"
        private const val ID = "id"
        private const val NAME = "name"
        private const val PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStudent = ("CREATE TABLE IF NOT EXISTS " + TBL_Contact + "("
                + ID + " TEXT PRIMARY KEY," + NAME + " TEXT,"
                + PHONE + " TEXT" + ")")

        db?.execSQL(createTableStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_Contact")
        onCreate(db)
    }


    fun createContact(contact: Contact): String {
        val db = this.writableDatabase

        val contentVales = ContentValues()
        val newId = UUID.randomUUID().toString()

        contentVales.put(ID, newId)
        contentVales.put(NAME, contact.name)
        contentVales.put(PHONE, contact.phone)

        val success = db.insert(TBL_Contact, null, contentVales)
        db.close()

        if (success != -1L) {
            return newId
        } else {
            throw Exception("Failed to insert contact")
        }
    }

    fun getContacts(): ArrayList<Contact> {
        val contactList: ArrayList<Contact> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_Contact"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {

            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: String
        var name: String
        var phone: String

        if (cursor.moveToFirst()) {
            do {
                val idColumnIndex = cursor.getColumnIndex("id")
                val nameColumnIndex = cursor.getColumnIndex("name")
                val phoneColumnIndex = cursor.getColumnIndex("phone")

                id = if(idColumnIndex != -1 && !cursor.isNull(idColumnIndex)) {
                    cursor.getString(idColumnIndex)
                } else {
                    ""
                }
                name = if (nameColumnIndex != -1 && !cursor.isNull(nameColumnIndex)) {
                    cursor.getString(nameColumnIndex)
                } else {
                    ""
                }
                phone = if (phoneColumnIndex != -1 && !cursor.isNull(phoneColumnIndex)) {
                    cursor.getString(phoneColumnIndex)
                } else {
                    ""
                }

                val contact = Contact(
                    id = id,
                    name = name,
                    phone = phone
                )
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
        return contactList
    }

    fun updateContact(contact: Contact): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("name", contact.name)
        values.put("phone", contact.phone)

        val result = db.update(TBL_Contact, values, "id = ?", arrayOf(contact.id.toString()))
        db.close()
        return Integer.parseInt("$result") != -1
    }

    fun deleteContact(userId: String): Boolean {
        val db = this.writableDatabase

        val result = db.delete(TBL_Contact, "id = ?", arrayOf(userId.toString()))

        db.close()
        return Integer.parseInt("$result") != -1
    }
}
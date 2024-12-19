package com.example.tteolioneapp.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferencesUtil(context: Context) {
    val SHARED_PREFERENCES_NAME = "smartstore_preference"


    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getNotice(): MutableList<String> {
        val str = preferences.getString("notice", "")!!
        val list = if (str.isEmpty()) mutableListOf<String>() else Gson().fromJson(
            str,
            MutableList::class.java
        ) as MutableList<String>

        return list
    }

}
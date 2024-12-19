package com.example.tteolioneapp.data.dto

import com.google.gson.annotations.SerializedName

data class EmailSendData(
    @SerializedName("email")
    val email: String
)

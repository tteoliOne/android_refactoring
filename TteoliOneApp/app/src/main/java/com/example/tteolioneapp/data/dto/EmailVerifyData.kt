package com.example.tteolioneapp.data.dto

import com.google.gson.annotations.SerializedName

data class EmailVerifyData(
    @SerializedName("code")
    val authCode: String,
    @SerializedName("email")
    val email: String
)

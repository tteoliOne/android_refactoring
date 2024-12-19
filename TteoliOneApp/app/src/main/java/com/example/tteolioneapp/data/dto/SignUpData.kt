package com.example.tteolioneapp.data.dto

import com.google.gson.annotations.SerializedName

data class SignUpData(
    @SerializedName("loginId")
    val loginId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String,
)

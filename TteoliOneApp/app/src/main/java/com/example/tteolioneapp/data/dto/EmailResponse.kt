package com.example.tteolioneapp.data.dto

data class EmailResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: String
)

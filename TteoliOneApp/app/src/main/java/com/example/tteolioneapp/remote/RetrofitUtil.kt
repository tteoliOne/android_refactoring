package com.example.tteolioneapp.remote

import com.example.tteolioneapp.base.ApplicationClass

class RetrofitUtil {
    companion object{
        val loginService = ApplicationClass.retrofit.create(LoginService::class.java)
    }
}
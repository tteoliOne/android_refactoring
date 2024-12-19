package com.example.tteolioneapp.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.example.tteolioneapp.BuildConfig
import com.example.tteolioneapp.data.local.SharedPreferencesUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        // ipconfig를 통해 ip확인하기
        // 핸드폰으로 접속은 같은 인터넷으로 연결 되어있어야함 (유,무선)
        const val SERVER_URL = BuildConfig.SERVER_URL

        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        lateinit var retrofit: Retrofit

    }


    override fun onCreate() {
        super.onCreate()

        //shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)

        // 로깅 인터셉터
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e("Post", "log: message ${message}")
            }
        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)


        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성

        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
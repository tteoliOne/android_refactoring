package com.example.tteolioneapp.remote

import com.example.tteolioneapp.data.dto.EmailResponse
import com.example.tteolioneapp.data.dto.EmailSendData
import com.example.tteolioneapp.data.dto.EmailVerifyData
import com.example.tteolioneapp.data.dto.NicknameData
import com.example.tteolioneapp.data.dto.SignUpData
import com.example.tteolioneapp.data.dto.SignUpIdData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface LoginService {

    // 이메일 보내기
    @POST("/api/v2/email/send")
    suspend fun postEmailData(@Body email: EmailSendData): Response<EmailResponse>


    // 이메일 검증
    @POST("/api/v2/email/verify")
    suspend fun postEmailVerifyData(@Body email: EmailVerifyData): Response<EmailResponse>

    // 아이디 중복 확인
    @POST("/api/users/check/login-id")
    suspend fun postCheckId(@Body loginId: SignUpIdData): Response<EmailResponse>


    // 닉네임 중복 확인
    @POST("/api/users/check/nickname")
    suspend fun postCheckNickname(@Body nicknameData: NicknameData): Response<EmailResponse>

    // 회원가입 정보 보내기
    @Multipart
    @POST("/api/users/signup") // Replace with your API endpoint
    suspend fun postSignupData(
        @Part profile: MultipartBody.Part,
        @Part("signUpRequest") signUpRequest: SignUpData,
    ): Response<EmailResponse>

}
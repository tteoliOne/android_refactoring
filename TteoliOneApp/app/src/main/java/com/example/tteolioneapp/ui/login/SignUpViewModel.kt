package com.example.tteolioneapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tteolioneapp.base.SignupDialog
import com.example.tteolioneapp.data.dto.EmailResponse
import com.example.tteolioneapp.data.dto.EmailSendData
import com.example.tteolioneapp.data.dto.EmailVerifyData
import com.example.tteolioneapp.data.dto.NicknameData
import com.example.tteolioneapp.data.dto.SignUpData
import com.example.tteolioneapp.data.dto.SignUpIdData
import com.example.tteolioneapp.remote.RetrofitUtil.Companion.loginService
import com.example.tteolioneapp.ui.LoginActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

private const val TAG = "SingUpViewModel_Post"

class SignUpViewModel : ViewModel() {
    private var _user = MutableLiveData<SignUpData>()
    val user: LiveData<SignUpData>
        get() = _user

    private var _verifyResponse = MutableLiveData<EmailResponse>()
    val verifyResponse: LiveData<EmailResponse>
        get() = _verifyResponse

    private var _idResponse = MutableLiveData<EmailResponse>()
    val idResponse: LiveData<EmailResponse>
        get() = _idResponse

    private var _nicknameResponse = MutableLiveData<EmailResponse>()
    val nicknameResponse: LiveData<EmailResponse>
        get() = _nicknameResponse

    private var _emailResponse = MutableLiveData<EmailResponse>()
    val emailResponse: LiveData<EmailResponse>
        get() = _emailResponse

    private var _signUpResponse = MutableLiveData<EmailResponse>()
    val signUpResponse: LiveData<EmailResponse>
        get() = _signUpResponse

    private var _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private var _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private var _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname


    // 이메일 보내기
    fun sendEmail(email: String, activity: LoginActivity) {
        viewModelScope.launch {
            try {
                getEmail(email)
                val response = loginService.postEmailData(EmailSendData(email))
                if (response.isSuccessful) {
                    _emailResponse.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (!errorBody.isNullOrEmpty()) {
                        val errorResponse = Gson().fromJson(errorBody, EmailResponse::class.java)
                        _emailResponse.value = errorResponse
                        showDialog(errorResponse.message, activity)
                    }
                }
            } catch (e: Exception) {
                showDialog("인터넷 연결을 확인해주세요", activity)
                Log.d(TAG, "sendEmail: $e")

                e.printStackTrace()

            }
        }
    }

    // 이메일 인증 보내기
    fun verifyEmail(code: String, activity: LoginActivity) {
        val email = email.value
        email?.let {
            val emailData = EmailVerifyData(code, it)
            viewModelScope.launch {
                try {
                    val response = loginService.postEmailVerifyData(emailData)
                    if (response.isSuccessful) {
                        _verifyResponse.value = response.body()
                    } else {
                        errorDialog(response, activity)
                    }


                } catch (e: Exception) {
                    showDialog("인터넷 연결을 확인해주세요", activity)
                    Log.d(TAG, "sendEmail: $e")
                    e.printStackTrace()

                }
            }
        }

    }

    // 이메일 보내기
    fun sendId(id: String, activity: LoginActivity) {
        viewModelScope.launch {
            try {
                getId(id)
                val response = loginService.postCheckId(SignUpIdData(id))
                if (response.isSuccessful) {
                    _idResponse.value = response.body()
                } else {
                    errorDialog(response, activity)
                }
            } catch (e: Exception) {
                _idResponse.value = EmailResponse(false, 1001, "응답시간 초과", "오류")
            }
        }
    }


    // 닉네임 중복 확인
    fun postCheckNickname(nickname: String, activity: LoginActivity) {
        viewModelScope.launch {
            try {
                getNickname(nickname)
                val response = loginService.postCheckNickname(NicknameData(nickname))
                if (response.isSuccessful) {
                    _nicknameResponse.value = response.body()
                } else {
                    errorDialog(response, activity)
                }
            } catch (e: Exception) {
                _nicknameResponse.value = EmailResponse(false, 1001, "응답시간 초과", "오류")
            }
        }
    }

    // 회원가입 정보 보내기
    fun postSignup(profile: MultipartBody.Part, activity: LoginActivity) {
        viewModelScope.launch {
            try {
                val signUpData = SignUpData(
                    loginId = id.value ?: "",
                    email = email.value ?: "",
                    username = name.value ?: "",
                    nickname = nickname.value ?: "",
                    password = password.value ?: ""
                )
                Log.d(TAG, "postSignup: $signUpData")
                val response =
                    loginService.postSignupData(signUpRequest = signUpData, profile = profile)
                if (response.isSuccessful) {
                    _signUpResponse.value = response.body()
                } else {
                    errorDialog(response, activity)
                }
            } catch (e: Exception) {
                _signUpResponse.value = EmailResponse(false, 1001, "응답시간 초과", "오류")
            }
        }
    }


    fun resetResponse() {
        _verifyResponse = MutableLiveData<EmailResponse>()
        _idResponse = MutableLiveData<EmailResponse>()
        _nicknameResponse = MutableLiveData<EmailResponse>()
        _emailResponse = MutableLiveData<EmailResponse>()
    }

    fun getEmail(email: String) {
        _email.value = email
    }

    fun getName(name: String) {
        _name.value = name
    }

    fun getId(id: String) {
        _id.value = id
    }

    fun getPassword(password: String) {
        _password.value = password
    }

    fun getNickname(nickname: String) {
        _nickname.value = nickname
    }


    // 알림창 띄우기
    fun showDialog(message: String, activity: LoginActivity) {
        val dialog = SignupDialog(message)
        // 알림창이 띄워져있는 동안 배경 클릭 막기
        dialog.isCancelable = false
        dialog.show(
            activity.supportFragmentManager,
            "SignupDialog"
        )
    }

    // 에러 알람창 띄우기
    private fun errorDialog(
        response: Response<EmailResponse>,
        activity: LoginActivity,
    ) {
        val errorBody = response.errorBody()?.string()
        if (!errorBody.isNullOrEmpty()) {
            val errorResponse = Gson().fromJson(errorBody, EmailResponse::class.java)
            showDialog(errorResponse.message, activity)
        } else {
            showDialog("알 수 없는 에러가 발생했습니다.", activity)
        }
    }


}
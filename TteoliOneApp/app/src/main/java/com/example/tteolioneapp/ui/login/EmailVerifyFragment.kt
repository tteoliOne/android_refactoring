package com.example.tteolioneapp.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tteolioneapp.R
import com.example.tteolioneapp.base.BaseFragment
import com.example.tteolioneapp.databinding.FragmentEmailVerifyBinding
import com.example.tteolioneapp.ui.LoginActivity
import okhttp3.internal.format
import java.util.Timer
import kotlin.concurrent.timer


class EmailVerifyFragment : BaseFragment<FragmentEmailVerifyBinding>(
    FragmentEmailVerifyBinding::bind,
    R.layout.fragment_email_verify
) {
    private lateinit var loginActivity: LoginActivity
    private val activityViewModel: SignUpViewModel by activityViewModels()

    // 타이머 초기 변수 설정
    private var timer: Timer? = null
    private var countdownMinutes = 3

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 타이머 설정
        initTimer()

        // 코드 유효성 검사
        checkEditText()

        // 완료 버튼 클릭
        clickConfirmButton()

        activityViewModel.verifyResponse.observe(viewLifecycleOwner) {
            loginActivity.openFragment(5)
            activityViewModel.resetResponse()
        }

    }

    // 타이머 설정 함수
    private fun initTimer() {

        // 분을 초로 바꿈
        var countdownSeconds = countdownMinutes * 60

        timer = timer(initialDelay = 0, period = 1000) {
            if (countdownSeconds <= 0) {

                // 이전화면으로 이동 함수 호출
                binding.emailConfirmTimeTextView.post { loginActivity.supportFragmentManager.popBackStack() }

            } else {
                countdownSeconds -= 1
                val second = countdownSeconds % 60
                val minutes = (countdownSeconds / 60).toInt()
                binding.emailConfirmTimeTextView.post {
                    binding.emailConfirmTimeTextView.text = format("%02d:%02d", minutes, second)
                }

            }
        }
    }

    // 코드 유효성 검사
    private fun checkEditText() {
        binding.emailConfirmEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.count() >= 7) {
                    binding.confirmButton.setBackgroundColor(Color.parseColor("#588F11"))
                    binding.confirmButton.isClickable = true
                } else {
                    binding.confirmButton.setBackgroundColor(Color.parseColor("#CDCFCECE"))
                    binding.confirmButton.isClickable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 완료버튼 클릭 시 함수
    private fun clickConfirmButton() {
        binding.confirmButton.setOnClickListener {
            val emailCode = binding.emailConfirmEditText.text.toString()

            activityViewModel.verifyEmail(emailCode, loginActivity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }

}
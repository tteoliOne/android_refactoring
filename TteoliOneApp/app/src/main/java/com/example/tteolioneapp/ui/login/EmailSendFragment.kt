package com.example.tteolioneapp.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.tteolioneapp.R
import com.example.tteolioneapp.base.BaseFragment
import com.example.tteolioneapp.databinding.FragmentEmailSendBinding
import com.example.tteolioneapp.ui.LoginActivity
import java.util.regex.Pattern

private const val TAG = "EmailSendFragment_Post"

class EmailSendFragment : BaseFragment<FragmentEmailSendBinding>(
    FragmentEmailSendBinding::bind,
    R.layout.fragment_email_send
) {

    private val activityViewModel: SignUpViewModel by activityViewModels()
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이메일 유효성 검사
        checkEditText()

        // 다음 버튼 클릭
        clickNextButton()

        activityViewModel.emailResponse.observe(viewLifecycleOwner) {
            hideProgress()
            Log.d(TAG, "clickNextButton: ${it.code}")
            if (it.code == 0) {
                loginActivity.openFragment(4)
                activityViewModel.resetResponse()
            }
        }
    }

    // editText 가 형식에 맞게 적었는지 확인하는 함수
    private fun checkEditText() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("emailEditText", "$s $start $before $count")
                if (Pattern.matches(
                        "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})",
                        binding.emailEditText.text
                    )
                ) {
                    binding.nextButton.setBackgroundColor(Color.parseColor("#588F11"))
                    binding.nextButton.isClickable = true
                } else {
                    binding.nextButton.setBackgroundColor(Color.parseColor("#CDCFCECE"))
                    binding.nextButton.isClickable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 다음 버튼 클릭 함수
    private fun clickNextButton() {
        binding.nextButton.setOnClickListener {
            if (Pattern.matches(
                    "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})",
                    binding.emailEditText.text
                )
            ) {
                // 로딩바 띄우기 함수 호출
                showProgress()

                val email = binding.emailEditText.text.toString()

                activityViewModel.sendEmail(email, loginActivity)


            }
        }
    }

    // 로딩바 띄우기 함수
    private fun showProgress() {
        binding.progressBarLayout.isVisible = true
    }

    // 로딩바 내리기 함수
    private fun hideProgress() {
        binding.progressBarLayout.isVisible = false
    }


}
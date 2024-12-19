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
import com.example.tteolioneapp.databinding.FragmentSignUpNameBinding
import com.example.tteolioneapp.ui.LoginActivity

class SignUpNameFragment : BaseFragment<FragmentSignUpNameBinding>(
    FragmentSignUpNameBinding::bind,
    R.layout.fragment_sign_up_name
) {
    private val activityViewModel: SignUpViewModel by activityViewModels()
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이름 유효성 검사
        checkEmptyName()

        clickNextButton()

    }


    // 이름 유효성 검사
    private fun checkEmptyName() {
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
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

    // 다음 버튼 클릭 시 함수
    private fun clickNextButton() {
        binding.nextButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            activityViewModel.getName(name)
            loginActivity.openFragment(6)
        }
    }

}
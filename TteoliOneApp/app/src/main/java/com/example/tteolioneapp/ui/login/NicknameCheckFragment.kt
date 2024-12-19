package com.example.tteolioneapp.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.tteolioneapp.R
import com.example.tteolioneapp.base.BaseFragment
import com.example.tteolioneapp.base.SignupDialog
import com.example.tteolioneapp.databinding.FragmentNicknameCheckBinding
import com.example.tteolioneapp.ui.LoginActivity


class NicknameCheckFragment : BaseFragment<FragmentNicknameCheckBinding>(
    FragmentNicknameCheckBinding::bind,
    R.layout.fragment_nickname_check
) {
    private val activityViewModel: SignUpViewModel by activityViewModels()
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 입력칸 유효성 검사
        checkEdit()

        activityViewModel.nicknameResponse.observe(viewLifecycleOwner) {
            if (it.success) {
                binding.confirmButton.isVisible = false
                binding.completeButton.isVisible = true
                showDialog("사용가능한 닉네임 입니다.")
            } else {
                showDialog(it.message)
            }
        }

        binding.confirmButton.setOnClickListener {

            val nickname = binding.nicknameEditText.text.toString()

            activityViewModel.postCheckNickname(nickname, loginActivity)

        }

        binding.completeButton.setOnClickListener {
            loginActivity.openFragment(9)
            activityViewModel.resetResponse()
        }

    }

    // 입력칸 유효성 검사
    private fun checkEdit() {
        binding.nicknameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    binding.confirmButton.isVisible = true
                    binding.completeButton.isVisible = false
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

    // 실패 알림창 띄우기
    private fun showDialog(message: String) {
        val dialog = SignupDialog(message)
        // 알림창이 띄워져있는 동안 배경 클릭 막기
        dialog.isCancelable = false
        dialog.show(
            this@NicknameCheckFragment.requireActivity().supportFragmentManager,
            "SignupDialog"
        )
    }


}
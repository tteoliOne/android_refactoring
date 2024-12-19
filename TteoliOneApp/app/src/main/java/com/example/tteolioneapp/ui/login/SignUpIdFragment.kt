package com.example.tteolioneapp.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tteolioneapp.R
import com.example.tteolioneapp.base.BaseFragment
import com.example.tteolioneapp.databinding.FragmentSignUpIdBinding
import com.example.tteolioneapp.ui.LoginActivity
import java.util.regex.Pattern

private const val TAG = "SignUpIdFragment_Post"

class SignUpIdFragment : BaseFragment<FragmentSignUpIdBinding>(
    FragmentSignUpIdBinding::bind,
    R.layout.fragment_sign_up_id
) {
    private lateinit var loginActivity: LoginActivity
    private val activityViewModel: SignUpViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 아이디 유효성 검사
        checkIdEditText()

        // 다음 버튼 클릭
        clickNextButton()

        activityViewModel.idResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "clickNextButton: ${it.code}")
            if (it.code == 0) {
                loginActivity.openFragment(7)
            } else {
                activityViewModel.showDialog(it.message, loginActivity)
            }
        }


    }

    // 아이디 유효성 검사
    private fun checkIdEditText() {
        binding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (Pattern.matches("^(?=.*[a-z])[a-z0-9]{6,20}\$", s)) {
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

    // 다음버튼 클릭 시 함수
    private fun clickNextButton() {
        binding.nextButton.setOnClickListener {

            val id = binding.idEditText.text.toString()
            // id 유효성 검사 함수 호출
            if (Pattern.matches("^(?=.*[a-z])[a-z0-9]{6,20}\$", id)) {
                activityViewModel.sendId(id, loginActivity)
            }

        }
    }


}
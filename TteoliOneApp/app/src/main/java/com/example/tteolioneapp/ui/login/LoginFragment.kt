package com.example.tteolioneapp.ui.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tteolioneapp.R
import com.example.tteolioneapp.base.BaseFragment
import com.example.tteolioneapp.databinding.FragmentLoginBinding
import com.example.tteolioneapp.ui.LoginActivity

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
) {
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button()
    }

    private fun button() {

        // 회원가입 버튼 클릭
        binding.signUpButton.setOnClickListener {
            loginActivity.openFragment(3)
        }

    }


}
package com.example.tteolioneapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tteolioneapp.R
import com.example.tteolioneapp.base.BaseFragment
import com.example.tteolioneapp.databinding.FragmentSignUpFinishBinding
import com.example.tteolioneapp.ui.LoginActivity

class SignUpFinishFragment : BaseFragment<FragmentSignUpFinishBinding>(
    FragmentSignUpFinishBinding::bind,
    R.layout.fragment_sign_up_finish
) {
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finishButton.setOnClickListener {
            loginActivity.openFragment(2)
        }


    }
}
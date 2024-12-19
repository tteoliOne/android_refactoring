package com.example.tteolioneapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.example.tteolioneapp.MainActivity
import com.example.tteolioneapp.R
import com.example.tteolioneapp.base.BaseActivity
import com.example.tteolioneapp.databinding.ActivityLoginBinding
import com.example.tteolioneapp.ui.login.EmailSendFragment
import com.example.tteolioneapp.ui.login.EmailVerifyFragment
import com.example.tteolioneapp.ui.login.LoginFragment
import com.example.tteolioneapp.ui.login.NicknameCheckFragment
import com.example.tteolioneapp.ui.login.SignUpFinishFragment
import com.example.tteolioneapp.ui.login.SignUpIdFragment
import com.example.tteolioneapp.ui.login.SignUpNameFragment
import com.example.tteolioneapp.ui.login.SignUpPasswordFragment
import com.example.tteolioneapp.ui.login.SignUpProfileFragment
import com.example.tteolioneapp.ui.login.SignUpViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val mainActivityViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            // 최초 실행 시에만 프레그먼트를 추가
            openFragment(2)
        }
    }

    fun openFragment(int: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (int) {
            1 -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }

            2 -> {
                // 회원가입한 뒤 돌아오면, 2번에서 addToBackStack해 놓은게 남아 있어서,
                // stack을 날려 줘야 한다. stack날리기.
                supportFragmentManager.popBackStack()
                transaction.replace(R.id.frame_layout_login, LoginFragment())
            }

            3 -> {
                transaction.replace(R.id.frame_layout_login, EmailSendFragment())
                    .addToBackStack(null)
            }

            4 -> {
                transaction.replace(R.id.frame_layout_login, EmailVerifyFragment())
                    .addToBackStack(null)
            }

            5 -> {
                transaction.replace(R.id.frame_layout_login, SignUpNameFragment())
                    .addToBackStack(null)
            }

            6 -> {
                transaction.replace(R.id.frame_layout_login, SignUpIdFragment())
                    .addToBackStack(null)
            }

            7 -> {
                transaction.replace(R.id.frame_layout_login, SignUpPasswordFragment())
                    .addToBackStack(null)
            }

            8 -> {
                transaction.replace(R.id.frame_layout_login, NicknameCheckFragment())
                    .addToBackStack(null)
            }

            9 -> {
                transaction.replace(R.id.frame_layout_login, SignUpProfileFragment())
                    .addToBackStack(null)
            }

            10 -> {
                supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
                transaction.replace(R.id.frame_layout_login, SignUpFinishFragment())
            }
        }
        transaction.commit()
    }
}
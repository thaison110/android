package com.sonnv.kidsonline.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.sonnv.kidsonline.databinding.ActivityLoginBinding
import com.sonnv.kidsonline.extension.hideKeyboardClickOutside
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.extension.setShowPassword
import com.sonnv.kidsonline.model.response.LoginData
import com.sonnv.kidsonline.ui.activity.BaseActivity
import com.sonnv.kidsonline.util.PARAM_LOGIN_DATA
import com.sonnv.kidsonline.util.PASS_WORD
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.USER_NAME
import com.sonnv.kidsonline.viewmodel.LoginViewModel

class LoginActivity : BaseActivity() {
    private var mBinding: ActivityLoginBinding? = null
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        hideActionBar()
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        mBinding?.root?.hideKeyboardClickOutside(this)
        initListener()
        initObserve()
        checkLoginAlready()
    }

    private fun checkLoginAlready() {
        val userName = PrefUtils.getInstance().getValue(USER_NAME, "")
        val password = PrefUtils.getInstance().getValue(PASS_WORD, "")

        if (userName.isNotEmpty() && password.isNotEmpty()) {
            showProgress()
            loginViewModel.login(this@LoginActivity, userName, password)
        }
    }

    private fun gotoMainScreen(loginData: LoginData) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra(PARAM_LOGIN_DATA, loginData)
        startActivity(intent)
        finish()
    }

    private fun initObserve() {
        loginViewModel.loginLiveData.observe(this) {
            hideProgress()

            it?.let {
                gotoMainScreen(it)
                return@let
            }

            if (it == null) {
                PrefUtils.getInstance().clearAll()
                Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkBeforeLogin()
    }

    private fun initListener() {
        mBinding?.apply {
            edtUser.setOnFocusChangeListener { view, b ->
                view.isSelected = b
            }

            edtPass.setOnFocusChangeListener { view, b ->
                view.isSelected = b
                edtUser.isSelected = !b
            }

            btnLogIn.setOnClickAffect {
                if (checkBeforeLogin()){
                    showProgress()
                    loginViewModel.login(this@LoginActivity,
                        edtUser.text.toString(),
                        edtPass.text.toString(),
                        checkboxSavePass.isChecked)
                }
            }
            btnEyes.setShowPassword(edtPass)
        }
    }

    private fun showProgress() {
        mBinding?.apply {
            progress.isClickable = true
            progress.isVisible = true
        }
    }

    private fun hideProgress() {
        mBinding?.apply {
            progress.isClickable = false
            progress.isVisible = false
        }
    }

    private fun checkBeforeLogin(): Boolean{
        mBinding?.apply {
            if (edtUser.text.isEmpty() || edtUser.text.isBlank()){
                edtUser.requestFocus()
                return false
            }
            if (edtPass.text.isEmpty() || edtPass.text.isBlank()){
                edtPass.requestFocus()
                return false
            }
            edtUser.clearFocus()
            edtPass.clearFocus()
            return true
        }
        return false
    }


    private fun hideActionBar() {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar?.hide()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}
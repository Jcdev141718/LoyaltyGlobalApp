package com.loyaltyglobal.ui.main.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.request.LoginRequest
import com.loyaltyglobal.data.model.response.LoginResponse
import com.loyaltyglobal.data.source.network.NetworkResult
import com.loyaltyglobal.databinding.FragmentLoginBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.viewmodel.LoginViewModel
import com.loyaltyglobal.util.*
import com.loyaltyglobal.util.Constants.AGENCY_ID
import com.loyaltyglobal.util.Constants.PREF_USER_ID
import com.loyaltyglobal.util.Constants.RC_SIGN_IN
import com.loyaltyglobal.util.Constants.USER_NAME_KEY
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    lateinit var mBinding: FragmentLoginBinding
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var isSignInWithGoogle: Boolean = false
    private val loginViewModel: LoginViewModel by viewModels()
    private var isNormalLogin = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        initObserver()
    }

    private fun initObserver() {
        loginViewModel.logInResponse.observe(this, {
            if (it != null) {
                handleLoginResponse(it)
            }
        })
    }

    private fun handleLoginResponse(result: NetworkResult<LoginResponse>) {
        mBinding.apply {
            when (result) {
                is NetworkResult.Loading -> {
                    if (isNormalLogin) {
                        groupNextArrow.hide()
                        progressbar.show()
                    } else {
                        progressbarGoogle.show()
                        txtContinueWithGoogle.hide()
                    }

                }
                is NetworkResult.Success -> {
                    progressbar.hide()
                    groupNextArrow.show()
                    progressbarGoogle.hide()
                    txtContinueWithGoogle.show()
                    mBaseActivity?.mPreferenceProvider?.setValue(PREF_USER_ID, result.responseData?.data?._id.toString())
                    if (result.responseData?.data?.isNumberVerified == true) {
                        activity?.addReplaceFragment(
                            R.id.container_main, EnterNameFragment(), true,
                            addToBackStack = true
                        )
                    } else {
                        activity?.addReplaceFragment(
                            R.id.container_main,
                            EnterMobileNumberFragment().apply {
                                arguments = Bundle().apply {
                                    putString(
                                        USER_NAME_KEY,
                                        "${result.responseData?.data?.firstName} ${result.responseData?.data?.lastName}"
                                    )
                                }
                            },
                            true,
                            addToBackStack = true
                        )
                    }
                }
                is NetworkResult.Error -> {
                    progressbar.hide()
                    progressbarGoogle.hide()
                    txtContinueWithGoogle.show()
                    groupNextArrow.show()
                    result.message?.let { activity?.showToast(it) }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mBinding.apply {
            llContinueWithGoogle.setOnClickListener {
                isNormalLogin = false
                signIn()
            }

            clTxtNext.clickWithDebounce {
                isNormalLogin = true
                doLogin(LoginRequest(
                    AGENCY_ID, "",
                    edtEmail.text.toString(), "email",
                    AGENCY_ID
                ))
                activity?.hideKeyboard()
            }

            edtEmail.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty() && edtEmail.text.toString().trim().isEmailValid()) {
                    changeButtonState(true)
                } else {
                    changeButtonState(false)
                }
            }
        }



    }
    private fun changeButtonState(isEnable : Boolean) {
        mBinding.apply {
            if (isEnable) {
                clTxtNext.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_filled_button)
            } else {
                clTxtNext.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_filled_button_disable
                )
            }
        }

    }




    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN && resultCode != 0) {
            try {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java)!!
                if (resultCode != 0) {
                    handleSignInResult(task)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("TAG", "Google sign in failed", e)
            }

        } else {
            activity?.showToast("Error:resultCode = 0")
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount =
                completedTask.getResult(com.google.android.gms.common.api.ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            updateUI(null)
        }
    }


    private fun updateUI(account: GoogleSignInAccount?) {
        isSignInWithGoogle = true
        mBinding.edtEmail.setText(account?.email)
        account?.email?.let {
            LoginRequest(
                AGENCY_ID, "",
                it, "email",
                AGENCY_ID
            )
        }?.let { doLogin(it) }
    }

    private fun doLogin(loginRequest: LoginRequest) {
        mBinding.apply {
            when {
                edtEmail.text.isNullOrEmpty() -> {
                    activity?.showToast(requireActivity().getString(R.string.please_enter_email))
                }
                !edtEmail.text.toString().isEmailValid() -> {
                    activity?.showToast(requireActivity().getString(R.string.please_enter_valid_email_address))
                }
                else -> {
                    loginViewModel.logIn(loginRequest)
                }
            }
        }
    }
}

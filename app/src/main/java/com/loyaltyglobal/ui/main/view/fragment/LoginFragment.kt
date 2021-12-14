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
import androidx.fragment.app.Fragment
import com.loyaltyglobal.R
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
import com.loyaltyglobal.util.addReplaceFragment
import com.loyaltyglobal.util.Constants.AGENCY_ID
import com.loyaltyglobal.util.Constants.RC_SIGN_IN
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.hideKeyboard
import com.loyaltyglobal.util.isEmailValid
import com.loyaltyglobal.util.show
import com.loyaltyglobal.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var mBinding: FragmentLoginBinding
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var isSignInWithGoogle: Boolean = false
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure Google Sign In
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        initObserver()
    }

    private fun initObserver() {
        loginViewModel.logInResponse.observe(this, {
           if (it != null){
               handleLoginResponse(it)
           }
        })
    }

    private fun handleLoginResponse(result: NetworkResult<LoginResponse>) {
        when (result) {
            is NetworkResult.Loading -> {
                // show a progress bar
                Log.e("TAG", "handleUserData() --> Loading  $result")
                mBinding.progressbar.show()
            }
            is NetworkResult.Success -> {
                // bind data to the view
                Log.e("TAG", "handleUserData() --> Success  $result")
                mBinding.progressbar.hide()
            }
            is NetworkResult.Error -> {
                // show error message
                Log.e("TAG", "handleUserData() --> Error ${result.message}")
                mBinding.progressbar.hide()
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

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.clTxtNext.setOnClickListener {
            activity?.addReplaceFragment(R.id.container_main, EnterMobileNumberFragment(), true,
                addToBackStack = true
            )
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mBinding.llContinueWithGoogle.setOnClickListener {
            signIn()
        }

        mBinding.clTxtNext.setOnClickListener {
            if (mBinding.edtEmail.text?.trim()?.isNotEmpty() == true) {
                when {
                    mBinding.edtEmail.text?.trim().isNullOrEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.please_enter_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    !mBinding.edtEmail.isEmailValid() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.invalid_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    mBinding.edtEmail.isEmailValid() && !mBinding.edtEmail.text?.trim()
                        .isNullOrEmpty() -> {
                        mBinding.groupNextArrow.hide()
                        mBinding.progressbar.show()
                    }
                    else -> {
                        loginViewModel.logIn(
                            LoginRequest(
                                AGENCY_ID, "",
                                mBinding.edtEmail.text.toString(), "email",
                                AGENCY_ID
                            )
                        )

                        activity?.hideKeyboard()
                    }
                }
            }
        }
        mBinding.edtEmail.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                mBinding.clTxtNext.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.shape_filled_button)
            } else {
                mBinding.clTxtNext.background = ContextCompat.getDrawable(
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
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data) // Google Sign In was successful, authenticate with Firebase
                val account =
                    task.getResult(com.google.android.gms.common.api.ApiException::class.java)!! //                firebaseAuthWithGoogle(account.idToken!!)
                if (resultCode != 0) {
                    handleSignInResult(task)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("TAG", "Google sign in failed", e)
            }

        } else {
            Toast.makeText(requireContext(), "Error:resultCode = 0", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(requireContext(), account?.email, Toast.LENGTH_SHORT).show()
        account?.email?.let {
            LoginRequest(
                AGENCY_ID, "",
                it, "email",
                AGENCY_ID
            )
        }?.let { loginViewModel.logIn(it) }
    }
}

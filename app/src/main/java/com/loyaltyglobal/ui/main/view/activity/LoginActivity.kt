package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import android.util.Log
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivityLoginBinding
import com.loyaltyglobal.ui.base.BaseActivity
import com.loyaltyglobal.ui.main.view.fragment.LoginFragment
import com.loyaltyglobal.util.Constants.PREF_PLAYER_ID
import com.loyaltyglobal.util.addReplaceFragment
import com.onesignal.OSSubscriptionObserver
import com.onesignal.OSSubscriptionStateChanges
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity(), OSSubscriptionObserver {
    private lateinit var mBinding: ActivityLoginBinding
    private var mPlayerId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        OneSignal.addSubscriptionObserver(this)
        addReplaceFragment(R.id.container_main,LoginFragment(),false, addToBackStack = false)
    }

    override fun onOSSubscriptionChanged(stateChanges: OSSubscriptionStateChanges?) {
        if (!stateChanges?.from?.isSubscribed!! && stateChanges.to.isSubscribed) { // The user is subscribed
            // Either the user subscribed for the first time
            // Or the user was subscribed -> unsubscribed -> subscribed
            mPlayerId = stateChanges.to.userId
            mPlayerId?.let {
                Log.e("TAG", "is registered ID >>> $it")
                mPreferenceProvider.setValue(PREF_PLAYER_ID, it)
            } // Make a POST call to your server with the user ID
        } else {
            Log.e("TAG", "is not registered")
        }
    }
}

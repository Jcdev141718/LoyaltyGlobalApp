package com.loyaltyglobal.ui.main.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils.concat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loyaltyglobal.databinding.BottomsheetEditProfileBinding
import com.loyaltyglobal.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
class EditProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetEditProfileBinding

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = BottomsheetEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                val behaviour = BottomSheetBehavior.from(parent)
                parent.setFullHeight()
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgClose.clickWithDebounce { dismiss() }
        Log.e("TAG","onViewCreated --> ${preferenceProvider.getValue(Constants.PREF_USER_DATA,"")}")
        setDataToUI()
    }

    private fun setDataToUI() {
        preferenceProvider.getUserData().let { userData ->
            binding.apply {
                txtName.text = concat(userData.firstName?.firstLetterCap()," ",userData.lastName?.firstLetterCap())
                txtEmailAddress.text = userData.email
                txtDob.text = userData.birthday.toString()
                txtPhoneNum.text = concat(userData.dialingCode,userData.phone)
            }
        }
    }


}
package com.zeepy.zeepyforandroid.myprofile

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder

/** Top-level Function **/
fun reportShowConfirmDialog(fragment: Fragment) {
    val confirmDialog =
        ZeepyDialogBuilder(fragment.resources.getString(R.string.report_confirm), "myProfile")

    confirmDialog.setLeftButton(R.drawable.box_grayf9_8dp, "취소")
        .setRightButton(R.drawable.box_yellowee_8dp, "확인했으며, 신고할게요")
        .setContent(fragment.resources.getString(R.string.report_confirm_content))
        .setButtonHorizontalWeight(0.287f, 0.712f)
        .setDialogClickListener(object : DialogClickListener {
            override fun clickLeftButton(dialog: ZeepyDialog) {
                dialog.dismiss()
            }

            override fun clickRightButton(dialog: ZeepyDialog) {
                // TODO: 확인 누르면 신고 완료되었다는 텍스트 띄워주면 어떨지?
                dialog.dismiss()
                if (fragment.findNavController().currentDestination?.id == R.id.reportOtherFragment)
                    fragment.findNavController().popBackStack()
            }
        })
        .build()
        .show(fragment.childFragmentManager, fragment.tag)
}
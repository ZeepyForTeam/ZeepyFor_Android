package com.zeepy.zeepyforandroid.customview

class ZeepyDialogBuilder(
    private val title: String,
    private val theme: String?
) {
    private var content: String? = null
    private var leftButtonDrawable: Int? = null
    private var leftButtonText: String? = null
    private var rightButtonDrawable: Int? = null
    private var rightButtonText: String? = null
    private var weightLeftButton: Float? = null
    private var weightRightButton: Float? = null
    private var dialogClickListener: DialogClickListener? = null
    private var singleButton:Boolean?= null

    fun build(): ZeepyDialog {
        return ZeepyDialog(
            title,
            content,
            leftButtonDrawable,
            leftButtonText,
            rightButtonDrawable,
            rightButtonText,
            weightLeftButton,
            weightRightButton,
            theme,
            singleButton,
            dialogClickListener
        )
    }

    fun setContent(content: String): ZeepyDialogBuilder {
        this.content = content
        return this
    }

    fun setSingleButton(isSingleButton: Boolean):ZeepyDialogBuilder {
        this.singleButton = isSingleButton
        return this
    }

    fun setLeftButton(leftButtonDrawable:Int, leftButtonText: String): ZeepyDialogBuilder {
        this.leftButtonDrawable = leftButtonDrawable
        this.leftButtonText = leftButtonText
        return this
    }

    fun setRightButton(rightButtonDrawable:Int, rightButtonText: String): ZeepyDialogBuilder {
        this.rightButtonDrawable = rightButtonDrawable
        this.rightButtonText = rightButtonText
        return this
    }

    fun setButtonHorizontalWeight(left: Float, right: Float): ZeepyDialogBuilder {
        this.weightLeftButton = left
        this.weightRightButton = right
        return this
    }

    fun setDialogClickListener(dialogClickListener: DialogClickListener): ZeepyDialogBuilder {
        this.dialogClickListener = dialogClickListener
        return this
    }

}
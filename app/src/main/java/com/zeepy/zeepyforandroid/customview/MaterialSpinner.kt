package com.zeepy.zeepyforandroid.customview

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.database.DataSetObserver
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.accessibility.AccessibilityEvent
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.zeepy.zeepyforandroid.R
import java.util.*


/**
 * Copyright 2019 Tiago Pereira
 * Copyright 2021 Jeong Suh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
open class MaterialSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    mode: Int = MODE_DROPDOWN
) : TextInputLayout(context, attrs) {

    var impactPoint: Float = 0.0f
    var distance: Float = 0.0f

    companion object {
        /**
         * Represents an invalid position.
         * All valid positions are in the range 0 to 1 less than the number of items in the current
         * adapter.
         */
        const val INVALID_POSITION = -1

        /**
         * Use a dialog window for selecting spinner options.
         */
        const val MODE_DIALOG = 0

        /**
         * Use a dropdown anchored to the Spinner for selecting spinner options.
         */
        const val MODE_DROPDOWN = 1

        /**
         * Use a bottom sheet dialog window for selecting spinner options.
         */
        const val MODE_BOTTOMSHEET = 2

    }

    private val colorStateList: ColorStateList

    private val popup: SpinnerPopup

    private val editText = TextInputEditText(getContext())

    var adapter: SpinnerAdapter? = null
        set(value) {
            field = DropDownAdapter(value, context.theme).also {
                popup.setAdapter(it)
            }
        }

    var onItemSelectedListener: OnItemSelectedListener? = null

    var onItemClickListener: OnItemClickListener? = null

    private var direction =
        if (isLayoutRtl()) ViewCompat.LAYOUT_DIRECTION_RTL else ViewCompat.LAYOUT_DIRECTION_LTR

    /**
     * The currently selected item
     */
    var selection = INVALID_POSITION
        set(value) {
            field = value
            adapter?.apply {
                if (value in 0 until count) {
                    editText.setText(
                        when (val item = getItem(value) ?: "") {
                            is CharSequence -> item
                            else -> item.toString()
                        }
                    )
                    onItemSelectedListener?.onItemSelected(
                        this@MaterialSpinner,
                        null,
                        value,
                        getItemId(value)
                    )
                } else {
                    editText.setText("")
                    onItemSelectedListener?.onNothingSelected(this@MaterialSpinner)
                }
            }
        }

    /**
     * Sets the [prompt] to display when the dialog is shown.
     *
     * @return The prompt to display when the dialog is shown.
     */
    var prompt: CharSequence?
        set(value) {
            popup.setPromptText(value)
        }
        get() = popup.getPrompt()

    val selectedItem: Any?
        get() = popup.getItem(selection)

    val selectedItemId: Long
        get() = popup.getItemId(selection)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.MaterialSpinner).run {
            getInt(R.styleable.MaterialSpinner_android_gravity, -1).let {
                if (it > -1) {
                    gravity = it
                    editText.gravity = it
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        @SuppressLint("RtlHardcoded")
                        if (isLayoutRtl()) {
                            gravity = Gravity.RIGHT
                            editText.gravity = Gravity.RIGHT
                        } else {
                            gravity = Gravity.LEFT
                            editText.gravity = Gravity.LEFT
                        }
                    }
                }
            }
            editText.isEnabled =
                getBoolean(R.styleable.MaterialSpinner_android_enabled, editText.isEnabled)
            editText.isFocusable =
                getBoolean(R.styleable.MaterialSpinner_android_focusable, editText.isFocusable)
            editText.isFocusableInTouchMode = getBoolean(
                R.styleable.MaterialSpinner_android_focusableInTouchMode,
                editText.isFocusableInTouchMode
            )

            getColorStateList(R.styleable.MaterialSpinner_android_textColor)?.let {
                editText.setTextColor(it)
            }
            getDimensionPixelSize(
                R.styleable.MaterialSpinner_android_textSize,
                -1
            ).let {
                // Changed the text size unit issue
                if (it > 0) editText.textSize = it.toFloat().div(resources.displayMetrics.scaledDensity)
            }
            getText(R.styleable.MaterialSpinner_android_text)?.let {
                editText.setText(it)
            }
            // explicitly set it to bottomsheet mode just for now
            popup = BottomSheetPopup(context, getString(R.styleable.MaterialSpinner_android_prompt))
            // Create the color state list.
            // noinspection Recycle
            colorStateList = context.obtainStyledAttributes(
                attrs,
                intArrayOf(R.attr.colorControlActivated)
            ).run {
                val activated = getColor(0, 0)
                val normal = getContext().getColorCompat(R.color.mtrl_textinput_default_box_stroke_color)
                recycle()
                ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_focused),
                        intArrayOf()
                    ), intArrayOf(activated, activated, normal)
                )
            }
            // Set the arrow and properly tint it.
            getContext().getDrawableCompat(
                getResourceId(
                    R.styleable.MaterialSpinner_android_src,
                    getResourceId(
                        R.styleable.MaterialSpinner_srcCompat,
                        R.drawable.spinner_arrow
                    )
                ), getContext().theme
            ).let {
                setDrawable(it)
            }

            recycle()
        }

        this.addView(editText, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

        editText.setPadding(42,0,42,0)
        Log.e("paddingleft", editText.paddingLeft.toString())
        Log.e("paddingright", editText.paddingRight.toString())
        Log.e("paddingBottom", editText.paddingBottom.toString())
        Log.e("paddingTop", editText.paddingTop.toString())

        popup.setOnDismissListener(object : SpinnerPopup.OnDismissListener {
            override fun onDismiss() {
                editText.clearFocus()
            }
        })

        // Disable input.
        editText.maxLines = 1
        editText.inputType = InputType.TYPE_NULL
        editText.imeOptions = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN

        editText.setOnClickListener {
            popup.show(selection)
        }

        editText.onFocusChangeListener.let {
            editText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                v.handler?.post {
                    if (hasFocus) {
                        v.performClick()
                    }
                    it?.onFocusChange(v, hasFocus)
                    onFocusChangeListener?.onFocusChange(this, hasFocus)
                }
            }
        }
    }

    fun getPopup(): SpinnerPopup {
        return popup
    }

    fun setDrawable(drawable: Drawable?, applyTint: Boolean = true) {
        val delta = (editText.paddingBottom - editText.paddingTop) / 2
        drawable?.let {
            DrawableCompat.wrap(drawable)
        }?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            if (applyTint) {
                //DrawableCompat.setTintList(this, colorStateList)
                //DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
            }
        }.let {
            if (isLayoutRtl()) {
                Pair(it, null)
            } else {
                Pair(null, it)
            }
        }.let { (left, right) ->
            editText.setCompoundDrawablesWithIntrinsicBounds(left, null, right, null)
            editText.compoundDrawables.forEach {
                it?.run {
                    bounds = copyBounds().apply {
                        top += delta
                        bottom += delta
                        Log.e("top and bottom", "$top $bottom")
                    }
                }
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        throw RuntimeException(
            "Don't call setOnClickListener." +
                    "You probably want setOnItemClickListener instead."
        )
    }

    /**
     * Set whether this view can receive the focus.
     * Setting this to false will also ensure that this view is not focusable in touch mode.
     *
     * @param [focusable] If true, this view can receive the focus.
     *
     * @see [android.view.View.setFocusableInTouchMode]
     * @see [android.view.View.setFocusable]
     * @attr ref android.R.styleable#View_focusable
     */
    override fun setFocusable(focusable: Boolean) {
        editText.isFocusable = focusable
        super.setFocusable(focusable)
    }

    /**
     * Set whether this view can receive focus while in touch mode.
     * Setting this to true will also ensure that this view is focusable.
     *
     * @param [focusableInTouchMode] If true, this view can receive the focus while in touch mode.
     *
     * @see [android.view.View.setFocusable]
     * @attr ref android.R.styleable#View_focusableInTouchMode
     */
    override fun setFocusableInTouchMode(focusableInTouchMode: Boolean) {
        editText.isFocusableInTouchMode = focusableInTouchMode
        super.setFocusableInTouchMode(focusableInTouchMode)
    }

    /**
     * @see [android.view.View.onRtlPropertiesChanged]
     */
    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        if (direction != layoutDirection) {
            direction = layoutDirection
            editText.compoundDrawables.let {
                editText.setCompoundDrawablesWithIntrinsicBounds(it[2], null, it[0], null)
            }
        }
        super.onRtlPropertiesChanged(layoutDirection)
    }

    override fun setError(errorText: CharSequence?) {
        super.setError(errorText)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            findViewById<TextView>(R.id.textinput_error)?.let { errorView ->
                errorView.gravity = editText.gravity
                when (val p = errorView.parent) {
                    is View -> {
                        p.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun performClick(): Boolean {
        return requestFocus()
    }

    /**
     * Call the OnItemClickListener, if it is defined.
     * Performs all normal actions associated with clicking: reporting accessibility event, playing
     * a sound, etc.
     *
     * @param [view] The view within the adapter that was clicked.
     * @param [position] The position of the view in the adapter.
     * @param [id] The row id of the item that was clicked.
     * @return True if there was an assigned OnItemClickListener that was called, false otherwise is
     * returned.
     */
    fun performItemClick(view: View?, position: Int, id: Long): Boolean {
        return run {
            onItemClickListener?.let {
                //playSoundEffect(SoundEffectConstants.CLICK)
                it.onItemClick(this@MaterialSpinner, view, position, id)
                true
            } ?: false
        }.also {
            view?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED)
        }
    }

    /**
     * Sets the prompt to display when the dialog is shown.
     *
     * @param [promptId] the resource ID of the prompt to display when the dialog is shown.
     */
    fun setPromptId(promptId: Int) {
        prompt = context.getText(promptId)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            SavedState(it).apply {
                this.selection = this@MaterialSpinner.selection
                this.isShowingPopup = this@MaterialSpinner.popup.isShowing()
            }
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                selection = state.selection
                if (state.isShowingPopup) {
                    viewTreeObserver?.addOnGlobalLayoutListener(object :
                        ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            if (!popup.isShowing()) {
                                requestFocus()
                            }
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                viewTreeObserver?.removeOnGlobalLayoutListener(this)
                            } else {
                                viewTreeObserver?.removeGlobalOnLayoutListener(this)
                            }
                        }
                    })
                }
            }
            else -> super.onRestoreInstanceState(state)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (popup.isShowing()) {
            popup.dismiss()
        }
    }

    private fun isLayoutRtl(): Boolean {
        return Locale.getDefault().isLayoutRtl()
    }

    private fun Locale.isLayoutRtl(): Boolean {
        return TextUtilsCompat.getLayoutDirectionFromLocale(this) == ViewCompat.LAYOUT_DIRECTION_RTL
    }

    private fun Context.getDrawableCompat(
        @DrawableRes id: Int,
        theme: Resources.Theme?
    ): Drawable? {
        return resources.getDrawableCompat(id, theme)
    }

    @Throws(Resources.NotFoundException::class)
    private fun Resources.getDrawableCompat(
        @DrawableRes id: Int,
        theme: Resources.Theme?
    ): Drawable? {
        return ResourcesCompat.getDrawable(this, id, theme)?.let { DrawableCompat.wrap(it) }
    }

    /**
     * Returns a themed color integer associated with a particular resource ID.
     * If the resource holds a complex [android.content.res.ColorStateList], then the default color from the set is returned.
     *
     * @param id The desired resource identifier, as generated by the aapt tool.
     *           This integer encodes the package, type, and resource entry.
     *           The value 0 is an invalid identifier.
     *
     * @throws NotFoundException Throws NotFoundException if the given ID does not exist.
     *
     * @return A single color value in the form 0xAARRGGBB.
     */
    @Throws(Resources.NotFoundException::class)
    @ColorInt
    fun Context.getColorCompat(@ColorRes id: Int): Int {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            resources.getColor(id, theme)
        } else {
            @Suppress("DEPRECATION") resources.getColor(id)
        }
    }


    private inner class BottomSheetPopup(
        val context: Context,
        private var prompt: CharSequence? = null
    ) : SpinnerPopup {

        private var popup: BottomSheetDialog? = null
        private var adapter: ListAdapter? = null
        private var listener: SpinnerPopup.OnDismissListener? = null

        override fun getPopup(): Dialog? {
            return popup
        }

        override fun setPromptText(hintText: CharSequence?) {
            prompt = hintText
        }

        override fun getPrompt(): CharSequence? {
            return prompt
        }

        override fun setAdapter(adapter: ListAdapter?) {
            this.adapter = adapter
        }

        override fun show(position: Int) {
            if (adapter == null)
                return

            popup = BottomSheetDialog(context, R.style.BottomSheetDialog).apply {
                prompt?.let { prompt ->
                    setTitle(prompt)
                }

                setContentView(R.layout.bottomsheet_lookaround)
                val parentLayout: LinearLayout? = findViewById(R.id.insert_point)
                parentLayout?.addView(ListView(context).apply {
                    adapter = this@BottomSheetPopup.adapter

                    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
                    onItemClickListener =
                        AdapterView.OnItemClickListener { parent, view, position, id ->
                            this@MaterialSpinner.selection = position
                            onItemClickListener?.let {
                                var value = this@MaterialSpinner.performItemClick(
                                    view,
                                    position,
                                    adapter?.getItemId(position) ?: 0L
                                )
                                Log.e("What is performItemClick in original?", value.toString())
                            }
                            dismiss()

                        }
                })

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    textDirection = this@MaterialSpinner.textDirection
                    textAlignment = this@MaterialSpinner.textAlignment
                }
                setOnDismissListener { listener?.onDismiss() }
            }.also {
                it.show()
            }
        }

        override fun dismiss() {
            popup?.dismiss()
        }

        override fun setOnDismissListener(listener: SpinnerPopup.OnDismissListener?) {
            this.listener = listener
        }

        override fun getItem(position: Int): Any? {
            return adapter?.getItem(position)
        }

        override fun getItemId(position: Int): Long {
            return adapter?.getItemId(position) ?: INVALID_POSITION.toLong()
        }

        override fun isShowing() = popup?.isShowing == true

    }

    private inner class DropDownAdapter(
        private val adapter: SpinnerAdapter?,
        dropDownTheme: Resources.Theme?
    ) : ListAdapter, SpinnerAdapter {

        private val listAdapter: ListAdapter?

        init {

            listAdapter = when (val it = adapter) {
                is ListAdapter -> it
                else -> null
            }

            dropDownTheme?.let {
                when (adapter) {
                    is ThemedSpinnerAdapter -> {
                        if (adapter.dropDownViewTheme != it) {
                            adapter.dropDownViewTheme = it
                        }
                    }
                    else -> {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                            when (adapter) {
                                is ThemedSpinnerAdapter -> {
                                    if (adapter.dropDownViewTheme == null) {
                                        adapter.dropDownViewTheme = it
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        override fun getCount(): Int {
            return adapter?.count ?: 0
        }

        override fun getItem(position: Int): Any? {
            return adapter?.let {
                if (position > INVALID_POSITION && position < it.count) it.getItem(
                    position
                ) else null
            }
        }

        override fun getItemId(position: Int): Long {
            return adapter?.getItemId(position) ?: INVALID_POSITION.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            return getDropDownView(position, convertView, parent)
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var shouldClick: Boolean = false
            if (position == 0) {
                convertView?.setOnTouchListener(OnTouchListener { v, event ->
                    Log.e("which action triggered?", event.action.toString())

                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            shouldClick = true
                            popup.getPopup()
                                ?.findViewById<ConstraintLayout>(R.id.layout_bottomsheet_lookaround)
                                ?.setBackgroundResource(R.drawable.spinner_bottomsheet_box_clicked)
                            v.setBackgroundResource(R.drawable.box_white_borderbottom_clicked)
                            return@OnTouchListener true
                        }
                        MotionEvent.ACTION_UP -> {
                            popup.getPopup()
                                ?.findViewById<ConstraintLayout>(R.id.layout_bottomsheet_lookaround)
                                ?.setBackgroundResource(R.drawable.spinner_bottomsheet_box)
                            popup.getPopup()
                            selection = position
                            popup.dismiss()
                            Log.e("rerrrrrrrrrrrrrrrrreached", "yes")
                        }
                        MotionEvent.ACTION_CANCEL -> {
                            popup.getPopup()
                                ?.findViewById<ConstraintLayout>(R.id.layout_bottomsheet_lookaround)
                                ?.setBackgroundResource(R.drawable.spinner_bottomsheet_box)
                            v.setBackgroundResource(R.drawable.box_white_borderbottom)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            popup.getPopup()
                                ?.findViewById<ConstraintLayout>(R.id.layout_bottomsheet_lookaround)
                                ?.setBackgroundResource(R.drawable.spinner_bottomsheet_box_clicked)
                            v.setBackgroundResource(R.drawable.box_white_borderbottom_clicked)
                            shouldClick = false
                        }
                        else -> {
                            popup.getPopup()
                                ?.findViewById<ConstraintLayout>(R.id.layout_bottomsheet_lookaround)
                                ?.setBackgroundResource(R.drawable.spinner_bottomsheet_box)
                            v.setBackgroundResource(R.drawable.box_white_borderbottom)
                        }
                    }
                    Log.e("final return statement reached??", "yes")
                    return@OnTouchListener false
                })
            }
            return adapter?.getDropDownView(position, convertView, parent)
        }

        override fun hasStableIds(): Boolean {
            return adapter?.hasStableIds() ?: false
        }

        override fun registerDataSetObserver(observer: DataSetObserver) {
            adapter?.registerDataSetObserver(observer)
        }

        override fun unregisterDataSetObserver(observer: DataSetObserver) {
            adapter?.unregisterDataSetObserver(observer)
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call. Otherwise,
         * return true.
         */
        override fun areAllItemsEnabled(): Boolean {
            return listAdapter?.areAllItemsEnabled() ?: true
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call. Otherwise,
         * return true.
         */
        override fun isEnabled(position: Int): Boolean {
            return listAdapter?.isEnabled(position) ?: true
        }

        override fun getItemViewType(position: Int): Int {
            return 0
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun isEmpty(): Boolean {
            return count == 0
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long)

        fun onNothingSelected(parent: MaterialSpinner)
    }

    interface OnItemClickListener {
        fun onItemClick(parent: MaterialSpinner, view: View?, position: Int, id: Long)
    }

    interface SpinnerPopup {

        /**
         * Listener that is called when this popup window is dismissed.
         */
        interface OnDismissListener {

            /**
             * Called when this popup window is dismissed.
             */
            fun onDismiss()
        }

        /**
         * Set hint text to be displayed to the user. This should provide a description of the
         * choice being made.
         *
         * @param [hintText] Hint text to set.
         */
        fun setPromptText(hintText: CharSequence?)

        fun getPrompt(): CharSequence?

        fun getPopup(): Dialog?

        /**
         * Sets the adapter that provides the data and the views to represent the data in this popup
         * window.
         *
         * @param [adapter] The adapter to use to create this window's content.
         */
        fun setAdapter(adapter: ListAdapter?)

        /**
         * Show the popup
         */
        fun show(position: Int)

        /**
         * Dismiss the popup
         */
        fun dismiss()

        /**
         * Set a listener to receive a callback when the popup is dismissed.
         *
         * @param [listener] Listener that will be notified when the popup is dismissed.
         */
        fun setOnDismissListener(listener: OnDismissListener?)

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param [position] Position of the item whose data we want within the adapter's data set.
         * @return The data at the specified position.
         */
        fun getItem(position: Int): Any?

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param [position] The position of the item within the adapter's data set whose row id we
         * want.
         * @return The id of the item at the specified position.
         */
        fun getItemId(position: Int): Long

        /**
         * @return true if the popup is showing, false otherwise.
         */
        fun isShowing(): Boolean
    }

    internal class SavedState : AbsSavedState {
        var selection: Int = INVALID_POSITION
        var isShowingPopup: Boolean = false

        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            selection = source.readInt()
            isShowingPopup = source.readByte().toInt() != 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(selection)
            dest.writeByte((if (isShowingPopup) 1 else 0).toByte())
        }

        override fun toString(): String {
            return "MaterialSpinner.SavedState{" +
                    Integer.toHexString(System.identityHashCode(this)) +
                    " selection=" +
                    selection +
                    ", isShowingPopup=" +
                    isShowingPopup +
                    "}"
        }

        companion object CREATOR : Parcelable.ClassLoaderCreator<SavedState> {

            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel, null)
            }

            override fun createFromParcel(parcel: Parcel, loader: ClassLoader): SavedState {
                return SavedState(parcel, loader)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}
package com.emmar.emmar_assingment.ui.activities

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import com.emmar.emmar_assingment.R
import com.emmar.emmar_assingment.utils.AppUtils

abstract class BaseActivity : AppCompatActivity() {
    private var errorObserver: Observer<Throwable>? = null
    private var loadingStateObserver: Observer<Boolean>? = null
    private var baseContainer: RelativeLayout? = null
    private var pbLoader: FrameLayout? = null
    private var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        init(this)
        baseContainer = findViewById(R.id.rl_base_container)
        pbLoader = findViewById(R.id.pbLoader)
        //initialise data binding here
        progress = ProgressDialog(this)
        //initialise activity context
        initDataBinding()
        //initialise error & failure observers here
        initObservers()
        setupViews()
        setListeners()
        setData()
    }


    abstract fun initDataBinding()

    protected open fun setupViews() {}

    /**
     * Method to set data.
     */
    protected open fun setData() {}

    /**
     * Method to set listeners
     */
    protected open fun setListeners() {}

    private fun initObservers() {
        errorObserver = Observer { throwable -> onErrorOccurred(throwable) }
        loadingStateObserver = Observer { aBoolean: Boolean? -> onLoadingStateChanged(aBoolean!!) }
    }
    private fun onLoadingStateChanged(aBoolean: Boolean) {

    }


    open fun onErrorOccurred(throwable: Throwable) {
        hideProgressDialog()
        enableViewInteraction()
        hideKeyboard(this)
        // handle error message
        if (throwable.message != null) {
            Toast.makeText(activity,throwable.message.toString(),Toast.LENGTH_SHORT).show()
        } else {
            // no message is present
            Toast.makeText(activity,"Some error occured",Toast.LENGTH_SHORT).show()
        }
    }

    open fun showProgressDialog() {
        if (progress?.isShowing == false)
            progress?.show()
    }

    open fun hideProgressDialog() {
        progress?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        progress?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }

    /**
     * hides keyboard onClick anywhere besides edit text on the screen
     *
     * @param ev:
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        return if ((ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is AppCompatEditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            val boundary = IntArray(2)
            view.getLocationOnScreen(boundary)
            view.clearFocus()
            val x = ev.rawX + view.getLeft() - boundary[0]
            val y = ev.rawY + view.getTop() - boundary[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken,
                0
            )
            super.dispatchTouchEvent(ev)
        } else {
            try {
                super.dispatchTouchEvent(ev)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    companion object {
        lateinit var activity: Activity
        private var dialog: Dialog? = null
        var isCOD: Boolean = true
        var isPrepaid: Boolean = true

        fun init(activity: Activity) {
            Companion.activity = activity
        }

        fun isNetworkConnected(): Boolean {
            return if (Companion::activity.isInitialized) AppUtils.isNetworkAvailable(activity) else true
        }

        fun hideKeyboard(context: Activity) {
            try {
                // use application level context to avoid unnecessary leaks.
                val inputManager: InputMethodManager =
                    context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    (context).currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun showKeyboard(context: Activity) {
            try {
                val inputMethodManager: InputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(
                    context.currentFocus,
                    InputMethodManager.SHOW_IMPLICIT
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun disableViewInteraction() {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }

        fun enableViewInteraction() {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }


}

package com.ozgurtas.samplekotlinapplication.Activity

import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by Ozgur on 17.01.2018.
 */

abstract class BaseActivity : AppCompatActivity() {

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentViewId = getContentViewId()
        setContentView(contentViewId)

        onCreateFinished(savedInstanceState)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // set all screens to portrait mode
    }

    override fun onPause() {
        super.onPause()
        hideLoadingDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoadingDialog()
    }

    //Show Progress Dialog
    protected fun showLoadingDialog() {
        if (dialog == null && !this@BaseActivity.isFinishing) {
            dialog = ProgressDialog(this)
            dialog?.setMessage("Loading...")
            dialog?.setCancelable(false)
            dialog?.show()
        }
    }

    //Hide Progress Dialog
    protected fun hideLoadingDialog() {
        if (dialog != null && !this@BaseActivity.isFinishing && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    //Show the toast message to the user
    protected fun showToast(message: String?) = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

    //Abstract class allows us to use only single onCreate() and single setContentView() methods on the other inherited classes
    protected abstract fun onCreateFinished(savedInstanceState: Bundle?)
    protected abstract fun getContentViewId(): Int
}

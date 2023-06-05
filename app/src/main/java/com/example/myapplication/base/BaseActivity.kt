package com.example.myapplication.base

import android.app.Dialog
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.network.RemoteDataSource
import com.kleen.laundrypos.base.BaseRepository
import java.io.Serializable


abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding, R : BaseRepository> : AppCompatActivity(){

    protected lateinit var binding : B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()
    private var progressDialog:Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = null
        binding = getActivityBinding() as B
        val factory = ViewModelFactory(getActivityRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        setBindingLifeCycle()
    }

    abstract fun getActivityBinding() : ViewDataBinding

    abstract fun getActivityRepository() : R

    abstract fun getViewModel() : Class<VM>

    abstract fun setBindingLifeCycle()

    protected open fun handleEvent(event: Event<Any?>) {
        when (event.key) {
            Event.EventType.START_ACTIVITY_NEW_TASK -> if (event.value is Event.Navigation) {
                val intent = Intent(this, (event.value as Event.Navigation).activityClass)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                if (event.parameters != null) {
                    for ((key, value) in event.parameters!!) {
                        intent.putExtra(key, value as Serializable)
                    }
                }
                startActivity(intent)
            }
            Event.EventType.PROGRESS_SHOW -> showProgress()
            Event.EventType.PROGRESS_HIDE -> hideProgress()
            Event.EventType.TOKEN_EXPIRED -> {
                hideProgress()
                finish()
            }
            else -> {}
        }
    }

    private fun showProgress() {
        if (progressDialog == null)
            buildDialog()
        if (!progressDialog!!.isShowing && !isFinishing) {
            try{
                progressDialog?.show()
            }catch (e: Exception){
                Log.e("baseerr", e.toString())
                buildDialog()
                progressDialog?.show()
            }
        }
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
    }

    private fun buildDialog() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        var dialogLoading = Dialog(this)
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setContentView(com.example.myapplication.R.layout.dialog_loading)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialogLoading.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialogLoading.window!!.attributes = lp
        dialogLoading.setCancelable(false)

        progressDialog = dialogLoading
    }
}
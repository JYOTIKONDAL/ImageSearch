package com.damcoassignment.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main_layout.*

abstract class BaseActivity<VDB : ViewDataBinding, BVM : BaseViewModel<*>> : AppCompatActivity() {
    lateinit var injectedViewModel: BVM
    lateinit var viewDataBinding: VDB
    abstract val viewModel: Class<BVM>
    abstract fun getBindingVariable(): Int

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executableDataBinding()
        initUserInterface()
    }

    fun executableDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        injectedViewModel = ViewModelProvider(this).get(viewModel)
        viewDataBinding.setVariable(getBindingVariable(), injectedViewModel)
        viewDataBinding.executePendingBindings()
    }

    protected abstract fun initUserInterface()

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setProgressBarVisibility(visibility: Int) {
        if (visibility == View.VISIBLE) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
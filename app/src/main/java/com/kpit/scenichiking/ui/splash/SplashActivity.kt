package com.kpit.scenichiking.ui.splash

import android.os.Bundle
import com.kpit.scenichiking.R
import com.kpit.scenichiking.base.BaseActivity
import com.kpit.scenichiking.ui.map.MapActivity
import com.kpit.scenichiking.util.ext.launchActivity
import com.kpit.scenichiking.util.ext.observeWith

class SplashActivity : BaseActivity<SplashViewModel>() {

    override val layoutId = R.layout.activity_splash

    override fun getViewModelClass() = SplashViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    override fun initView() {
        // no-op
    }

    override fun observeViewModel() = with(viewModel) {
        timerEndedLiveData.observeWith(this@SplashActivity) { launchActivity<MapActivity>() }
    }
}

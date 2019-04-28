package de.niku.braincards.view.activity_main

import android.os.Bundle
import de.niku.braincards.BR
import de.niku.braincards.R
import de.niku.braincards.common.base.BaseActivity
import de.niku.braincards.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    override fun getLayoutResId() = R.layout.activity_main
    override fun getViewBindingId() = BR.viewmodel

}

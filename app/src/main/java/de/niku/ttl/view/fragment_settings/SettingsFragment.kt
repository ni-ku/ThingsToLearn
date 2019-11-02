package de.niku.ttl.view.fragment_settings

import android.os.Bundle
import android.view.View
import de.niku.ttl.BR

import de.niku.ttl.R
import de.niku.ttl.common.base.BaseFragment
import de.niku.ttl.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_settings
    override fun getViewBindingId(): Int = BR.viewmodel

    override fun performExtraViewBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectObservables()
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    fun connectObservables() {
    }

    fun clearObservables() {
    }
}

package com.blockchain.base.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import javax.inject.Inject


abstract class BaseViewModel : ViewModel() {
    @Inject
    protected lateinit var application: Application

}
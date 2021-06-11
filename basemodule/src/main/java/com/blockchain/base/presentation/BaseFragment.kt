package com.blockchain.base.presentation

import android.content.Context
import dagger.android.support.DaggerFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes



abstract class BaseFragment : DaggerFragment(){
    private var activity: AppCompatActivity? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }
}
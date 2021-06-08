package com.blockchain.app.presentation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import com.blockchain.app.R
import com.blockchain.base.presentation.BaseActivity

import dagger.android.AndroidInjection

class TransactionActivity : BaseActivity() {
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setUpNavigation()
    }


    private fun setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        val bundle = Bundle()
        navGraph.startDestination = R.id.transaction_fragment

        navController.setGraph(navGraph, bundle)
    }
}

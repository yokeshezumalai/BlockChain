package com.blockchain.app.base

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.blockchain.app.TestApp


/**
 * Custom runner to disable dependency injection.
 */
class BlockChainTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
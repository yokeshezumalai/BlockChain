package com.blockchain.app

import android.app.Application
import com.blockchain.app.application.BaseApplication

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [com.boubyan.util.BoubyanTestRunner].
 */
class TestApp : BaseApplication()
package com.blockchain.base.presentation
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import dagger.android.support.DaggerAppCompatActivity

public abstract class BaseActivity : DaggerAppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutRes(): Int

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }
}
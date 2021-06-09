package com.blockchain.app.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blockchain.app.AppConfig
import com.blockchain.app.R
import com.blockchain.app.di.Injectable
import com.blockchain.base.presentation.BaseViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_filter_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.apply

class FilterDialogFragment : BottomSheetDialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory
    private val viewModel: TransactionViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(TransactionViewModel::class.java)
    }

    interface Callback {
        fun onDialogClose(filter: String?)
    }

    var callback: Callback? = null


    companion object {

        const val THIRTY_DAYS = 0
        const val THREE_MONTH = 1
        const val SIX_MONTH = 2
        const val ONE_YEAR = 3
        const val ALL = 4


        fun show(activity: FragmentActivity, callback: Callback) {
            FilterDialogFragment().apply {
                this.callback = callback
            }.show(activity.supportFragmentManager, "filter_dialog")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_filter_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView(){

        filters.addOnSegmentClickListener {
            when (it.absolutePosition) {
                THIRTY_DAYS -> viewModel.timeSpanFilter.value = AppConfig.THIRTY_DAYS
                THREE_MONTH -> viewModel.timeSpanFilter.value = AppConfig.THREE_MONTH
                SIX_MONTH -> viewModel.timeSpanFilter.value = AppConfig.SIX_MONTH
                ONE_YEAR -> viewModel.timeSpanFilter.value = AppConfig.ONE_YEAR
                ALL -> viewModel.timeSpanFilter.value = AppConfig.ALL
            }
        }

        btnApply.setOnClickListener {
            callback?.onDialogClose(viewModel.timeSpanFilter.value)
            dismiss()
        }
    }
}

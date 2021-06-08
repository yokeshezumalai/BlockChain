package com.blockchain.app.helper

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.blockchain.app.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class PriceMarkerView(
    context: Context,
    @LayoutRes layoutResource: Int
) : MarkerView(context, layoutResource) {

    private var markerTextLabel: TextView? = null

    val root = LayoutInflater.from(context).inflate(layoutResource, this, true)

    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        markerTextLabel = root.findViewById(R.id.markerTextLabel) as TextView
        markerTextLabel?.text = entry?.data.toString()

        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(getXOffset(), getYOffset())
    }

    private fun getXOffset(): Float {
        // this will center the marker-view horizontally
        return (-(width / 2)).toFloat()
    }

    private fun getYOffset(): Float {
        // this will cause the marker-view to be above the selected value
        return (-height).toFloat()
    }
}

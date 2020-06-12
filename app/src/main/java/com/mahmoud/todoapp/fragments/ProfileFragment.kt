package com.mahmoud.todoapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.mahmoud.todoapp.R

class ProfileFragment : Fragment() {
    var chart: PieChart? = null
    var pieData: PieData? = null
    var pieDataSet: PieDataSet? = null
    var pieEntries: ArrayList<PieEntry>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        chart = view?.findViewById<PieChart>(R.id.pieChart)

        pieChart()

        return view
    }

    private fun getEntries() {
        pieEntries = ArrayList<PieEntry>()
        pieEntries?.add(PieEntry(60.toFloat(), "Completed tasks"))
        pieEntries?.add(PieEntry(60f, "Incomplete tasks"))
        pieEntries?.add(PieEntry(10f, "In progress"))


    }

    private fun pieChart() {
        getEntries()
        pieDataSet = PieDataSet(pieEntries, "")
        pieData = PieData(pieDataSet)
        chart!!.data = pieData
        pieDataSet!!.setColors(*ColorTemplate.MATERIAL_COLORS)
        pieDataSet!!.sliceSpace = 5f
        pieDataSet!!.valueTextColor = Color.WHITE
        pieDataSet!!.valueTextSize = 10f
        chart?.setDrawEntryLabels(false)
        chart?.setDrawCenterText(true)
        //pieChart.setUsePercentValues(true)

        chart?.animateXY(600, 600);

        // description colors
        chart?.legend?.isEnabled = true

        chart?.description?.isEnabled = false
        /* chart?.description?.textAlign =Paint.Align.RIGHT
         chart?.description?.textColor = Color.WHITE*/
        val vf: ValueFormatter =
            object : ValueFormatter() {
                //value format here, here is the overridden method
                override fun getFormattedValue(value: Float): String {
                    return "" + value.toInt()
                }
            }
        pieData?.setValueFormatter(vf)

        chart?.background = ContextCompat.getDrawable(
            context!!,
            R.color.colorPrimaryDark
        )
        chart?.legend?.textColor = Color.WHITE
        chart?.centerText = "Tasks during this month"
        chart?.setCenterTextTypeface(ResourcesCompat.getFont(context!!, R.font.montserrat_bold))
        chart?.setEntryLabelTypeface(
            ResourcesCompat.getFont(
                context!!,
                R.font.montserrat_regular
            )

        )
        //  pieChart.setCenterTextColor()

    }

}

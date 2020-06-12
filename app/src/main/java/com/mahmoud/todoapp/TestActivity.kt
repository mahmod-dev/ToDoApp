package com.mahmoud.todoapp

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.fragment_profile.*


class TestActivity : AppCompatActivity() {
    var pieData: PieData? = null
    var pieDataSet: PieDataSet? = null
    var pieEntries: ArrayList<PieEntry>? = null
    var PieEntryLabels: ArrayList<Any>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        pieChart()

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
        pieChart.data = pieData
        pieDataSet!!.setColors(*ColorTemplate.MATERIAL_COLORS)
        pieDataSet!!.sliceSpace = 5f
        pieDataSet!!.valueTextColor = Color.WHITE
        pieDataSet!!.valueTextSize = 10f
        pieChart.setDrawEntryLabels(false)
        pieChart.setDrawCenterText(true)
        //pieChart.setUsePercentValues(true)

        pieChart.animateXY(600, 600);

        // description colors
        pieChart.legend.isEnabled = true
        pieChart.description.text = "description label"
        val vf: ValueFormatter =
            object : ValueFormatter() {
                //value format here, here is the overridden method
                override fun getFormattedValue(value: Float): String {
                    return "" + value.toInt()
                }
            }
        pieData?.setValueFormatter(vf)

        pieChart.centerText = "Tasks during this month"
        pieChart.setCenterTextTypeface(ResourcesCompat.getFont(this, R.font.montserrat_bold))
        pieChart.setEntryLabelTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular))
        //  pieChart.setCenterTextColor()

    }

}

package com.example.android.expense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        dbHandler = DBHandler.getInstance(this, null, null, 1);
        setupPieChart();
    }

    private void setupPieChart() {
        List<Record> chartRecords = dbHandler.groupByHandler();
        List<PieEntry> pieEntries = new ArrayList<>();
        for (Record record : chartRecords) {
            PieEntry pieEntry = new PieEntry(record.getAmount(), record.getCategory());
            pieEntries.add(pieEntry);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueTextSize(24);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(pieDataSet);
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.animateY(1000);
        chart.setData(data);
        chart.setEntryLabelColor(R.color.colorAccent);
        chart.setEntryLabelTextSize(24);
        Legend legend = chart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(24);
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        chart.invalidate();
    }
}

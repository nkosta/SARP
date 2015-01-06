package com.example.sarp_mobile;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Kosta on 31.12.2014.
 */
public class SimulationFragment extends Fragment {

    AlgorithmsSwipeActivity contActivity;

    LinearLayout ganttChart, chartValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_simulation, container, false);
        contActivity = (AlgorithmsSwipeActivity) getActivity();


        return rootView;
    }


    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contActivity = (AlgorithmsSwipeActivity) getActivity();
        ganttChart = (LinearLayout) getView().findViewById(R.id.gantt_chart);
        chartValues = (LinearLayout) getView().findViewById(R.id.gantt_chart_values);

        LinearLayout child, childValue;

        ganttChart.removeAllViews();
        chartValues.removeAllViews();

        Proces procesi[] = contActivity.getProcessesArray();

        try {
            contActivity.simulate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("rotated", true);
    }

    public LinearLayout getGantChartView() {
        return ganttChart;
    }
}

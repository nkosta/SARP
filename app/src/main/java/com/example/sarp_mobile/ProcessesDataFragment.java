package com.example.sarp_mobile;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ProcessesDataFragment extends Fragment {

    AlgorithmsSwipeActivity contActivity;

    private int algorithmId, dataGenMode, numOfProcesses;
    private TableLayout tableViewProcesses;
    String algorithmName;

    private SeekBar seekBar;
    private TextView textViewSeekBarDescription, textViewTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contActivity = (AlgorithmsSwipeActivity) getActivity();

        numOfProcesses = contActivity.getNumOfProcesses();
        algorithmId = contActivity.getAlgorithmId();
        dataGenMode = contActivity.getDataGenMode();

        int layout =  dataGenMode == 0 ? R.layout.activity_algorithm_setup_automatic
                                                    : R.layout.activity_algorithm_setup_manual;
        contActivity.setDataFragmenLayout(layout);
        View rootView = inflater.inflate(layout, container, false);

        Resources res = getResources();
        String[] algorithmsList = res.getStringArray(R.array.algorithms_list_array);
        algorithmName = algorithmsList[algorithmId];

        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
        textViewSeekBarDescription = (TextView) rootView.findViewById(R.id.textView1);
        textViewTitle = (TextView) rootView.findViewById(R.id.TextViewAlgoSetupActivityTitle);
        tableViewProcesses = (TableLayout)rootView.findViewById(R.id.process_table);

        textViewTitle.setText(Html.fromHtml("Algoritem: " + "<b>" + algorithmName + "</b>"));
        textViewSeekBarDescription.setText("število procesov: " + (seekBar.getProgress() + 2));

        /** Če je algoritem PS (priority scheduling) dodaj v tabelo procesov še
         * stolpec za prioriteto posameznega procesa **/
        if (algorithmId == Algoritmi.PRIORITY_SCHEDULING) {
            for (int i = 0; i < numOfProcesses + 1; i++) {
                TableRow row = (TableRow) tableViewProcesses.getChildAt(i);
                row.getChildAt(3).setVisibility(View.VISIBLE);
            }
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBarDescription.setText("število procesov: " + (seekBar.getProgress() + 2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textViewSeekBarDescription.setText("število procesov: " + (seekBar.getProgress() + 2));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numOfProcesses = seekBar.getProgress() + 2;
                contActivity.setNumOfProcesses(numOfProcesses);
                textViewSeekBarDescription.setText("število procesov: " + numOfProcesses);

                for (int i = 3; i <= 6; i++) {
                    TableRow row = (TableRow) tableViewProcesses.getChildAt(i);
                    if (i <= numOfProcesses)
                        row.setVisibility(View.VISIBLE);
                    else row.setVisibility(View.INVISIBLE);
                }
            }

        });

        return rootView;
    }


}

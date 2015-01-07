package com.example.sarp_mobile;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class AlgorithmsSwipeActivity extends FragmentActivity implements
        ActionBar.TabListener {

    /*Vsebuje zapise oblike A 10 B 2 C 3 A 2 A 2... Črka pomeni ime proc, številka pa koliko časa se je izvajal - samo za RR. */
    public static List<Object> RR_gantogram = new ArrayList<Object>();


    private static Proces x = new Proces(null);
    private Proces[] processesArray;
    Proces ready_queue[];

    private int numOfProcesses = 6;

    private int dataFragmentLayout;
    private TableLayout tableViewProcesses;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    HashMap<Character, Integer> slovarBarv;

    LinearLayout ganttChart, chartValues;

    private int algorithmId, dataGenMode;

    // Tab titles
    private String[] tabs = {"Procesi", "Simulacija"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithms_swipe);

        Intent intent = getIntent();

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        FragmentManager fm = getFragmentManager();

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
                if (position == 1) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                else
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        if (savedInstanceState !=null) {
            algorithmId = savedInstanceState.getInt("algorithmId");
            dataGenMode = savedInstanceState.getInt("dataGenMode");
            numOfProcesses = savedInstanceState.getInt("numOfProcesses");
            ready_queue = (Proces[]) savedInstanceState.getParcelableArray("readyQueue");
            processesArray = (Proces[]) savedInstanceState.getParcelableArray("processesArray");
            slovarBarv = (HashMap<Character, Integer>) savedInstanceState.getSerializable("colorMap");

        }
        else {
            algorithmId = intent.getIntExtra("ALGORITHM", 0);
            dataGenMode = intent.getIntExtra("DATA_GEN_MODE", 0);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public int getDataGenMode() {
        return dataGenMode;
    }

    public int getAlgorithmId() {
        return algorithmId;
    }

    public int getNumOfProcesses() {
        return numOfProcesses;
    }

    public void setNumOfProcesses(int n) {
        numOfProcesses = n;
    }

    public void setDataFragmenLayout(int l) {
        dataFragmentLayout = l;
    }

    // onClick Button Generiraj
    public void generateData(View view) {
        tableViewProcesses = (TableLayout)findViewById(R.id.process_table);

        x.resetAll();
        if (algorithmId == Algoritmi.PRIORITY_SCHEDULING) {
            processesArray = new Priority_Proces[numOfProcesses];
        }
        else {
            processesArray = new Proces[numOfProcesses];
        }

        int casDospetjaPrev = -1;
        for (int i = 0; i < numOfProcesses; i++) {
            TableRow row = (TableRow) tableViewProcesses.getChildAt(i + 1);
            if (dataGenMode == 0) {
                if (algorithmId == Algoritmi.PRIORITY_SCHEDULING) {
                    processesArray[i] = new Priority_Proces();
                    TextView prioriteta = (TextView) row.getChildAt(3);
                    prioriteta.setText("" + ((Priority_Proces)processesArray[i]).get_prioriteta_proc());
                }
                else {
                    processesArray[i] = new Proces();
                }
                int casDospetja = processesArray[i].get_cas_dospetja_proc();
                int casTrajanja = processesArray[i].get_trajanje_proc();
                TextView casD = (TextView) row.getChildAt(1);
                TextView casT = (TextView) row.getChildAt(2);
                casD.setText("" + casDospetja);
                casT.setText("" + casTrajanja);
            }
            else {
                EditText ime = (EditText) row.getChildAt(0);
                EditText casD;
                int casDospetja;

                if (i == 0) {
                    casDospetja = 0;
                }
                else {
                    casD = (EditText) row.getChildAt(1);
                    casDospetja = Integer.parseInt(casD.getText().toString());
                }

                if (casDospetja <= casDospetjaPrev) {
                    Context context = getApplicationContext();
                    CharSequence text = casDospetja == casDospetjaPrev ? "Dva ali več procesov imajo enak čas dospetja"
                            : "časi dospetja niso kronološko urejeni";
                    //int duration = 3;

                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                EditText casT = (EditText) row.getChildAt(2);
                int casTrajanja = Integer.parseInt(casT.getText().toString());
                if (algorithmId == Algoritmi.PRIORITY_SCHEDULING) {
                    EditText editTextPriority = (EditText) row.getChildAt(3);
                    int priority = Integer.parseInt(editTextPriority.getText().toString());
                    processesArray[i] = new Priority_Proces(ime.getText().toString(), casDospetja, casTrajanja, priority);
                }
                else {
                    processesArray[i] = new Proces(ime.toString(), casDospetja, casTrajanja);
                }
                casDospetjaPrev = casDospetja;
            }
        }

        try {
            simulate();
            // Prestavi se na tab s simulacijo
            //getActionBar().setSelectedNavigationItem(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Funkcija za izris diagrama
    public void drawChart(Proces procesi[]) {
        ganttChart = (LinearLayout) findViewById(R.id.gantt_chart);
        chartValues = (LinearLayout) findViewById(R.id.gantt_chart_values);
        LinearLayout child, childValue;

        ganttChart.removeAllViews();
        chartValues.removeAllViews();

        String bgColors[] = getResources().getStringArray(R.array.chart_colors);

        int counter = 0;
        slovarBarv = new HashMap<Character, Integer>();
        for (Proces p : procesi) {
            Character imeP = p.get_ime_proc().charAt(0);
            if (slovarBarv.containsKey(imeP))
                continue;
            else {
                slovarBarv.put(imeP, Color.parseColor(bgColors[counter]));
                counter++;
            }
        }

        int timePrejsnji = 0;
        for (int i = 0; i < procesi.length; i++) {

            Proces p = procesi[i];
            // ustvari rezino diagrama glede na pripadajoci proces
            child = new LinearLayout(this);
            childValue = new LinearLayout(this);

            //nastavi barvo ozadja
            child.setBackgroundColor(slovarBarv.get(p.get_ime_proc().charAt(0)));

            // nastavi sirino (glede na težo), visino
            LinearLayout.LayoutParams paramsChart = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT);
            paramsChart.weight = p.get_trajanje_proc();
            child.setLayoutParams(paramsChart);
            ganttChart.addView(child);


            LinearLayout.LayoutParams paramsChartValues = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT);
            paramsChartValues.weight = p.get_trajanje_proc();
            childValue.setLayoutParams(paramsChartValues);

            // Ustvarimo se textView za izpis casa izvajanja za posamezno rezino,
            // dodamo ga v LinearLayout (vizualno se nahaja pod gantovim diagramom), ki spada posamezni rezini
            TextView textVal = new TextView(this);

            // Poseben primer, treba je dodati dodaten TextView
            if (i == 0) {
                TextView textValSpec = new TextView(this);
                LinearLayout.LayoutParams params1 = new
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params1.weight = 1;
                textValSpec.setLayoutParams(params1);
                textValSpec.setText(0 + "");
                childValue.addView(textValSpec);
            }
            else {
                View fillerView = new View(this);
                LinearLayout.LayoutParams params1 = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params1.weight = 1;
                fillerView.setLayoutParams(params1);
                childValue.addView(fillerView);
            }
            timePrejsnji += p.get_trajanje_proc();
            textVal.setText(timePrejsnji + "");

            LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            textVal.setLayoutParams(lpView);
            childValue.addView(textVal);

            chartValues.addView(childValue);
        }
    }


    public void simulate() throws Exception {
        TableLayout tabSim = (TableLayout) findViewById(R.id.simulation_table);

        tabSim.removeAllViews();

        TableRow row = new TableRow(this);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.gravity = Gravity.CENTER_HORIZONTAL;
        row.setLayoutParams(rowParams);

        TextView textViewLabelProcessName = new TextView(this);
        textViewLabelProcessName.setText(Html.fromHtml("<b>Ime\nProcesa</b>"));
        textViewLabelProcessName.setTypeface(Typeface.SERIF);
        textViewLabelProcessName.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textViewLabelWaitTime = new TextView(this);
        textViewLabelWaitTime.setText(Html.fromHtml("<b>Čas\nčakanja</b>"));
        textViewLabelWaitTime.setTypeface(Typeface.SERIF);
        textViewLabelWaitTime.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textViewLabelTAT = new TextView(this);
        textViewLabelTAT.setText(Html.fromHtml("<b>TAT</b>"));
        textViewLabelTAT.setTypeface(Typeface.SERIF);
        textViewLabelTAT.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textViewLabelRNTAT = new TextView(this);
        textViewLabelRNTAT.setText(Html.fromHtml("<b>RNTAT</b>"));
        textViewLabelRNTAT.setTypeface(Typeface.SERIF);
        textViewLabelRNTAT.setGravity(Gravity.CENTER_HORIZONTAL);

        row.addView(textViewLabelProcessName);
        row.addView(textViewLabelWaitTime);
        row.addView(textViewLabelTAT);
        row.addView(textViewLabelRNTAT);

        tabSim.addView(row);

        switch (algorithmId) {
            case Algoritmi.PRIORITY_SCHEDULING:
                ready_queue = Algoritmi.priority_scheduling((Priority_Proces[]) processesArray);
                PomozneMetode.izracunajCakalneCasePS((Priority_Proces[]) ready_queue, this);
                drawChart(ready_queue);
                break;
            case Algoritmi.SJN:
                ready_queue = Algoritmi.sjn(processesArray);
                PomozneMetode.izracunajCakalneCaseSJN(ready_queue, this);
                drawChart(ready_queue);
                break;
            case Algoritmi.HRRN:
                ready_queue = Algoritmi.hrrn(processesArray);
                PomozneMetode.izracunajCakalneCaseSJN(ready_queue, this);
                drawChart(ready_queue);
                break;
            case Algoritmi.ROUND_ROBIN:
                RR_gantogram.clear();
                int quantum_a = 4;
                ready_queue = Algoritmi.rr(processesArray, quantum_a);
                Proces[] temp = new Proces[RR_gantogram.size()/2];

                PomozneMetode.izracunajCakalneCaseRR(ready_queue, this);
                int cntr = 0;
                for (int i = 0; i < RR_gantogram.size(); i+=2) {
                    String imeP = (String) RR_gantogram.get(i);
                    int trajanje_p = (Integer)RR_gantogram.get(i + 1);
                    Proces p = new Proces();
                    p.set_ime_proc(imeP);
                    p.set_trajanje_proc(trajanje_p);
                    temp[cntr] = p;
                    cntr++;
                }
                drawChart(temp);
                break;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("numOfProcesses", numOfProcesses);
        outState.putInt("algorithmId", algorithmId);
        outState.putInt("dataGenMode", dataGenMode);

        outState.putParcelableArray("readyQueue", ready_queue);
        outState.putParcelableArray("processesArray", processesArray);
        outState.putSerializable("colorMap", slovarBarv);
    }

    public Proces[] getProcessesArray() {
        return processesArray;
    }
}
package com.example.sarp_mobile;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class AlgorithmsSwipeActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private static Proces x = new Proces(null);
    private Proces[] processesArray;

    private int numOfProcesses = 6;

    private int dataFragmentLayout;
    private TableLayout tableViewProcesses;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;


    private int algorithmId, dataGenMode;

    // Tab titles
    private String[] tabs = {"Procesi", "Simulacija"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithms_swipe);

        Intent intent = getIntent();

        algorithmId = intent.getIntExtra("ALGORITHM", 0);
        dataGenMode = intent.getIntExtra("DATA_GEN_MODE", 0);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

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
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
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
        //btnSimuliraj = (Button)findViewById(R.id.buttonSimulate);

        x.resetAll();
        processesArray = new Proces[numOfProcesses];

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
                    processesArray[i] = new Priority_Proces(ime.toString(), casDospetja, casTrajanja, priority);
                }
                else {
                    processesArray[i] = new Proces(ime.toString(), casDospetja, casTrajanja);
                }
                casDospetjaPrev = casDospetja;
            }
        }

        try {
            simulate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Funkcija za izris diagrama
    public void drawChart(Proces procesi[]) {
        //float weights[] = {0.3f, 0.1f, 0.4f, 0.2f};
        LinearLayout ganttChart = (LinearLayout) findViewById(R.id.gantt_chart);
        ganttChart.removeAllViews();
        LinearLayout child;

        for (int i = 0; i < numOfProcesses; i++) {
            Proces p = procesi[i];
            // ustvari rezino diagrama glede na pripadajoci proces
            child = new LinearLayout(this);

            //nastavi barvo ozadja
            String bgColors[] = getResources().getStringArray(R.array.chart_colors);
            child.setBackgroundColor(Color.parseColor(bgColors[i]));

            // nastavi sirino (glede na težo), visino
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = p.get_trajanje_proc();
            child.setLayoutParams(params);
            ganttChart.addView(child);
        }
    }

    private void simulate() throws Exception {
        if (algorithmId == Algoritmi.PRIORITY_SCHEDULING) {
            Proces ready_queue[] = Algoritmi.priority_scheduling(processesArray);
            PomozneMetode.izracunajCakalneCasePS(ready_queue);
            drawChart(ready_queue);
        }
    }
}
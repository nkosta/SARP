package com.example.sarp_mobile;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PomozneMetode
{

    /* Funkcija preveri ali v tabeli obstaja duplikat elementa. */
    /**
     * Checks if now two start times in table are the same
     *
     * @param tab Process start times
     * @return False if duplicate time found in table other wise true
     */
    public static boolean hasEnakCasdospetja(int[] tab)
    {
        int val = tab[0];

        for(int i=1;i<tab.length;i++)
        {
            if(val == tab[i])
                return false;

            val = tab[i];
        }

        return true;
    }

	/*NAT = terms of the queuing model, turnaround time (TAT) is the residence time Tr , or total
    time that the item(process) spends in the system (** waiting time plus service time (pri meni čas trajanja proc. **)
	NTAT = normalized turnaround time, which is the ratio of turnaround time
	to service time.*/

    /* Metoda izračuna čakalne čase za posamezne procese algoritma PS.  */
    /**
     * Computes wait times for PS algorithm for the queue of processes passed in
     *
     * @param ready_queue
     * @param a           Results are feed directly into this AlgorithmsSwipeActivity instance
     * @throws Exception
     */
    public static void izracunajCakalneCasePS(Priority_Proces ready_queue[], AlgorithmsSwipeActivity a) throws Exception
    {
        int tat = 0;
        int time = 0;
        double rntat = 0;
        int casCakanja = 0;
        double vsotaTat = 0;
        double vsotaRnTat = 0;
        double vsotaCasovCakanja = 0;

        TableLayout tabSim = (TableLayout) a.findViewById(R.id.simulation_table);

        for(int i=0;i<ready_queue.length;i++)
        {
            TableRow row = new TableRow(a);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rowParams.gravity = Gravity.CENTER_HORIZONTAL;
            row.setLayoutParams(rowParams);
            tabSim.addView(row);

            if(i == 0)
            {
                time += ready_queue[0].get_trajanje_proc();
                tat = (casCakanja + ready_queue[0].get_trajanje_proc());
                rntat = (double)(tat/(double)ready_queue[0].get_trajanje_proc());
                vsotaTat += tat;
                vsotaRnTat += rntat;
            }

            else
            {
                while(ready_queue[i].get_cas_dospetja_proc() > time)
                    time++;

                casCakanja = time - ready_queue[i].get_cas_dospetja_proc();
                time += ready_queue[i].get_trajanje_proc();
                vsotaCasovCakanja += casCakanja;
                tat = (casCakanja + ready_queue[i].get_trajanje_proc());
                rntat = (double)(tat/(double)ready_queue[i].get_trajanje_proc());
                vsotaTat += tat;
                vsotaRnTat += rntat;
            }

            String args[] = { ready_queue[i].get_ime_proc(), casCakanja + "",
                    tat + "",  String.format("%.2f", rntat)};

            tabSim.addView(fillSimTableRow(args, a));
        }

        fillSimTabAverageValues(vsotaCasovCakanja/ready_queue.length, vsotaTat/ready_queue.length,
                vsotaRnTat/ready_queue.length, a);
    }

    /* Metoda izračuna čakalne čase za posamezne procese algoritma FCFS.  */
    public static void izracunajCakalneCaseFCFS(Proces ready_queue[], AlgorithmsSwipeActivity a) throws Exception
    {
        int tat;
        int time = 0;
        double rntat;
        int casCakanja = 0;
        double vsotaTat = 0;
        double vsotaRnTat = 0;
        double vsotaCasovCakanja = 0;

        TableLayout tabSim = (TableLayout) a.findViewById(R.id.simulation_table);

        for(int i=0;i<ready_queue.length;i++)
        {
            if(i == 0)
            {
                time += ready_queue[0].get_trajanje_proc();
                vsotaCasovCakanja += casCakanja;
                tat = (casCakanja + ready_queue[0].get_trajanje_proc());
                rntat = (double)(tat/(double)ready_queue[0].get_trajanje_proc());
                vsotaTat += tat;
                vsotaRnTat += rntat;

            }

            else
            {
                while(ready_queue[i].get_cas_dospetja_proc() > time)
                    time++;

                casCakanja = time - ready_queue[i].get_cas_dospetja_proc();
                time += ready_queue[i].get_trajanje_proc();
                vsotaCasovCakanja += casCakanja;
                tat = (casCakanja + ready_queue[i].get_trajanje_proc());
                rntat = (tat/(double)ready_queue[i].get_trajanje_proc());
                vsotaTat += tat;
                vsotaRnTat += rntat;

            }

            String args[] = { ready_queue[i].get_ime_proc(), casCakanja + "",
                    tat + "",  String.format("%.2f", rntat)};

            tabSim.addView(fillSimTableRow(args, a));
        }

        fillSimTabAverageValues(vsotaCasovCakanja/ready_queue.length, vsotaTat/ready_queue.length,
                vsotaRnTat/ready_queue.length, a);
    }

    /* Metoda izračuna čakalne čase za posamezne procese algoritma SJN.  */
    /**
     * Calculates wait times for Shortest job next SNJ algorithm for processes passed in
     * @param ready_queue queue of processes for calucaltion
     * @param a  AlgorithmsSwipeActivity into which is updated with the results
     * @throws Exception
     */
    public static void izracunajCakalneCaseSJN(Proces[] ready_queue, AlgorithmsSwipeActivity a) throws Exception
    {
        int tat;
        int time = 0;
        double rntat;
        int casCakanja = 0;
        double vsotaTat = 0;
        double vsotaRnTat = 0;
        double vsotaCasovCakanja = 0;

        TableLayout tabSim = (TableLayout) a.findViewById(R.id.simulation_table);

        for(int i=0; i<ready_queue.length; i++)
        {
            TableRow row = new TableRow(a);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rowParams.gravity = Gravity.CENTER_HORIZONTAL;
            row.setLayoutParams(rowParams);
            tabSim.addView(row);

            if(i == 0)
            {
                time += ready_queue[0].get_trajanje_proc();
                casCakanja = ready_queue[0].get_cas_cakanja_proc();
                vsotaCasovCakanja += casCakanja;
                tat = (casCakanja + ready_queue[0].get_trajanje_proc());
                rntat = (tat/(double)ready_queue[0].get_trajanje_proc());
                vsotaTat += tat;
                vsotaRnTat += rntat;

            }

            else
            {
                while(ready_queue[i].get_cas_dospetja_proc() > time)
                    time++;

                casCakanja = time - ready_queue[i].get_cas_dospetja_proc();
                time += ready_queue[i].get_trajanje_proc();
                vsotaCasovCakanja += casCakanja;
                tat = (casCakanja + ready_queue[i].get_trajanje_proc());
                rntat = (tat/(double)ready_queue[i].get_trajanje_proc());
                vsotaTat += tat;
                vsotaRnTat += rntat;

            }

            String args[] = { ready_queue[i].get_ime_proc(), casCakanja + "",
                    tat + "",  String.format("%.2f", rntat)};

            tabSim.addView(fillSimTableRow(args, a));

        }

        fillSimTabAverageValues(vsotaCasovCakanja/ready_queue.length, vsotaTat/ready_queue.length,
                vsotaRnTat/ready_queue.length, a);
    }
    /**
     *  Calculates the wait times for Round Robin algorithm
     *
     * @param ready_queue queue of processes to process
     * @param a   AlgorithmsSwipeActivity instance which will be updated with the results
     */
    public static void izracunajCakalneCaseRR(Proces[] ready_queue, AlgorithmsSwipeActivity a)
    {
        int tat;
        double rntat;
        int casCakanja = 0;
        double vsotaTat = 0;
        double vsotaRnTat = 0;
        double vsotaCasovCakanja = 0;

        TableLayout tabSim = (TableLayout) a.findViewById(R.id.simulation_table);

        for(int i=0; i<ready_queue.length; i++)
        {
            casCakanja = ready_queue[i].get_cas_cakanja_proc();
            vsotaCasovCakanja += casCakanja;
            tat = (casCakanja + ready_queue[i].get_trajanje_proc());
            rntat = (tat/(double)ready_queue[i].get_trajanje_proc());
            vsotaTat += tat;
            vsotaRnTat += rntat;

            String args[] = { ready_queue[i].get_ime_proc(), casCakanja + "",
                              tat + "",  String.format("%.2f", rntat)};

            tabSim.addView(fillSimTableRow(args, a));
        }

        fillSimTabAverageValues(vsotaCasovCakanja/ready_queue.length, vsotaTat/ready_queue.length,
                                vsotaRnTat/ready_queue.length, a);
    }

    public static View fillSimTableRow(String arguments[], AlgorithmsSwipeActivity a) {
        TableRow row = new TableRow(a);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.gravity = Gravity.CENTER_HORIZONTAL;
        row.setLayoutParams(rowParams);

        TextView textViewProcessName = new TextView(a);
        textViewProcessName.setTextColor(a.slovarBarv.get(arguments[0].charAt(0)));
        textViewProcessName.setText(Html.fromHtml("<b>"+arguments[0]+"</b>"));
        textViewProcessName.setTypeface(Typeface.SERIF);
        textViewProcessName.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textViewWaitTime = new TextView(a);
        textViewWaitTime.setText(arguments[1]);
        textViewWaitTime.setTypeface(Typeface.SERIF);
        textViewWaitTime.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textViewTAT = new TextView(a);
        textViewTAT.setText(arguments[2]);
        textViewTAT.setTypeface(Typeface.SERIF);
        textViewTAT.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView textViewRNTAT = new TextView(a);
        textViewRNTAT.setText(arguments[3]);
        textViewRNTAT.setTypeface(Typeface.SERIF);
        textViewRNTAT.setGravity(Gravity.CENTER_HORIZONTAL);

        row.addView(textViewProcessName);
        row.addView(textViewWaitTime);
        row.addView(textViewTAT);
        row.addView(textViewRNTAT);

        return row;
    }

    private static void fillSimTabAverageValues(double wait_time, double tat, double ntat, AlgorithmsSwipeActivity a) {
        LinearLayout l = (LinearLayout) a.findViewById(R.id.avg_values);
        l.setVisibility(View.VISIBLE);
        TextView tvWaitTime = (TextView) a.findViewById(R.id.avg_wait_time);
        TextView tvTat = (TextView) a.findViewById(R.id.avg_tat);
        TextView tvNtat = (TextView) a.findViewById(R.id.avg_ntat);


        tvWaitTime.setText(String.format("Povprečen čas čakanja: %1.2f", wait_time));
        tvTat.setText(String.format("Povprečen TAT: %1.2f", tat));
        tvNtat.setText(String.format("Povprečen NTAT: %1.2f", ntat));
    }
}


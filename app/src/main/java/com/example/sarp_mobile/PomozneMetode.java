package com.example.sarp_mobile;

import android.app.ActionBar;
import android.graphics.Typeface;
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

    /* Funkcija preveri ali so vsa vnosna polja izpolnjena. */
//    public static boolean hasPravilniVnos(JTextField vnosnaPolja[])
//    {
//        for(int i=0;i<vnosnaPolja.length;i++)
//        {
//            if(vnosnaPolja[i].getText().equals(""))
//                return false;
//        }
//
//        return true;
//    }

	/*NAT = terms of the queuing model, turnaround time (TAT) is the residence time Tr , or total
    time that the item(process) spends in the system (** waiting time plus service time (pri meni čas trajanja proc. **)
	NTAT = normalized turnaround time, which is the ratio of turnaround time
	to service time.*/

    /* Metoda izračuna čakalne čase za posamezne procese algoritma PS.  */
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


//                textArea.append(String.format("    %s\t        %2d\t       %2d\t       %1.2f\n",ready_queue[0].get_ime_proc(),casCakanja,tat,rntat));
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

//                textArea.append(String.format("    %s\t        %2d\t       %2d\t       %1.2f\n",ready_queue[i].get_ime_proc(),casCakanja,tat,rntat));
            }

            TextView textViewProcessName = new TextView(a);
            textViewProcessName.setText(ready_queue[i].get_ime_proc());
            textViewProcessName.setTypeface(Typeface.SERIF);
            textViewProcessName.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewWaitTime = new TextView(a);
            textViewWaitTime.setText(casCakanja + "");
            textViewWaitTime.setTypeface(Typeface.SERIF);
            textViewWaitTime.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewTAT = new TextView(a);
            textViewTAT.setText(tat + "");
            textViewTAT.setTypeface(Typeface.SERIF);
            textViewTAT.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewRNTAT = new TextView(a);
            textViewRNTAT.setText(String.format("%.2f", rntat));
            textViewRNTAT.setTypeface(Typeface.SERIF);
            textViewRNTAT.setGravity(Gravity.CENTER_HORIZONTAL);

            row.addView(textViewProcessName);
            row.addView(textViewWaitTime);
            row.addView(textViewTAT);
            row.addView(textViewRNTAT);
        }

//        textArea.append(String.format("Povprečen čas čakanja: %1.2f\n", (vsotaCasovCakanja/ready_queue.length)));
//        textArea.append(String.format("Povprečen TAT: %1.2f\n", (vsotaTat/ready_queue.length)));
//        textArea.append(String.format("Povprečen NTAT: %1.2f\n", (vsotaRnTat/ready_queue.length)));
    }

    /* Metoda izračuna čakalne čase za posamezne procese algoritma FCFS.  */
//    public static void izracunajCakalneCaseFCFS(Proces ready_queue[], JTextArea textArea) throws Exception
//    {
//        int tat;
//        int time = 0;
//        double rntat;
//        int casCakanja = 0;
//        double vsotaTat = 0;
//        double vsotaRnTat = 0;
//        double vsotaCasovCakanja = 0;
//
//        textArea.append("|Proces|\t|Čas čakanja|\t     |TAT|\t     |NTAT|\n");
//
//        for(int i=0;i<ready_queue.length;i++)
//        {
//            if(i == 0)
//            {
//                time += ready_queue[0].get_trajanje_proc();
//                vsotaCasovCakanja += casCakanja;
//                tat = (casCakanja + ready_queue[0].get_trajanje_proc());
//                rntat = (double)(tat/(double)ready_queue[0].get_trajanje_proc());
//                vsotaTat += tat;
//                vsotaRnTat += rntat;
//
//                textArea.append(String.format("    %s\t        %2d\t       %2d\t       %1.2f\n",ready_queue[0].get_ime_proc(),casCakanja,tat,rntat));
//            }
//
//            else
//            {
//                while(ready_queue[i].get_cas_dospetja_proc() > time)
//                    time++;
//
//                casCakanja = time - ready_queue[i].get_cas_dospetja_proc();
//                time += ready_queue[i].get_trajanje_proc();
//                vsotaCasovCakanja += casCakanja;
//                tat = (casCakanja + ready_queue[i].get_trajanje_proc());
//                rntat = (tat/(double)ready_queue[i].get_trajanje_proc());
//                vsotaTat += tat;
//                vsotaRnTat += rntat;
//
//                textArea.append(String.format("    %s\t        %2d\t       %2d\t       %1.2f\n",ready_queue[i].get_ime_proc(),casCakanja,tat,rntat));
//            }
//        }
//
//        textArea.append(String.format("Povprečen čas čakanja: %1.2f\n", (vsotaCasovCakanja/ready_queue.length)));
//        textArea.append(String.format("Povprečen TAT: %1.2f\n", (vsotaTat/ready_queue.length)));
//        textArea.append(String.format("Povprečen NTAT: %1.2f\n", (vsotaRnTat/ready_queue.length)));
//    }

    /* Metoda izračuna čakalne čase za posamezne procese algoritma SJN.  */
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

            TextView textViewProcessName = new TextView(a);
            textViewProcessName.setText(ready_queue[i].get_ime_proc());
            textViewProcessName.setTypeface(Typeface.SERIF);
            textViewProcessName.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewWaitTime = new TextView(a);
            textViewWaitTime.setText(casCakanja + "");
            textViewWaitTime.setTypeface(Typeface.SERIF);
            textViewWaitTime.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewTAT = new TextView(a);
            textViewTAT.setText(tat + "");
            textViewTAT.setTypeface(Typeface.SERIF);
            textViewTAT.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewRNTAT = new TextView(a);
            textViewRNTAT.setText(String.format("%.2f", rntat));
            textViewRNTAT.setTypeface(Typeface.SERIF);
            textViewRNTAT.setGravity(Gravity.CENTER_HORIZONTAL);

            row.addView(textViewProcessName);
            row.addView(textViewWaitTime);
            row.addView(textViewTAT);
            row.addView(textViewRNTAT);
        }

//        textArea.append(String.format("Povprečen čas čakanja: %1.2f\n", (vsotaCasovCakanja/ready_queue.length)));
//        textArea.append(String.format("Povprečen TAT: %1.2f\n", (vsotaTat/ready_queue.length)));
//        textArea.append(String.format("Povprečen NTAT: %1.2f\n", (vsotaRnTat/ready_queue.length)));
    }

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

            TableRow row = new TableRow(a);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rowParams.gravity = Gravity.CENTER_HORIZONTAL;
            row.setLayoutParams(rowParams);
            tabSim.addView(row);

            TextView textViewProcessName = new TextView(a);
            textViewProcessName.setText(ready_queue[i].get_ime_proc());
            textViewProcessName.setTypeface(Typeface.SERIF);
            textViewProcessName.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewWaitTime = new TextView(a);
            textViewWaitTime.setText(casCakanja + "");
            textViewWaitTime.setTypeface(Typeface.SERIF);
            textViewWaitTime.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewTAT = new TextView(a);
            textViewTAT.setText(tat + "");
            textViewTAT.setTypeface(Typeface.SERIF);
            textViewTAT.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textViewRNTAT = new TextView(a);
            textViewRNTAT.setText(String.format("%.2f", rntat));
            textViewRNTAT.setTypeface(Typeface.SERIF);
            textViewRNTAT.setGravity(Gravity.CENTER_HORIZONTAL);

            row.addView(textViewProcessName);
            row.addView(textViewWaitTime);
            row.addView(textViewTAT);
            row.addView(textViewRNTAT);
//            textArea.append(String.format("    %s\t        %2d\t       %2d\t       %1.2f\n",ready_queue[i].get_ime_proc(),casCakanja,tat,rntat));
        }

//        textArea.append(String.format("Povprečen čas čakanja: %1.2f\n", (vsotaCasovCakanja/ready_queue.length)));
//        textArea.append(String.format("Povprečen TAT: %1.2f\n", (vsotaTat/ready_queue.length)));
//        textArea.append(String.format("Povprečen NTAT: %1.2f\n", (vsotaRnTat/ready_queue.length)));
    }

}


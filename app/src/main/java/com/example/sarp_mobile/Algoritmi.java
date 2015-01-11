package com.example.sarp_mobile;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Algoritmi
{
    public final static int PRIORITY_SCHEDULING = 0;
    public final static int SJN = 1;
    public final static int FCFS = 2;
    public final static int HRRN = 3;
    public final static int ROUND_ROBIN = 4;


    public static Proces[] fcfs(Proces[]procesi)
    {
        return procesi;
    }

    /**
     * Array of Process to use in simulations
     *
     * @param procesi
     * @return  array sorted by process priority
      */
    public static Priority_Proces[] priority_scheduling(Priority_Proces[]procesi) throws Exception
    {
        Priority_Proces[]temp = procesi.clone();

        Arrays.sort(temp, new Comparator<Priority_Proces>()
        {
            public int compare(Priority_Proces p1, Priority_Proces p2)
            {
                Integer priority1 = p1.get_prioriteta_proc();
                Integer priority2 = p2.get_prioriteta_proc();
                return priority1.compareTo(priority2);
            }
        });

        return temp;
    }
    /**
     * Simulation of Shortest job next (SJN)
     * Algorithm for process execution
     *
     * @param procesi process tused in simulation
     * @return Process with updated values
     */
    public static Proces[] sjn(Proces[]procesi) throws Exception
    {
        int i;
        int time;
        int k = 0;
        int smallest;
        int sum_bt = 0;
        int bt[] = new int[(procesi.length+1)];
        int at[] = new int[(procesi.length+1)];
        Proces[] temp = new Proces[(procesi.length)];
        String imena[] = new String[(procesi.length)];

        for(int m=0; m<procesi.length; m++)
        {
            imena[m] = procesi[m].get_ime_proc();
            bt[m] = procesi[m].get_trajanje_proc();
            at[m] = procesi[m].get_cas_dospetja_proc();
        }

        for(int j=0; j<bt.length-1; j++)
            sum_bt += bt[j];

        bt[bt.length-1] = Integer.MAX_VALUE;

        for(time = 0; time < sum_bt;)
        {
            smallest = bt.length-1;

            for(i=0; i<procesi.length; i++)
            {
                if(at[i] <= time && bt[i] > 0 && bt[i] < bt[smallest])
                    smallest = i;
            }

            if(smallest == bt.length-1)
            {
                time++;
                continue;
            }

            temp[k] = procesi[smallest];
            temp[k].set_cas_cakanja((time - at[smallest]));
            k++;

            time += bt[smallest];
            bt[smallest] = 0;
        }

        return temp;
    }
    /**
     * Simulation of Highest Response Ratio Next (HRRN)
     *
     * @param procesi process tused in simulation
     * @return Process with updated values
     */
    public static Proces[] hrrn(Proces[]procesi) throws Exception
    {
        int k = 1;
        int time = 0;
        Proces temp[] = procesi.clone();
        Vector<Proces> q = new Vector<Proces>();
        Vector<Proces> izvedi = new Vector<Proces>();

		/* Obdelaj prvi proces. */
        izvedi.add(temp[0]);
        time += temp[0].get_trajanje_proc();
        temp[0].set_cas_cakanja(0);

		/* Obdelaj ostale procese - začni pri drugem. */
        while(k < temp.length)
        {
            while(temp[k].get_cas_dospetja_proc() > time)
                time++;

			/* Če je čas dospetja naslednjega procesa manjsi ali enak trenutni uri (time) */
            if(temp[k].get_cas_dospetja_proc() <= time)
            {
                temp[k].set_cas_cakanja(time - temp[k].get_cas_dospetja_proc());
                time += temp[k].get_trajanje_proc();

                izvedi.add(temp[k]);
                k++;

                for(int i=k; i<temp.length; i++)
                {
                    while(temp[k].get_cas_dospetja_proc() > time)
                        time++;

                    if(temp[i].get_cas_dospetja_proc() <= time)
                    {
                        q.add(temp[i]);
                    }
                }
                break;
            }

            else
                time++;
        }

        while(!q.isEmpty())
        {
            q = racunajRatio(q,time);

			/* Na začetku Vektorja se vedno nahaja proces, ki ima najvisje razmerje. Ta gre naslednji v obdelavo. */
            Collections.sort(q, Collections.reverseOrder(new Comparator<Proces>() {
                public int compare(Proces p1, Proces p2) {
                    Double ratio1 = p1.get_proc_ratio();
                    Double ratio2 = p2.get_proc_ratio();

                    return ratio1.compareTo(ratio2);
                }
            }));

            izvedi.add(q.get(0));
            time += q.get(0).get_trajanje_proc();

			/* Obdelan proces odstrani iz Vektorja. */
            q.remove(0);
        }

        izvedi.toArray(temp);

        return temp;
    }

    private static Vector<Proces> racunajRatio(Vector<Proces> q, int time)
    {
        double ratio;

        for(int i=0;i<q.size();i++)
        {
            q.get(i).set_cas_cakanja(time - q.get(i).get_cas_dospetja_proc());
            ratio = (double)(q.get(i).get_cas_cakanja_proc() + (double)q.get(i).get_trajanje_proc()) / (double)q.get(i).get_trajanje_proc();
            q.get(i).set_proc_ratio(ratio);
        }

        return q;
    }

    /**
     * Round-robin (RR) algorithm simulation
     *
     * @param procesi process used in simulation
     * @param quantum Cpu time allowed  for each process before interuption
     * @return Process with updated values
     */
    public static Proces[] rr(Proces[]procesi, int quantum) throws Exception
    {
        int j;
        int k;
        int q;
        int sum = 0;
        q = quantum;
        int n = procesi.length;
        int bt[] = new int[n];
        int wt[] = new int[n];
        int a[] = new int[n];

        for(int m=0; m<procesi.length; m++)
        {
            bt[m] = procesi[m].get_trajanje_proc();
        }

        for(int i=0; i<n; i++)
            a[i] = bt[i];

        for(int i=0; i<n; i++)
            wt[i] = 0;

        do
        {
            for(int i=0; i<n; i++)
            {
                if(bt[i] > q && bt[i] != 0)
                {
                    AlgorithmsSwipeActivity.RR_gantogram.add(procesi[i].get_ime_proc());
                    AlgorithmsSwipeActivity.RR_gantogram.add(q);

                    bt[i] -= q;

                    for(j=0; j<n; j++)
                    {
                        if((j !=i ) && (bt[j] != 0))
                            wt[j] += q;
                    }
                }

                else if(bt[i] <= q && bt[i] != 0)
                {
                    AlgorithmsSwipeActivity.RR_gantogram.add(procesi[i].get_ime_proc());
                    AlgorithmsSwipeActivity.RR_gantogram.add(bt[i]);

                    for(j=0; j<n; j++)
                    {
                        if((j != i ) && (bt[j] != 0))
                            wt[j] += bt[i];
                    }

                    bt[i] = 0;
                }
            }

            sum = 0;

            for(k=0;k<n;k++)
                sum = sum + bt[k];

        }
        while(sum != 0);

        for(int i=0; i<n; i++)
            procesi[i].set_cas_cakanja(wt[i]);

        return procesi;
    }
}

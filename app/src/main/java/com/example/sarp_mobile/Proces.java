package com.example.sarp_mobile;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Process object
 * Stores data relevant to analysis of process execution
 * Handles random data generation per process atinstantiationn
 */
public class Proces implements Parcelable {
    /* Vsak proces ima naslednje atribute */
    private String ime_proc;
    private int trajanje_proc;
    private int dospetje_proc;
    private int cakanje_proc;
    private double proc_ratio;

    /*Pomožne spremenljivke v razredu*/
    /* Minimalna in maksimalna vrednost za čas trajaja procesa */
    private final int min_t = 1;
    private final int max_t = 10;

    /* Minimalna in maksimalna vrednost za časovno rezino procesa pri RR */
    private final int min_q = 1;
    private final int max_q = 20;

    /* Pomožna spr., ki se uporablja za generiranje nakljucnega casa dospetja procesa */
    /* Prvi proces vedno pride v času 0. Vsak naslednji pa v času prejšnji +1 ali +2. */
    static int cas_dosp = 0;

    /* Indikator, ki določa prvi klic konstruktorja, da vemo za prvi proces nastaviti */
    /* čas dospetja na 0. */
    static boolean prvi_klic = true;

    /* Začetna vrednost (ime) za generiranje imen procesov */
    static Character c = 'A';
    /**
     * Empty constructor
     *
     * @param n
     */
    public Proces(Object n){}

    /* Privzeti konstruktor, ki zgenerira vse vrednosti */
    /**
     * Constructor without parameters generates
     * Name is char variable incremented by 1 for each call to this constructor
     * Random process start and random process duration
     */
    public Proces()
    {
        this.ime_proc = gen_ime_proc();
        this.trajanje_proc = gen_cas_trajanja();
        this.dospetje_proc = gen_cas_dospetja();

        /* Po prvem klicu konstruktorja, rečemo da to ni več prvi klic. */
        prvi_klic = false;
    }

    /* Ta konstruktor ima vse tri atribute. */
    /**
     * Constructor creates instance with data in supplied parameters
     *
     * @param ime_proc          Proces name
     * @param cas_dospetja_proc Process start time
     * @param trajanje_proc     Process duration
     */
    public Proces(String ime_proc, int cas_dospetja_proc, int trajanje_proc)
    {
        this.ime_proc = ime_proc;
        this.trajanje_proc = trajanje_proc;
        this.dospetje_proc = cas_dospetja_proc;
        prvi_klic = false;
    }

    /* get in set metode/funkcije za vse 3 atribute */
    /**
     * Setter for process name
     *
     * @param ime_proc
     */
    protected void set_ime_proc(String ime_proc)
    {
        this.ime_proc = ime_proc;
    }

    /**
     * Setter for proces duration time
     *
     * @param trajanje_proc
     */
    protected void set_trajanje_proc(int trajanje_proc)
    {
        this.trajanje_proc = trajanje_proc;
    }
    /**
     * Setter for process start time
     *
     * @param cas_dospetja_proc
     */
    protected void set_cas_dospetja_proc(int cas_dospetja_proc)
    {
        this.dospetje_proc = cas_dospetja_proc;
    }
    /**
     * Setter for process wait time
     *
     * @param cakanje_proc
     */
    protected void set_cas_cakanja(int cakanje_proc)
    {
        this.cakanje_proc = cakanje_proc;
    }

    protected void set_proc_ratio(double proc_ratio)
    {
        this.proc_ratio = proc_ratio;
    }
    /**
     * Getter for process name
     *
     * @return current process name
     */
    protected String get_ime_proc()
    {
        return this.ime_proc;
    }
    /**
     * Getter for process duration
     *
     * @return current process duration
     */
    protected int get_trajanje_proc()
    {
        return this.trajanje_proc;
    }
    /**
     * Getter for proces start time
     *
     * @return current time of process start
     */
    protected int get_cas_dospetja_proc()
    {
        return this.dospetje_proc;
    }
    /**
     * Getter for process wait time
     *
     * @return current wait time for process
     */
    protected int get_cas_cakanja_proc()
    {
        return this.cakanje_proc;
    }

    protected double get_proc_ratio()
    {
        return this.proc_ratio;
    }

    /* Generira naključen čas trajanja procesa na intervalu 1 - 5 [min, max] */
    private int gen_cas_trajanja()
    {
        return min_t + (int)(Math.random() * ((max_t - min_t) + 1));
    }

    /* Generira naključen čas trajanja procesa na intervalu 5-10 (min-max) */
    private int gen_cas_dospetja()
    {
        if(prvi_klic)
            return 0;

        else
            return cas_dosp += (int)(Math.random() < 0.45 ? 1:2);
    }

    /* Vrne ime procesa. Vsakič za en znak "večje" ime --> [A],[A,B],[A,B,C] */
    private String gen_ime_proc()
    {
        return (c++).toString();
    }

    /* Generira nakljucni quantum za RR med vključno 1 in vključno 20 */
    public int gen_quantum()
    {
        return min_q + (int)(Math.random() * ((max_q - min_q) + 1));
    }

    /* Te metode ponastavijo vse staticne spremenljivke razreda */
    /**
     * Resets counter used so that randomly generated start times aren't in conflict
     */
    public void reset_cas_dosp()
    {
        cas_dosp = 0;
    }
    /**
     * Resets is first call flag used
     * to get start time zero for first process generated
     */
    public void reset_prvi_klic()
    {
        prvi_klic = true;
    }
    /**
     * Resets char variable used to generate process names
     */
    public void reset_names()
    {
        c = 'A';
    }
    /**
     * Resets all the static variables
     */
    public void resetAll()
    {
        c = 'A';
        cas_dosp = 0;
        prvi_klic = true;
    }

    @Override
    public String toString()
    {
        return String.format("%s\t%d\t%d", this.ime_proc, this.dospetje_proc, this.trajanje_proc);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ime_proc);
        dest.writeInt(this.trajanje_proc);
        dest.writeInt(this.dospetje_proc);
        dest.writeInt(this.cakanje_proc);
        dest.writeDouble(this.proc_ratio);
        dest.writeInt(this.min_t);
        dest.writeInt(this.max_t);
        dest.writeInt(this.min_q);
        dest.writeInt(this.max_q);
    }

    private Proces(Parcel in) {
        this.ime_proc = in.readString();
        this.trajanje_proc = in.readInt();
        this.dospetje_proc = in.readInt();
        this.cakanje_proc = in.readInt();
        this.proc_ratio = in.readDouble();
        //this.min_t = in.readInt();
        //this.max_t = in.readInt();
        //this.min_q = in.readInt();
        //this.max_q = in.readInt();
    }

    public static final Parcelable.Creator<Proces> CREATOR = new Parcelable.Creator<Proces>() {
        public Proces createFromParcel(Parcel source) {
            return new Proces(source);
        }

        public Proces[] newArray(int size) {
            return new Proces[size];
        }
    };
}
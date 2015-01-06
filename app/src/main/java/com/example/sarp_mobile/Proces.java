package com.example.sarp_mobile;

import android.os.Parcel;
import android.os.Parcelable;

public class Proces implements Parcelable {
    /* Vsak proces ima naslednje atribute */
    private String ime_proc;
    private int trajanje_proc;
    private int dospetje_proc;
    private int cakanje_proc;
    /*Pomožne spremenljivke v razredu*/
    /* Minimalna in maksimalna vrednost za čas trajaja procesa */
    private final int min_t = 1;
    private final int max_t = 10;

    /* Pomožna spr., ki se uporablja za generiranje nakljucnega casa dospetja procesa */
    /* Prvi proces vedno pride v času 0. Vsak naslednji pa v času prejšnji +1 ali +2. */
    static int cas_dosp = 0;

    /* Indikator, ki določa prvi klic konstruktorja, da vemo za prvi proces nastaviti */
    /* čas dospetja na 0. */
    static boolean prvi_klic = true;

    /* Začetna vrednost (ime) za generiranje imen procesov */
    static Character c = 'A';

    public Proces(Object n){}

    /* Privzeti konstruktor, ki zgenerira vse vrednosti */
    public Proces()
    {
        this.ime_proc = gen_ime_proc();
        this.trajanje_proc = gen_cas_trajanja();
        this.dospetje_proc = gen_cas_dospetja();

        /* Po prvem klicu konstruktorja, rečemo da to ni več prvi klic. */
        prvi_klic = false;
    }

    /* Ta konstruktor ima vse tri atribute. */
    public Proces(String ime_proc, int cas_dospetja_proc, int trajanje_proc)
    {
        this.ime_proc = ime_proc;
        this.trajanje_proc = trajanje_proc;
        this.dospetje_proc = cas_dospetja_proc;
        prvi_klic = false;
    }

    /* get in set metode/funkcije za vse 3 atribute */
    protected void set_ime_proc(String ime_proc)
    {
        this.ime_proc = ime_proc;
    }

    protected void set_trajanje_proc(int trajanje_proc)
    {
        this.trajanje_proc = trajanje_proc;
    }

    protected void set_cas_dospetja_proc(int cas_dospetja_proc)
    {
        this.dospetje_proc = cas_dospetja_proc;
    }

    protected void set_cas_cakanja(int cakanje_proc)
    {
        this.cakanje_proc = cakanje_proc;
    }

    protected String get_ime_proc()
    {
        return this.ime_proc;
    }

    protected int get_trajanje_proc()
    {
        return this.trajanje_proc;
    }

    protected int get_cas_dospetja_proc()
    {
        return this.dospetje_proc;
    }

    protected int get_cas_cakanja_proc()
    {
        return this.cakanje_proc;
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

    /* Te metode ponastavijo vse staticne spremenljivke razreda */
    public void reset_cas_dosp()
    {
        cas_dosp = 0;
    }

    public void reset_prvi_klic()
    {
        prvi_klic = true;
    }

    public void reset_names()
    {
        c = 'A';
    }

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
        dest.writeInt(this.min_t);
        dest.writeInt(this.max_t);
    }

    private Proces(Parcel in) {
        this.ime_proc = in.readString();
        this.trajanje_proc = in.readInt();
        this.dospetje_proc = in.readInt();
        this.cakanje_proc = in.readInt();
        //this.min_t = in.readInt();
        //this.max_t = in.readInt();
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